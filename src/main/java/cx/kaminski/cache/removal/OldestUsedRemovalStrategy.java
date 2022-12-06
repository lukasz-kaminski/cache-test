package cx.kaminski.cache.removal;

import cx.kaminski.cache.CachedObject;

import java.time.LocalDateTime;
import java.util.Map;

public class OldestUsedRemovalStrategy<K, V> implements RemovalStrategy<K, V> {
    @Override
    public void removeOneFrom(Map<K, CachedObject<V>> map) {
        LocalDateTime oldestTimeStamp = LocalDateTime.now();
        K keyToRemove = null;
        for (Map.Entry<K, CachedObject<V>> entry : map.entrySet()) {
            LocalDateTime lastRetrievalTime = entry.getValue().getLastRetrievalTime();
            if(lastRetrievalTime.isBefore(oldestTimeStamp)) {
                oldestTimeStamp = lastRetrievalTime;
                keyToRemove = entry.getKey();
            }
        }
        if(keyToRemove != null) {
            map.remove(keyToRemove);
        }
    }
}
