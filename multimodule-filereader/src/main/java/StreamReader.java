import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Dzianis_Sevastseyenk on 02/01/2017.
 */
public class StreamReader {


    public static List<String> readOther2Csv(String path, Function<String, String> function) {
        List<String> urls;
        try (Stream<String> lines = Files.lines(Paths.get(path))) {
            urls = lines
                .map(function)
                .collect(Collectors.toList());
            return urls;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
