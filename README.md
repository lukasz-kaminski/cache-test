# Info

This is a simple cache implementation with following possible strategies:
- `CappedCapacityLRUCache` - after reaching capacity removes least recently used entry
- `CappedCapacityFIFOCache` - after reaching capacity removes entries in insertion order
- `ExpiringCache` - entries have configurable time to live and are evicted after that time

# Considerations
 
- It's a simple not-optimal solution
- It's a Spring Boot app because I had already set it up and wanted to save time 
- It was agreed to purposely not include advanced synchronisation in order to simplify solution and save time
