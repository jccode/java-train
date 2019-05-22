package com.github.jccode.fp;

import com.github.jccode.fp.common.List;
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

        Stream<Integer> s1 = stream.take(100000);
        System.out.println(s1.toList());

        Stream<Integer> s2 = stream.take(100000).drop(90000);
        System.out.println(s2.toList());

        System.out.println(Stream.stream(List.list(1, 2, 3, 4)).toList());
        System.out.println(Stream.stream(1,2,3,4,5).toList());
        System.out.println(Stream.repeat(5).take(5).toList());

        System.out.println(Stream.iterate(1, x -> x + 2).take(10).toList());

    }
}
