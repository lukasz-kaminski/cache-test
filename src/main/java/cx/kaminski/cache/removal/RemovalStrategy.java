package cx.kaminski.cache.removal;

import cx.kaminski.cache.CachedObject;

import java.util.Map;

public interface RemovalStrategy<K, V> {
    void removeOneFrom(Map<K, CachedObject<V>> map);
}
