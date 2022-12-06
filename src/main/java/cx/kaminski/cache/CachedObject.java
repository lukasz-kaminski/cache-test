package cx.kaminski.cache;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class CachedObject<T> {
    @Getter
    private LocalDateTime lastRetrievalTime = LocalDateTime.now();
    private final T value;

    T getValue() {
        lastRetrievalTime = LocalDateTime.now();
        return value;
    }
}
