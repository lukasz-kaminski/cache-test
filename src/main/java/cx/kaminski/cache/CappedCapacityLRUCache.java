package cx.kaminski.cache;

import java.time.Clock;
import java.time.LocalDateTime;
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
        LocalDateTime oldestTimeStamp = now();
        Object keyToRemove = null;
        for (Map.Entry<K, CachedObject<V>> entry : map.entrySet()) {
            LocalDateTime lastRetrievalTime = entry.getValue().getAccessTime();
            if(lastRetrievalTime.isBefore(oldestTimeStamp)) {
                oldestTimeStamp = lastRetrievalTime;
                keyToRemove = entry.getKey();
            }
        }
        if(keyToRemove != null) {
            map.remove(keyToRemove);
        }
    }

    private LocalDateTime now() {
        return LocalDateTime.ofInstant(clock.instant(), clock.getZone());
    }

}
