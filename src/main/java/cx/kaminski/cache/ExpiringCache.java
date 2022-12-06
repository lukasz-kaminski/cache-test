package cx.kaminski.cache;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

public class ExpiringCache<K, V> extends AbstractMapBackedCache<K, V> {

    private final Clock clock;
    private final Duration cacheTTL;
    private final Timer timer = new Timer();


    // starting the timer right from the constructor might not be the best idea
    // it can be done for example via some factory
    public ExpiringCache(Duration cacheTTL, Duration evictionInterval, Clock clock) {
        super(new ConcurrentHashMap<>());
        this.clock = clock;
        this.cacheTTL = cacheTTL;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                cleanCache();
            }
        }, 0, evictionInterval.toMillis());
    }

    @Override
    protected void put(K key, CachedObject<V> value) {
        value.setAccessTime(LocalDateTime.ofInstant(clock.instant(), clock.getZone()));
        map.put(key, value);
    }

    // finalize is deprecated and unsafe, this needs changing
    @Override
    protected void finalize() throws Throwable {
        try {
            timer.cancel();
        } finally {
            super.finalize();
        }
    }

    private void cleanCache() {
        LocalDateTime now = LocalDateTime.ofInstant(clock.instant(), clock.getZone());
        map.values().removeIf(cachedObject -> cachedObject.getAccessTime().plus(cacheTTL).isBefore(now));
    }

}
