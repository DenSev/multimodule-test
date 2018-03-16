import org.apache.commons.lang3.tuple.Pair;
import org.elasticsearch.action.admin.cluster.shards.ClusterSearchShardsRequest;
import org.elasticsearch.action.admin.cluster.shards.ClusterSearchShardsResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.elasticsearch.index.query.QueryBuilders.termsQuery;

/**
 * Created by Dzianis_Sevastseyenk on 04/13/2017.
 */
public class App2 {

    public static void main(String... args) throws Exception {
        Client client = new ElasticsearchClientProvider()
            .init("es-lease-deals-2-qa-21", "es-lease-deals-2.qa-21.vip.aws2", 9300);

        //870, 1417, 1803151, 1544
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
            .query(termsQuery("dealer.rooftopId", "870", "1417", "1803151", "1544"))
            .size(100)
            .fetchSource(true);

        Map<String, String> routingToShardsMap = getRoutingToShardsMap(client);

        ExecutorService executorService = Executors.newFixedThreadPool(8);
        ConcurrentSkipListSet<String> ids = new ConcurrentSkipListSet<String>();
        for (Entry<String, String> map : routingToShardsMap.entrySet()) {
            executorService.submit(() -> {

                try {
                    SearchRequest searchRequest = new SearchRequest(new String[]{"lease-deals"}, searchSourceBuilder).types("lease-deal");
                    searchRequest.scroll(TimeValue.timeValueMinutes(5)).routing(map.getValue());
                    SearchResponse searchResponse = client.search(searchRequest).get();

                    do {
                        for (SearchHit hit : searchResponse.getHits().getHits()) {
                            if (hit.getSource() != null
                                && !hit.getSource().isEmpty()) {
                                Object node = getNode(hit.getSource(), "leaseDeals");
                                if (((ArrayList) node).size() < 11) {
                                    System.out.println(hit.getId() + " " + getNode(hit.getSource(), "dealer", "rooftopId") + " " + ((ArrayList) node).size()  );
                                }
                            }
                        }
                        SearchScrollRequest scrollRequest = new SearchScrollRequest(searchResponse.getScrollId());
                        scrollRequest.scroll(new Scroll(TimeValue.timeValueMinutes(5)));
                        searchResponse = client.searchScroll(scrollRequest).actionGet();

                    } while (searchResponse.getHits().getHits().length != 0);

                } catch (InterruptedException | ExecutionException e) {

                }


            });
        }
        executorService.shutdown();
    }


    /**
     * Returns first element for key from provided collection of pairs, while also removing it, or null if there
     * are no elements with such key. collection provided in param will have its state adjusted.
     *
     * @param collection - collection of pairs
     * @param key        - key of pair
     * @return - value of pair for key in params or null if no pair with such key was found
     */
    public static String removeValueForKey(Collection<Pair<String, String>> collection, String key) {
        /*if (CollectionUtils.isNotEmpty(collection)) {
            Iterator<Pair<String, String>> iterator = collection.iterator();
            while (iterator.hasNext()) {
                Pair<String, String> pair = iterator.next();
                if (key.equals(pair.getKey())) {
                    iterator.remove();
                    return pair.getValue();
                }
            }
        }*/
        return null;
    }

    /**
     * Same as {@code removeValueForKey}, but returns default value instead of null
     *
     * @param collection   - collection of pairs
     * @param key          - key of pair
     * @param defaultValue - defaultValue to be returned if no such key exists in collection
     * @return - value of pair for key in params or defaultValue if no pair with such key was found
     */
    public static String removeValueForKeyOrGetDefault(Collection<Pair<String, String>> collection, String key, String defaultValue) {
        String value = removeValueForKey(collection, key);
        return value != null ? value : defaultValue;
    }

    /**
     * Retrieves value from nested map of maps, fields param represents path to value
     *
     * @param node   map of maps
     * @param fields - field path, must be sequence of map key passed in order of map nesting
     * @return - node value or null
     */
    public static Object getNode(Map node, String... fields) {
        Object childNode = node.get(fields[0]);
        for (int i = 1; i < fields.length; i++) {
            if (childNode != null) {
                childNode = ((Map) childNode).get(fields[i]);
            }
        }
        return childNode;
    }

    public static void putNodeValue(Map node, Object value, String... fields) {
        Object childNode = node.get(fields[0]);
        for (int i = 1; i < fields.length - 1; i++) {
            if (childNode != null) {
                childNode = ((Map) childNode).get(fields[i]);
            }
        }
        if (childNode != null) {
            ((Map) childNode).put(fields[fields.length - 1], value);
        }
    }

    /**
     * Pulls value from nested map of maps, fields param represents path to value
     *
     * @param node   map of maps
     * @param fields - field path, must be sequence of map key passed in order of map nesting
     * @return - node value or null
     */
    public static Object getAndRemoveNode(Map node, String... fields) {
        Object childNode = node.get(fields[0]);
        for (int i = 1; i < fields.length; i++) {
            if (childNode != null) {
                if (i == fields.length - 1) {
                    childNode = ((Map) childNode).remove(fields[i]);
                } else {
                    childNode = ((Map) childNode).get(fields[i]);
                }
            }
        }
        return childNode;
    }

    private static Map<String, String> getRoutingToShardsMap(Client client) throws InterruptedException, ExecutionException {
        int shardsNumber = client.admin().cluster().prepareSearchShards("lease-deals").get().getGroups().length;

        Map<String, String> shardToRoutingIndexMap = new HashMap<>(shardsNumber);
        int routingIndex = 0;

        while (shardToRoutingIndexMap.size() < shardsNumber) {
            String routingParam = String.valueOf(routingIndex);
            ClusterSearchShardsResponse clusterSearchShardsResponse = client.admin().cluster()
                .searchShards(new ClusterSearchShardsRequest("lease-deals").routing(routingParam)).get();
            routingIndex++;

            int shardIndex = clusterSearchShardsResponse.getGroups()[0].getShards()[0].getId();
            shardToRoutingIndexMap.put(String.valueOf(shardIndex), routingParam);
        }

        return shardToRoutingIndexMap;
    }

    public void test(String... args) {
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
