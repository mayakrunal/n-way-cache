package org.nway;

import org.nway.impl.NWayCache;

public class CacheFactory {
    public static <K, V> Cache<K, V> newCache(int associativity, int numSets) {
        return new NWayCache<>(associativity, numSets);
    }

    public static <K, V> Cache<K, V> newCache(int associativity, int numSets, Policy<V> evictionPolicy) {
        return new NWayCache<>(associativity, numSets, evictionPolicy);
    }
}
