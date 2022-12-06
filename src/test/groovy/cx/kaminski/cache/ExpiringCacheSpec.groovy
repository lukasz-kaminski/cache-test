package cx.kaminski.cache

import spock.lang.Specification
import spock.lang.Subject
import spock.util.time.MutableClock

import java.time.Clock
import java.time.Duration
import java.time.LocalTime

class ExpiringCacheSpec extends Specification {

    static final Duration CACHE_TTL = Duration.ofSeconds(3)
    static final Duration ETERNITY = Duration.ofDays(999)

    Clock clock = new MutableClock()

    @Subject
    ExpiringCache<String, String> cache = new ExpiringCache<>(
            CACHE_TTL,
            ETERNITY, //dirty hack so the timer never actually runs, but the need to use this might mean a design flaw
            clock
    )

    def 'should expire cache after set time'() {
        given:
        cache.put('key1', 'value1')
        cache.put('key2', 'value2')
        clock + Duration.ofSeconds(1)
        cache.put('key3', 'value3')
        cache.put('key4', 'value4')
        clock + Duration.ofMillis(2500) //2,5s

        when:
        cache.cleanCache()

        then:
        !cache.contains('key1')
        !cache.contains('key2')
        cache.contains('key3')
        cache.contains('key4')

    }
}
