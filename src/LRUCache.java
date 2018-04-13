import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache {

    final static int MAX_ENTRIES = 5;

    static LinkedHashMap<String, String> LRU_CACHE = new LinkedHashMap<String,
            String>(MAX_ENTRIES + 1, .75F, false) {
        protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
            return size() > MAX_ENTRIES;
        }
    };


    public static void main(String[] args) {
        add("key1", "value1");
        add("key2", "value2");
        add("key3", "value3");
        add("key2", "value2");
        add("key5", "value5");
        add("key4", "value4");
        add("key6", "value6");
        get("key2");
        add("key7", "value7");
        add("key8", "value8");
        add("key9", "value9");
        add("key10", "value10");


        System.out.println(LRU_CACHE);
    }

    static void add(String key, String value) {
        LRU_CACHE.put(key, value);
    }

    // key에 해당하는 값을 찾아 반환. 없으면 Exception
    static String get(String key) {
        add(key, LRU_CACHE.get(key));

        return LRU_CACHE.get(key);
    }

    // key에 해당하는 값을 찾아 반환. 없으면 Exception
    static void remove(String key) {
        LRU_CACHE.remove(key);
    }

    // add나 get된지 오래된 값을 자료구조에서 제거

}
