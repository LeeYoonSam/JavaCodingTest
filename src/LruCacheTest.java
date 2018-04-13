import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class LruCacheTest<K, V> {
    private final LinkedHashMap<K, V> map;

    private int size;
    private int maxSize;
    private int putCount;
    private int createCount;
    private int evictionCount;
    private int hitCount;
    private int missCount;

    public LruCacheTest(int maxSize) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        this.maxSize = maxSize;
        this.map = new LinkedHashMap<K, V>(0, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > maxSize;
            }
        };
    }

    public synchronized final V get(K key) {
        if(key == null) {
            throw new NullPointerException("key == null");
        }

        V result = map.get(key);

        // 키에 해당하는 값을 찾음
        if(result != null) {
            hitCount ++;
            return result;
        }

        // 키에 해당하는 값이 없으면 미스 추가
        missCount ++;

        result = create(key);

        if(result != null) {
            createCount ++;

            size += safeSizeOf(key, result);
            map.put(key, result);
            trimToSize(maxSize);
        }

        return result;
    }

    public synchronized  final V put(K key, V value) {
        if(key == null || value == null) {
            throw new NullPointerException("key == null || value == null");
        }

        putCount ++;

        size += safeSizeOf(key, value);
        V previous = map.put(key, value);
        if(previous != null) {
            size -= safeSizeOf(key, previous);
        }

        trimToSize(maxSize);
        return previous;
    }

    private void trimToSize(int maxSize) {
        while(size > maxSize && !map.isEmpty()) {
            // 마지막 아이템 추출
            Map.Entry<K, V> toEvict = map.entrySet().iterator().next();

            if(toEvict == null) {
                break;
            }

            K key = toEvict.getKey();
            V value = toEvict.getValue();

            map.remove(key);
            size -= safeSizeOf(key, value);
            evictionCount ++;

            entryEvicted(key, value);

        }
    }

    // 맵 초기화
    private void clear() {
        map.clear();
    }

    // value 존재하는지 체크
    public final boolean containsValue(V value) {
        return map.containsKey(value);
    }

    // 모든 entrySet 객체 반환
    public final Set<Map.Entry<K,V>> entrySet() {
        return map.entrySet();
    }

    // 모든 키값을 가진 set 객체 반환
    public final Set<K> keySet() {
        return map.keySet();
    }

    public final V remove(K key) {
        if(key == null) {
            throw new NullPointerException("key == null");
        }

        V previous = map.remove(key);
        if (previous != null) {
            size -= safeSizeOf(key, previous);
        }

        return previous;
    }

    private int safeSizeOf(K key, V value) {
        int result = sizeOf(key, value);

        if(result < 0) {
            throw new IllegalStateException("Negative size: " + key + "=" + value);
        }

        return result;
    }
    protected void entryEvicted(K key, V value) {}

    protected V create(K key) {
        return null;
    }

    protected int sizeOf(K key, V value) {
        return 1;
    }

    public synchronized  final void evictAll() {
        trimToSize(-1);
    }

    public synchronized  final int size() {
        return size;
    }

    public synchronized final int smaxSze() {
        return maxSize;
    }

    public synchronized final int hitCount() {
        return hitCount;
    }

    public synchronized final int missCount() {
        return missCount;
    }

    public synchronized final int createCount() {
        return createCount;
    }

    public synchronized final int putCount() {
        return putCount;
    }

    public synchronized final int evictionCount() {
        return evictionCount;
    }

    public synchronized final Map<K, V> snapshot() {
        return new LinkedHashMap<>(map);
    }

    @Override public synchronized final String toString() {
        int accesses = hitCount + missCount;
        int hitPercent = accesses != 0 ? (100 * hitCount / accesses) : 0;
        return String.format("LruCache[maxSize=%d,hits=%d,misses=%d,hitRate=%d%%]",
                maxSize, hitCount, missCount, hitPercent);
    }
}
