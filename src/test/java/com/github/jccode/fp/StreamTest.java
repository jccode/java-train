package com.github.jccode.fp;

import com.github.jccode.fp.common.Stream;
import org.junit.Test;

/**
 * StreamTest
 *
 * @author 01372461
 */
public class StreamTest {

    @Test
    public void simpleTest() {
        Stream<Integer> stream = Stream.from(0);
        System.out.println(stream.head());
        System.out.println(stream.tail().head());
        System.out.println(stream.tail().tail().head());

//        System.out.println(stream.take(5).toList());
    }
}
