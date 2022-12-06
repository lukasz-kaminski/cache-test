package cx.kaminski.cache;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class CappedCapacityLRUCache<K, V> extends AbstractMapBackedCache<K, V> {

    private final int maxCapacity;
    private final Clock clock;

    public CappedCapacityLRUCache(int maxCapacity, Clock clock) {
        super(new HashMap<>(maxCapacity));
        this.maxCapacity = maxCapacity;
        this.clock = clock;
    }

    @Override
    protected void put(K key, CachedObject<V> value) {
        if(map.size() == maxCapacity) {
            evictLeastRecentlyUsed();
        }
        value.setAccessTime(now());
        map.put(key, value);
    }

    @Override
    protected CachedObject<V> getCachedObject(K key) {
        CachedObject<V> foundObject = map.get(key);
        if(foundObject != null) {
            foundObject.setAccessTime(now());
        }
        return foundObject;
    }

    private void evictLeastRecentlyUsed() {
        map.entrySet().stream()
                .min(Comparator.comparing(entry -> entry.getValue().getAccessTime()))
                .map(Map.Entry::getKey)
                .ifPresent(map::remove);
    }

    private LocalDateTime now() {
        return LocalDateTime.ofInstant(clock.instant(), clock.getZone());
    }

}
