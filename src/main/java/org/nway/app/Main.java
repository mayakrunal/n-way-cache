package org.nway.app;

import org.nway.Cache;
import org.nway.CacheFactory;
import org.nway.impl.LFUPolicy;
import org.nway.impl.MRUPolicy;
import org.nway.impl.RandomPolicy;

public class Main {
    public static void main(String[] args) {
        Cache<Integer, String> cache = CacheFactory.newCache(2, 2);
        cache.put(1, "a");
        cache.put(2, "b");
        cache.put(3, "c");
        cache.put(4, "d");
        cache.get(1);

        //default is LRU so should replace (2,"b") with (5,"e")
        cache.put(5, "e");

        cache = CacheFactory.newCache(2, 2, new MRUPolicy<>());
        cache.put(1, "a");
        cache.put(2, "b");
        cache.put(3, "c");
        cache.put(4, "d");
        cache.get(1);

        //default is LRU so should replace (1,"a") with (5,"e")
        cache.put(5, "e");

        cache = CacheFactory.newCache(2, 2, new LFUPolicy<>());
        cache.put(1, "a");
        cache.put(2, "b");
        cache.put(3, "c");
        cache.put(4, "d");
        cache.get(1);
        cache.get(2);

        //default is LRU so should replace (3,"c") with (5,"e")
        cache.put(5, "e");


        cache = CacheFactory.newCache(2, 2, new RandomPolicy<>());
        cache.put(1, "a");
        cache.put(2, "b");
        cache.put(3, "c");
        cache.put(4, "d");

        //should replace random
        cache.put(5, "e");

    }
}