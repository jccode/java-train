package com.github.jccode.javatrain.lru;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * 继承自现成的 LinkedHashMap 来实现 LRU算法.
 * @param <K>
 * @param <V>
 */
public class LRUCache<K, V> extends LinkedHashMap<K, V> {

    private final int size;

    public LRUCache(int size) {
        super(size, .75f, true);
        this.size = size;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > size;
    }
}
