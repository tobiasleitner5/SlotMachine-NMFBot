package Main;

import com.google.common.collect.Lists;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testdatagenerator.TestDataGenerator;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String [] args){

        //handle input
        Options options = new Options();

        Option airline = new Option("a", "airline", true, "name of the airline");
        airline.setRequired(true);
        options.addOption(airline);

        Option numberOfFlights = new Option("f", "numberOfFlights", true, "number of flights");
        numberOfFlights.setRequired(true);
        options.addOption(numberOfFlights);

        Option numberOfSlots = new Option("s", "numberOfSlots", true, "number of slots");
        numberOfSlots.setRequired(true);
        options.addOption(numberOfSlots);

        Option timeWindow = new Option("w", "timeWindow", true, "Time window for slots and flights");
        timeWindow.setRequired(true);
        options.addOption(timeWindow);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            e.printStackTrace();
            formatter.printHelp("NMF Bot", options);
            System.exit(1);
        }

        int numberOfFlightsInt = Integer.parseInt(cmd.getOptionValue("numberOfFlights"));
        int numberOfSlotsInt = Integer.parseInt(cmd.getOptionValue("numberOfSlots"));
        String airlineString = cmd.getOptionValue("airline");
        String timeWindowString = cmd.getOptionValue("timeWindow");

        //check input
        try {
            checkInput(numberOfFlightsInt, numberOfSlotsInt, timeWindowString);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        String [] s = timeWindowString.split("->");

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        //generate list of times
        List<LocalDateTime> l = TestDataGenerator.generateTimeList(LocalDateTime.parse(s[0], timeFormatter), LocalDateTime.parse(s[1], timeFormatter));

        //split this list in 10 smaller lists
        //WICHTIG: IMMER MEHR ALS 10 DATES erzeugen und am BESTEN eine gerade Anzahl! MINUTE 40 bis MINUTE 40. Division durch 10 soll ohne REST sein.
        List<List<LocalDateTime>> listOfLists = Lists.partition(l, l.size()/10);
        if(listOfLists.size() > 10){
            listOfLists.get(9).addAll(listOfLists.get(10));
        }

        int [] flightProbs = {1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int [] slotProbs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 9, 9, 9, 10, 10, 10, 10, 10, 10, 10};

        List<LocalDateTime> slotTimes = TestDataGenerator.pickRandomDatesWithProbabilities(slotProbs, numberOfSlotsInt, listOfLists);
        List<LocalDateTime> flightTimes = TestDataGenerator.pickRandomDatesWithProbabilities(flightProbs, numberOfFlightsInt, listOfLists);

        while(!isValid(slotTimes, flightTimes)){
            slotTimes = TestDataGenerator.pickRandomDatesWithProbabilities(slotProbs, numberOfSlotsInt, listOfLists);
            flightTimes = TestDataGenerator.pickRandomDatesWithProbabilities(flightProbs, numberOfFlightsInt, listOfLists);
        }
        slotTimes.sort(Comparator.naturalOrder());
        flightTimes.sort(Comparator.naturalOrder());
        System.out.println("Slots" + slotTimes);
        System.out.println("Flights" + flightTimes);

    }

    private static void checkInput(int numberOfFlights, int numberOfSlots, String timeWindowString) throws IOException {
        if((numberOfFlights >= 10) && (numberOfSlots >= 10) && numberOfSlots >= numberOfFlights && timeWindowString.matches("[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}->[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}")){
            LOGGER.info("Input is OK.");
        }
        else{
            throw new IOException("Input is not valid.");
        }
    }

    private static boolean isValid(List<LocalDateTime> slotTimes, List<LocalDateTime> flightTimes){
        List<LocalDateTime> internSlotTimes = new ArrayList<>(slotTimes);
        List<LocalDateTime> internFlightTimes = new ArrayList<>(flightTimes);

        internSlotTimes.sort(Comparator.naturalOrder());
        internFlightTimes.sort(Comparator.naturalOrder());

        List<LocalDateTime> ok = new ArrayList<>();

        for(LocalDateTime localDateTime : internFlightTimes){
            for(int i = 0; i < internSlotTimes.size(); i++){
                if(localDateTime.isBefore(internSlotTimes.get(i)) || localDateTime.isEqual(internSlotTimes.get(i))){
                    ok.add(localDateTime);
                    internSlotTimes.remove(i);
                    break;
                }
            }
        }
        System.out.println(ok.size() == flightTimes.size());
        return ok.size() == flightTimes.size();
    }

}
