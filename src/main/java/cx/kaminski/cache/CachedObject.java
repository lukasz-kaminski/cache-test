package cx.kaminski.cache;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@AllArgsConstructor
class CachedObject<T> {
    @Getter
    @Setter
    private LocalDateTime accessTime;
    @Getter
    private final T value;
}
