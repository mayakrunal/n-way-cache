package org.nway.impl;

import org.nway.Entry;
import org.nway.Policy;

/**
 * Most Recent Used Cache Replacement Policy
 */
public class MRUPolicy<V> implements Policy<V> {
    @Override
    public synchronized int evictEntryIndex(Entry<V>[] entries, int startIndex, int endIndex) {
        int index = 0;
        long latestTime = 0;
        for (int i = 0; i < entries.length; i++) {
            if (entries[i].isOccupied() && entries[i].getTimestamp() > latestTime) {
                latestTime = entries[i].getTimestamp();
                index = i;
            }
        }
        return index;
    }
}

