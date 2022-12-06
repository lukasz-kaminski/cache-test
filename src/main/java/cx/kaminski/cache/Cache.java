package cx.kaminski.cache;

import org.springframework.lang.NonNull;

public interface Cache<K, V> {
    V get(@NonNull K key);

    boolean contains(@NonNull K key);

    void put(@NonNull K key, @NonNull V value);

    V evict(@NonNull K key);

    void evictAll();
}
