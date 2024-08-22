package org.nway.impl;

import org.nway.Entry;
import org.nway.Policy;

import java.util.Random;

/**
 * Least Recent Used Cache Replacement Policy
 */
public class RandomPolicy<V> implements Policy<V> {
    Random random = new Random();

    @Override
    public synchronized int evictEntryIndex(Entry<V>[] entries, int startIndex, int endIndex) {
        return random.nextInt(entries.length);
    }
}
