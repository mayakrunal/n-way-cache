package org.nway.impl;


import org.nway.Entry;
import org.nway.Policy;

// least frequently used policy
public class LFUPolicy<V> implements Policy<V> {
    @Override
    public synchronized int evictEntryIndex(Entry<V>[] entries, int startIndex, int endIndex) {
        int index = 0;
        long lowestFrequency = Long.MAX_VALUE;
        for (int i = startIndex; i <= endIndex; i++) {
            if (entries[i].isOccupied()) {
                if (entries[i].getFrequency() < lowestFrequency) {
                    lowestFrequency = entries[i].getFrequency();
                    index = i;
                } else if (entries[i].getFrequency() == lowestFrequency) {
                    // If it has the same frequency set the one which was accessed last as index
                    index = entries[i].getTimestamp() < entries[index].getTimestamp() ? i : index;
                }
            }
        }
        return index;
    }
}
