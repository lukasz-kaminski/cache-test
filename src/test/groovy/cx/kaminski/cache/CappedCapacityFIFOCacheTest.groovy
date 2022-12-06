package cx.kaminski.cache

import spock.lang.Specification
import spock.lang.Subject

class CappedCapacityFIFOCacheTest extends Specification {

    @Subject
    CappedCapacityFIFOCache<String, String> cache = new CappedCapacityFIFOCache<>(3)

    def 'should evict in order of addition'() {
        given:
        cache.put('key1', 'value1')
        cache.put('key2', 'value2')
        cache.put('key3', 'value3')

        when:
        cache.put('key4', 'value4')

        then:
        !cache.contains('key1')
        cache.get('key4') == 'value4'
    }

    def 'should evict in order of addition regardless of usage'() {
        given:
        cache.put('key1', 'value1')
        cache.put('key2', 'value2')
        cache.put('key3', 'value3')

        when:
        cache.get('key1')
        cache.get('key3')

        and:
        cache.put('key4', 'value4')

        then:
        !cache.contains('key1')
        cache.get('key4') == 'value4'
    }
}
