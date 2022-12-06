package cx.kaminski.cache;

import cx.kaminski.cache.removal.RemovalStrategy;

import java.util.Map;

public class CapacityMapBasedCache<K, V> extends MapBasedCache<K, V> {

    private final int maxCapacity;

    public CapacityMapBasedCache(Map<K, CachedObject<V>> map, RemovalStrategy<K, V> removalStrategy, int maxCapacity) {
        super(map, removalStrategy);
        this.maxCapacity = maxCapacity;
    }

    @Override
    void put(K key, CachedObject<V> value) {
        if(map.size() == maxCapacity) {
            removalStrategy.removeOneFrom(map);
        }
        map.put(key, value);
    }

    @Override
    public V get(K key) {
        if(key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        return map.get(key).getValue();
    }

}
