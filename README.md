# Info

This is a simple cache implementation, currently there's one removal strategy and one concrete cache class.
`CapacityMapBasedCache` holds given max amount of entries before it starts removing them.
`OldestUsedRemovalStrategy` removes "oldest" element in map in terms of being last used.

# Considerations
 
- It's a simple not-optimal solution, especially the `OldestRemovalStrategy`
- It's a Spring Boot app because I had already set it up and wanted to save time 
- It was agreed to purposely not include advanced synchronisation in order to simplify solution and save time
- There are safeguards missing in `get` and `remove` for when there is no given key

# TODOs

Next I'd focus on:

1. Testing the cache
1. Rearranging classes and tests or refactoring `RemovalStrategy` so that `CachedObject` isn't public, it's an implementation detail
1. Implementing and testing `ExpirableMapBasedCache` that has a separate thread for periodical eviction of expired cache entries