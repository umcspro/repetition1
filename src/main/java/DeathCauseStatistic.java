import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DeathCauseStatistic {
    private String icd10;
    private int[] ageDeaths;


    private DeathCauseStatistic(String icd10, int[] ageDeaths) {
        this.icd10 = icd10;
        this.ageDeaths = ageDeaths;
    }
    public String getIcd10() {
        return icd10;
    }

    //bez total
    public static DeathCauseStatistic fromCsvLine(String csvLine){
        String[] line = csvLine.split(",");
        String icd = line[0].trim();
        int[] ageDeaths = Arrays.stream(line).skip(2)
                .mapToInt(s -> s.equals("-") ? 0 : Integer.parseInt(s))
                .toArray();
        return new DeathCauseStatistic(icd,ageDeaths);
    }
    @Override
    public String toString() {
        return "DeathCauseStatistic{" +
                "icd10='" + icd10 + '\'' +
                ", ageDeaths=" + Arrays.toString(ageDeaths) +
                '}';
    }
    public record AgeBracketDeaths(int young, int old, int deathCount) {
    }
//    public class AgeBracketDeaths {
//        public final int young;
//        public final int old;
//        public final int deathCount;
//        public AgeBracketDeaths(int young, int old, int deathCount) {
//            this.young = young;
//            this.old = old;
//            this.deathCount = deathCount;
//        }
//        @Override
//        public String toString() {
//            return "AgeBracketDeaths{" +
//                    "young=" + young +
//                    ", old=" + old +
//                    ", deathCount=" + deathCount +
//                    '}';
//        }
//    }

//    public AgeBracketDeaths getAgeBracketDeaths(int age) {
//        //Najlepiej byloby utworzyc w konstruktorze gotowa liste, mozna ja tez wczytywac z pliku.
//        String header = "Kod,Razem,0 – 4,5 - 9,10 - 14,15 - 19,20 - 24,25 - 29,30 - 34,35 - 39,40 - 44,45 - 49,50 - 54,55 - 59,60 - 64,65 - 69,70 - 74,75 - 79,80 - 84,85 - 89,90 - 94,95 lat i więcej";
//        List<String> ageBrackets = Arrays.asList(header.split(",")).subList(2, header.split(",").length);
//
//            for (int i = 0; i < ageBrackets.size(); i++) {
//                String bracket = ageBrackets.get(i);
//                int start, end;
//                if (bracket.contains("+")) {
//                    start = Integer.parseInt(bracket.split(" ")[0]);
//                    end = Integer.MAX_VALUE;
//                } else {
//                    String[] parts = bracket.split(" – ");
//                    start = Integer.parseInt(parts[0]);
//                    end = Integer.parseInt(parts[1]);
//                }
//
//                if (age >= start && age <= end) {
//                    return new AgeBracketDeaths(start, end, ageDeaths[i]);
//                }
//            }
//            return null;  // Jeśli żaden przedział nie pasuje
//        }

                //Lambdowo:

//        private AgeBracketDeaths createAgeBracketDeaths(String bracket, int index) {
//            int start, end;
//            if (bracket.contains("+")) {
//                start = Integer.parseInt(bracket.split(" ")[0]);
//                end = Integer.MAX_VALUE;
//            } else {
//                String[] parts = bracket.split(" – ");
//                start = Integer.parseInt(parts[0]);
//                end = Integer.parseInt(parts[1]);
//            }
//            return new AgeBracketDeaths(start, end, ageDeaths[index]);
//        }
//    public AgeBracketDeaths getAgeBracketDeaths(int age) {
//        String header = "Kod,Razem,0 – 4,5 - 9,10 - 14,15 - 19,20 - 24,25 - 29,30 - 34,35 - 39,40 - 44,45 - 49,50 - 54,55 - 59,60 - 64,65 - 69,70 - 74,75 - 79,80 - 84,85 - 89,90 - 94,95 lat i więcej";
//        List<String> ageBrackets = Stream.of(header.split(","))
//                .skip(2)  // Pomija pierwsze dwa elementy
//                .collect(Collectors.toList());
//        return IntStream.range(0, ageBrackets.size())
//                .mapToObj(i -> createAgeBracketDeaths(ageBrackets.get(i), i))
//                .filter(abd -> age >= abd.young && age <= abd.old)
//                .findFirst()
//                .orElse(null);
//    }

    //bez totala z zalozeniem o niezmiennym przedziale:
    public AgeBracketDeaths getAgeBracketDeaths(Integer age) {
        int index = age >= 100 ? ageDeaths.length - 1 : age / 5;
        return new AgeBracketDeaths(
                index * 5,
                index == ageDeaths.length - 1 ? 999: index * 5 + 4,
                ageDeaths[index]
        );
    }

}
