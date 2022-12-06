package cx.kaminski.cache;

import cx.kaminski.cache.removal.RemovalStrategy;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public abstract class MapBasedCache<K,V> {
    protected final Map<K, CachedObject<V>> map;

    protected final RemovalStrategy<K, V> removalStrategy;

    public void put(K key, V value) {
        if(key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        put(key, new CachedObject<>(value));
    }

    public V get(K key) {
        if(key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        return map.get(key).getValue();
    }

    abstract void put(K key, CachedObject<V> value);

    public V remove(K key) {
        if(key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        return map.remove(key).getValue();
    }

    public void evict() {
        map.clear();
    }

}
