import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DeathCauseStatisticsList {

    List<DeathCauseStatistic> deathCauseStatistics = new ArrayList<>();
    public void repopulate(String path){
        try(Stream<String> fileLines = Files.lines(Path.of(path))) {
            deathCauseStatistics = fileLines.skip(2)
                    .map(DeathCauseStatistic::fromCsvLine)
                    .toList();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<DeathCauseStatistic> mostDeadlyDiseases(int age,int n) {
        //
        return deathCauseStatistics.stream()
                .sorted( (o1,o2)->Integer.compare(o2.getAgeBracketDeaths(age).deathCount(),
                                o1.getAgeBracketDeaths(age).deathCount()))
                .limit(n)
                .collect(Collectors.toList());
    }

    public List<DeathCauseStatistic> getDeathCauseStatistics() {
        return deathCauseStatistics;
    }
}