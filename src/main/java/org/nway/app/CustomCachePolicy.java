package org.nway.app;

import org.nway.Entry;
import org.nway.Policy;

public class CustomCachePolicy<V> implements Policy<V> {
    @Override
    public int evictEntryIndex(Entry<V>[] entries, int startIndex, int endIndex) {
        return entries.length / 2;
    }
}
