package org.nway.impl;

import org.nway.Cache;
import org.nway.Entry;
import org.nway.Policy;

import java.lang.reflect.Array;
import java.util.Arrays;

// N-way Set association cache implementation
public class NWayCache<K, V> implements Cache<K, V> {

    // line entries
    private final Entry<V>[] entries;

    private final int associativity;
    //num of sets
    private final int numSets;
    //eviction policy default LRU
    private final Policy<V> evictionPolicy;

    public NWayCache(int associativity, int numSets) {
        this(associativity, numSets, new LRUPolicy<>());
    }

    public NWayCache(int associativity, int numSets, Policy<V> evictionPolicy) {
        this.associativity = associativity;
        this.numSets = numSets;
        this.entries = newEntries();
        this.evictionPolicy = evictionPolicy;

    }

    @SuppressWarnings("unchecked")
    private Entry<V>[] newEntries() {
        Entry<V>[] entries = (Entry<V>[]) Array.newInstance(Entry.class, associativity * numSets);
        for (int i = 0; i < entries.length; i++) {
            entries[i] = new Entry<>();
        }
        return entries;
    }

    // returns the set No associated with the Key
    private int getSetNo(int hashKey) {
        return hashKey % numSets;
    }

    // returns the start Index for the key
    private int getStartIndex(int hashKey) {
        return getSetNo(hashKey) * associativity;
    }

    // returns the end Index for the key
    private int getEndIndex(int hashKey) {
        return getStartIndex(hashKey) + associativity - 1;
    }

    // Method to get the value by key
    @Override
    public synchronized V get(K key) {
        int hashKey = hash(key);
        int startIndex = getStartIndex(hashKey);
        int endIndex = getEndIndex(hashKey);
        for (int i = startIndex; i <= endIndex; i++) {
            //make sure it's occupied and match the hash key
            if (entries[i].isOccupied() && entries[i].getTag() == hashKey) {
                entries[i].updateTimeNFrequency();
                //return the value
                return entries[i].getValue();
            }
        }
        return null;
    }

    // put new value in the cache by key
    @Override
    public synchronized void put(K key, V value) {
        int hashKey = hash(key);
        int startIndex = getStartIndex(hashKey);
        int endIndex = getEndIndex(hashKey);
        //create a new Entry
        Entry<V> newEntry = new Entry<>(value, System.nanoTime(), hashKey, false, 0);

        //if entry is already present in cache
        for (int i = startIndex; i <= endIndex; i++) {
            if (entries[i].isOccupied() && entries[i].getTag() == newEntry.getTag()) {
                // since it's already present get the old frequency
                newEntry.setFrequency(entries[i].getFrequency());
                // replace the entry with new value
                entries[i] = newEntry;
                entries[i].updateTimeNFrequency();
                entries[i].setOccupied(true);
                return;
            }
        }

        //if there is a line available
        for (int i = startIndex; i <= endIndex; i++) {
            if (!entries[i].isOccupied()) {
                // replace the entry with new value
                entries[i] = newEntry;
                entries[i].updateTimeNFrequency();
                entries[i].setOccupied(true);
                return;
            }
        }

        //entry is not already present and all the lines are full do eviction policy
        int evictIndex = evictionPolicy.evictEntryIndex(entries, startIndex, endIndex);
        entries[evictIndex] = newEntry;
        entries[evictIndex].updateTimeNFrequency();
        entries[evictIndex].setOccupied(true);
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        long currentTimeStamp = System.nanoTime();

        builder.append(String.format("Associativity: %s , No of Sets: %s, EvictionPolicy: %s, CurrentTimeStamp: %s\n",
                associativity,
                numSets,
                evictionPolicy.getClass().getName(),
                currentTimeStamp));

        Arrays.stream(entries).forEach(e -> {
            builder.append(String.format("SetNo: %s, Value: %s, Frequency: %s, TimestampDiff: %s\n",
                    getSetNo(e.getTag()),
                    e.getValue().toString(),
                    e.getFrequency(),
                    currentTimeStamp - e.getTimestamp()));
        });

        return builder.toString();
    }
}
