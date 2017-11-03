/**
 * Created by Dzianis_Sevastseyenk on 04/13/2017.
 */
public class App2 {

    public static void main(String... args) {
        /*Integer test = 0;


        System.out.println((double) test == 0.0);

        Optional<Integer> num = Stream.iterate(0, n-> n+1)
            .filter(number -> number % 2 == 0)
            .filter(n -> n >= 5)
            .map(n->n*2)
            .limit(20)
            .findAny();

        System.out.println(num);
*/
        String s1 = "1234567890";

        String s2 = "67890";

        System.out.println(s1.substring(0, s1.length() - s2.length()));


        /*List<String> urls = StreamReader.readOther2Csv("D:/test.txt", (line) -> {
            List<String> urlParts = new ArrayList<>();

            for( String s: line.split("\\?")){
                Collections.addAll(urlParts, s.split("&"));
            }
            Collections.sort(urlParts);

            StringBuilder sb = new StringBuilder(urlParts.get(0));
            sb.append("?").append(urlParts.get(1));
            for (int i = 2; i< urlParts.size(); i++){
                sb.append("&").append(urlParts.get(i));
            }
            return sb.toString();
        });


        Set<String> set = new HashSet<>(urls);

        for (String s: set){
            System.out.println(s);
        }*/
/*
        String fields1 = "make:model:year";
        String fields2 = "make:model:subModel";
        String fields3 = "util:ooo";
        String[] fields = {fields1, fields2, fields3};
        Set<Entry<String, List<String>>> fieldsTree = new HashSet<>();

        System.out.println(5000 % 1024);
        System.out.println(5000 / 1024);


        Integer[] ints = {0, 1, 2, 3, 4, 5, 6};

        List<Integer> intList = Arrays.asList(ints);


        Lists.partition(intList, 3).stream()
            .forEach(list -> System.out.println(list));
        for (int i = 0; i < ints.length / 3; i++) {
            System.out.println(Arrays.asList(ints).subList(i * 3, (i + 1) * 3));
        }*/


       /* for (String item : fields) {
            String[] splitFields = item.split(":");
            int groupsCount = 2;
            for (int i = 0; i < splitFields.length; i++) {
                String[] ff = item.split(":", groupsCount);

                List<String> parentPath = new ArrayList<>();
                for (int j = 0; j < i; j++) {

                    parentPath.add(splitFields[j]);
                }
                fieldsTree.add(new SimpleEntry<String, List<String>>(ff[i], parentPath));
                groupsCount++;
            }
        }

        List<Entry<String, List<String>>> list = Lists.newArrayList(fieldsTree);
        list.sort(Comparator.comparingInt(o -> o.getValue().size()));

        Map<String, Object> map = new HashMap<>();


        for (Entry<String, List<String>> entry : list) {
            if (entry.getValue().size() == 0 && !map.containsKey(entry.getKey())) {
                map.put(entry.getKey(), new HashMap<>());
            }
            Iterator<String> keyIndex = entry.getValue().iterator();
            if (keyIndex.hasNext()) {
                String key1 = keyIndex.next();
                Map<String, Object> childMap = (Map) map.get(key1);

                while (keyIndex.hasNext()) {
                    String key = keyIndex.next();
                    childMap = (Map) childMap.get(key);
                }
                childMap.put(entry.getKey(), new HashMap<>());
            }
        }


        // Assert.assertEquals(map, expectedTree);
        System.out.println(map.toString());

        System.out.println(list.toString());
*/
        /*for (String line : urls) {
            System.out.println(line);
        }*/
    }


}
