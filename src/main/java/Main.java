import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        DeathCauseStatisticsList deathCauseStatistics  = new DeathCauseStatisticsList();
        deathCauseStatistics.repopulate("./zgony.csv");
//        deathCauseStatistics.mostDeadlyDiseases(3,3)
//                .forEach(System.out::println);

        DeathCauseStatistic  df = deathCauseStatistics.getDeathCauseStatistics().get(0);
        System.out.println(df.getAgeBracketDeaths(100));


        ICDCodeTabularOptimizedForMemory icdmemory
                = new ICDCodeTabularOptimizedForMemory("./icd10.txt");

        ICDCodeTabularOptimizedForTime icdtime
                = new ICDCodeTabularOptimizedForTime("./icd10.txt");

        System.out.println("MEMORY:");
        System.out.println(icdmemory.getDescription("C00"));
        System.out.println("TIME:");
        System.out.println(icdtime.getDescription("C00"));
    }
}
