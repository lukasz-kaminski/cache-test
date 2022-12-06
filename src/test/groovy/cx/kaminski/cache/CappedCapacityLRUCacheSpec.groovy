package cx.kaminski.cache

import spock.lang.Specification
import spock.lang.Subject
import spock.util.time.MutableClock

import java.time.Clock

class CappedCapacityLRUCacheSpec extends Specification {

    @Subject
    CappedCapacityLRUCache<String, String> cache = new CappedCapacityLRUCache<>(3, Clock.systemUTC())

    def 'should add to cache'() {
        when:
        cache.put('key1', 'value1')
        cache.put('key2', 'value2')

        then:
        cache.contains('key1')
        cache.contains('key2')
        cache.get('key1') == 'value1'
        cache.get('key2') == 'value2'
    }

    def 'should remove from cache'() {
        given:
        cache.put('key1', 'value1')
        cache.put('key2', 'value2')

        when:
        def evictedValue = cache.evict('key1')

        then:
        !cache.contains('key1')
        cache.get('key2') == 'value2'
        evictedValue == 'value1'
    }

    def 'should throw an exception when getting null key'() {
        when:
        cache.get(null)
        then:
        thrown IllegalArgumentException
    }

    def 'should throw an exception when putting null key'() {
        when:
        cache.put(null, 'test')
        then:
        thrown IllegalArgumentException
    }

    def 'should throw an exception when putting null value'() {
        when:
        cache.put('test', null)
        then:
        thrown IllegalArgumentException
    }

    def 'should evict all'() {
        given:
        cache.put('key1', 'value1')
        cache.put('key2', 'value2')
        cache.put('key3', 'value3')

        when:
        cache.evictAll()

        then:
        ['key1', 'key2', 'key3'].each {
            !cache.contains(it)
        }
    }

    def 'should evict least recently used after capacity is reached'() {
        given:
        Clock clock = new MutableClock()
        cache = new CappedCapacityLRUCache<>(3, clock)

        and:
        cache.put('key1', 'value1')
        clock.next()
        cache.put('key2', 'value2')
        clock.next()
        cache.put('key3', 'value3')
        clock.next()

        when:
        cache.get('key2')
        clock.next()
        cache.get('key1')
        clock.next()
        cache.get('key3')
        clock.next()
        cache.put('key4', 'value4')

        then:
        !cache.contains('key2')
        ['key1', 'key3', 'key4'].each {
            cache.contains(it)
        }
    }

    def 'should evict after reaching cap when no entry was retrieved'() {
        given:
        Clock clock = new MutableClock()
        cache = new CappedCapacityLRUCache<>(3, clock)

        and:
        cache.put('key1', 'value1')
        clock.next()
        cache.put('key2', 'value2')
        clock.next()
        cache.put('key3', 'value3')
        clock.next()

        when:
        cache.put('key4', 'value4')

        then:
        !cache.contains('key1')
        ['key2', 'key3', 'key4'].each {
            cache.contains(it)
        }
    }

}
