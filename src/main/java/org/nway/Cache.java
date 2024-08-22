package org.nway;

/**
 * Cache Interface with Key and Value
 */
public interface Cache<K, V> {

    //get value by key
    V get(K key);

    //put object at the key place
    void put(K key, V value);

    // return hash of the key
    default int hash(K key) {
        // prime number multiply and addition for even spread of the keys
        return key.hashCode() * 37 + 17;
    }
}
