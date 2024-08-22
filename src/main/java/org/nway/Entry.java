package org.nway;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Entry<V> {
    // the data
    private V value;

    // the timestamp
    private long timestamp;

    // the key hashcode for equality comparison
    private int tag;

    // boolean value indicating weather the cache line is occupied or not
    private boolean occupied;

    // value indicating frequency of usage
    private long frequency;

    // update the time and frequency
    public void updateTimeNFrequency() {
        this.timestamp = System.nanoTime();
        this.frequency = frequency + 1;
    }
}
