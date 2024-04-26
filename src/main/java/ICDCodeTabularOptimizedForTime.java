import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ICDCodeTabularOptimizedForTime implements ICDCodeTabular{
    Map<String,String> diseases = new HashMap<>();
    @Override
    public String getDescription(String code) throws IndexOutOfBoundsException {
        //zamiast zwyklego ifa:
        String value = Optional.ofNullable(diseases.get(code))
                .orElseThrow(() -> new IndexOutOfBoundsException("BAD CODE!"));
        return value;
    }
    public ICDCodeTabularOptimizedForTime(String path) {
        try(Stream<String> lines = Files.lines(Path.of(path))){
            diseases = lines.skip(87)
                    .map(String::trim)
                    .filter(s->s.matches("[A-Z]\\d\\d.*"))
                    .map(s->s.split(" ",2))
                    .collect(Collectors.toMap(
                            sp-> sp[0],
                            sp-> sp[1].trim(),
                            (existingValue, newValue) -> existingValue));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
