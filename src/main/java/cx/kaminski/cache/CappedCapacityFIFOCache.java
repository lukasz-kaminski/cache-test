package cx.kaminski.cache;

import java.util.LinkedHashMap;
import java.util.Map;

public class CappedCapacityFIFOCache<K, V> extends AbstractMapBackedCache<K, V> {

    public CappedCapacityFIFOCache(int maxCapacity) {
        super(new LinkedHashMap<>() {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, CachedObject<V>> eldest) {
                return size() > maxCapacity;
            }
        });
    }
}
