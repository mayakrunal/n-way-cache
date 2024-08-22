package org.nway;

import org.junit.jupiter.api.Test;
import org.nway.impl.MRUPolicy;

import static org.junit.jupiter.api.Assertions.assertNull;

class CacheTest {

    private final static int ASSOCIATIVITY = 2;
    private final static int SETS = 3;

    private void populateCache(Cache<Integer, String> cache) {
        char val = 'A';
        for (int i = 1; i <= ASSOCIATIVITY * SETS; i++) {
            cache.put(i, String.valueOf(val));
            val++;
        }
    }

    private int getSetNo(int hashKey) {
        return hashKey % SETS;
    }

    @Test
    void testLRUPolicy() {
        Cache<Integer, String> cache = CacheFactory.newCache(ASSOCIATIVITY, SETS);
        populateCache(cache);

        System.out.println(cache);

        System.out.printf("Key: %s will be assigned to SetNo: %s\n", 7, getSetNo(7));
        //default is LRU so should replace (1,"A") with (7,"Test")
        cache.put(7, "Test");

        System.out.println(cache);

        // assert that 2 is not there
        assertNull(cache.get(1));
    }

    @Test
    void testMRUPolicy() {
        Cache<Integer, String> cache = CacheFactory.newCache(ASSOCIATIVITY, SETS, new MRUPolicy<>());
        populateCache(cache);
        cache.get(1);

        System.out.println(cache);

        System.out.printf("Key: %s will be assigned to SetNo: %s\n", 7, getSetNo(7));


        //default is LRU so should replace (1,"a") with (7,"g")
        cache.put(7, "Test");

        System.out.println(cache);
        // assert that 2 is not there
        assertNull(cache.get(1));
    }

}