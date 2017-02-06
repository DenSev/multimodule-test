import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Dzianis_Sevastseyenk on 02/01/2017.
 */
public class StreamReader {


    public static List<String> readOther2Csv(){
        List<String> urls;
        try(Stream<String> lines = Files.lines(Paths.get("D://other2.csv"))) {
            urls = lines
                .map((str)->{


                    return "http://qa-11-www.edmunds.com" + str;
                })
                .collect(Collectors.toList());
            return urls;
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public static Set<List<String>> readCsv(){
        Set<List<String>> makemodelyear;
        try(Stream<String> lines = Files.lines(Paths.get("D://mmy.csv"))) {
            makemodelyear = lines
                .map((str)->{
                    List<String> mmy= new ArrayList<>();

                    String source[] = str.split("/");
                    mmy.add(source[1]);
                    mmy.add(source[2]);
                    mmy.add(source[3]);
                    String[] subModelAndZip = source[4].split("\\?");
                    String subModel = subModelAndZip[0];
                    String[] zipAndTrash = subModelAndZip[1].split("\\&");
                    String zip = zipAndTrash[zipAndTrash.length -1];

                    mmy.add(subModel);
                    mmy.add(zip);

                    return mmy;
                })
                .collect(Collectors.toSet());

            return makemodelyear;
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public static List<List<String>> readOtherCSV(){

        List<List<String>> trimsMap;
        try(Stream<String> lines = Files.lines(Paths.get("D://other.csv"))) {
             trimsMap = lines
                .map((str)->{
                    List<String> keyVal = new ArrayList<>();
                    String source[] = str.split("/");
                    String key = source[0]+source[1]+source[2];
                    String value = source[3];
                    keyVal.add(key);
                    keyVal.add(value);

                    return keyVal;
                }).collect(Collectors.toList(
                ));

            return trimsMap;
        } catch (IOException e){
            throw new RuntimeException(e);
        }



    }


}
