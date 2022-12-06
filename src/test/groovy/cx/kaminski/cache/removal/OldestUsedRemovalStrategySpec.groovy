package cx.kaminski.cache.removal

import cx.kaminski.cache.CachedObject
import spock.lang.Specification
import spock.lang.Subject

class OldestUsedRemovalStrategySpec extends Specification {

    @Subject
    RemovalStrategy<String, String> removalStrategy = new OldestUsedRemovalStrategy<>()

    def 'should remove oldest cache object'() {
        given:
        Map<String, CachedObject<String>> map = new HashMap<>([
                '1': new CachedObject<>('some value'),
                '2': new CachedObject<>('to delete'),
                '3': new CachedObject<>('some other value'),
        ])
        and: 'second one is the oldest used'
        map.get('2').getValue()
        map.get('1').getValue()
        map.get('3').getValue()

        when:
        removalStrategy.removeOneFrom(map)

        then:
        map.values().collect {it.getValue()}.toList() == [
                'some value', 'some other value'
        ]
    }

    def 'should not throw exception when removing unused element'() {
        given:
        Map<String, CachedObject<String>> map = new HashMap<>([
                '1': new CachedObject<>('to delete'),
                '2': new CachedObject<>('some value'),
                '3': new CachedObject<>('some other value'),
        ])

        when:
        removalStrategy.removeOneFrom(map)

        then:
        noExceptionThrown()
        map.values().collect {it.getValue()}.toList() == [
                'some value', 'some other value'
        ]
    }
}
