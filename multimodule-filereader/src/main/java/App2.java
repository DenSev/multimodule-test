import java.util.List;

/**
 * Created by Dzianis_Sevastseyenk on 04/13/2017.
 */
public class App2 {

    public static void main(String... args){
        List<String> urls = StreamReader.readOther2Csv("D:/franchises.txt", (line)->{
            line = line.split(":")[1];
            line = line.substring(2, line.length()-1);
            line = "" + line;
            return line;
        });

        for (String line : urls) {
            System.out.println(line);
        }
    }
}
