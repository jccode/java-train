package com.github.jccode.javatrain.lru;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class LRUCacheTest {

    @Test
    public void simpleTest() {
        LRUCache<String, Integer> lruCache = new LRUCache<>(3);
        lruCache.put("tom", 18);
        lruCache.put("cat", 19);
        lruCache.put("dog", 20);
        System.out.println(lruCache);
        System.out.println("-------");
        assertThat(lruCache.keySet(), hasItems("tom", "cat", "dog"));

        lruCache.put("chicken", 21);
        System.out.println(lruCache);
        System.out.println("-------");
        assertThat(lruCache.keySet(), hasItems("cat", "dog", "chicken"));

        lruCache.get("cat");
        lruCache.put("pig", 22);
        System.out.println(lruCache);
        System.out.println("-------");
        assertThat(lruCache.keySet(), hasItems("chicken", "cat", "pig"));
    }
}
