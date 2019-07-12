package com.github.jccode.javatrain.lru;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;

public class LRUTest {

    @Test
    public void simpleTest() {
        LRU<String, Integer> lru = new LRU<>(3);
        lru.put("tom", 18);
        lru.put("cat", 19);
        lru.put("dog", 20);
        System.out.println(lru);
        System.out.println("-------");
        assertThat(lru.keys(), hasItems("tom", "cat", "dog"));

        lru.put("chicken", 21);
        System.out.println(lru);
        System.out.println("-------");
        assertThat(lru.keys(), hasItems("cat", "dog", "chicken"));

        lru.get("cat");
        lru.put("pig", 22);
        System.out.println(lru);
        System.out.println("-------");
        assertThat(lru.keys(), hasItems("chicken", "cat", "pig"));
    }
}
