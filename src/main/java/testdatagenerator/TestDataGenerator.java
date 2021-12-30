package testdatagenerator;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestDataGenerator {

    public static Random random = new Random();

    public static List<LocalDateTime> generateTimeList(LocalDateTime start, LocalDateTime end){
        Duration duration = Duration.between(start, end);
        int minutes = (int) duration.getSeconds()/60;
        List<LocalDateTime> timeList = new ArrayList<>();

        for(int i = 0; i < minutes; i++){
            timeList.add(start);
            start = start.plus(1, ChronoUnit.MINUTES);
        }
        return timeList;
    }

    public static List<LocalDateTime> pickRandomDatesWithProbabilities(int [] probs, int counter, List<List<LocalDateTime>> listOfLists){
        List<Integer> dezils = pickRandomDezilWithProbabilities(probs, counter);
        List<LocalDateTime> timeListDezil = new ArrayList<>();
        for(int i : dezils){
            List<LocalDateTime> dezil = listOfLists.get(i-1);
            timeListDezil.add(dezil.get(random.nextInt(dezil.size())));
        }
        return timeListDezil;
    }

    private static List<Integer> pickRandomDezilWithProbabilities(int [] probs, int counter){
        List<Integer> list = new ArrayList<>();
        int index = 0;
        for(int i = 0; i < counter; i++){
            index = random.nextInt(probs.length);
            list.add(probs[index]);
        }
        return list;
    }
}
