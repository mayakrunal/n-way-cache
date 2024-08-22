package org.nway.impl;

import org.nway.Entry;
import org.nway.Policy;

/**
 * Least Recent Used Cache Replacement Policy
 */
public class LRUPolicy<V> implements Policy<V> {
    @Override
    public synchronized int evictEntryIndex(Entry<V>[] entries, int startIndex, int endIndex) {
        int index = 0;
        long earliestTime = System.nanoTime();
        for (int i = startIndex; i <= endIndex; i++) {
            if (entries[i].isOccupied() && entries[i].getTimestamp() < earliestTime) {
                earliestTime = entries[i].getTimestamp();
                index = i;
            }
        }
        return index;
    }
}
