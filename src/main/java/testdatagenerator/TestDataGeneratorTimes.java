package testdatagenerator;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestDataGeneratorTimes {

    private TestDataGeneratorTimes(){super();}

    public static final Random random = new Random();

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

    public static LocalDateTime pickRandomDate(List<LocalDateTime> dateTimeList){
        return dateTimeList.get(random.nextInt(dateTimeList.size()-1));
    }
}
