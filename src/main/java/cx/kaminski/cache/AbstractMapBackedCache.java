package cx.kaminski.cache;

import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Optional;

/**
 * This is a base for map based cache implementations.
 * It takes care of wrapping/unwrapping values with {@link CachedObject}
 * and keeps key and value from being null.
 *
 *
 *
 * @param <K> key type
 * @param <V> value type
 */
@RequiredArgsConstructor
abstract class AbstractMapBackedCache<K,V> implements Cache<K, V> {

    protected final Map<K, CachedObject<V>> map;

    @Override
    public void put(K key, V value) {
        if(key == null || value == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        put(key, new CachedObject<>(value));
    }

    @Override
    public V get(K key) {
        if(key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        return Optional.ofNullable(getCachedObject(key)).map(CachedObject::getValue).orElse(null);
    }

    @Override
    public boolean contains(K key) {
        if(key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        return containsKey(key);
    }

    @Override
    public V evict(K key) {
        if(key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        return Optional.ofNullable(evictCachedObject(key)).map(CachedObject::getValue).orElse(null);
    }

    @Override
    public void evictAll() {
        map.clear();
    }

    protected void put(K key, CachedObject<V> value) {
        map.put(key, value);
    }

    protected CachedObject<V> getCachedObject(K key) {
        return map.get(key);
    }

    protected CachedObject<V> evictCachedObject(K key) {
        return map.remove(key);
    }

    protected boolean containsKey(K key) {
        return map.containsKey(key);
    }

}
