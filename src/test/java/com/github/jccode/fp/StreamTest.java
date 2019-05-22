package com.github.jccode.fp;

import com.github.jccode.fp.common.List;
import com.github.jccode.fp.common.Stream;
import com.github.jccode.fp.common.TailCall;
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

    @Test
    public void foldLeftTest() {
        Stream<Integer> s3 = Stream.from(0).take(100_0000);
        System.out.println(s3.toList().foldLeft(0, x -> y -> x + y));
        TailCall<Integer> t = Stream.foldLeft(s3, 0, x -> y -> x + y);
        System.out.println(t.eval());
        System.out.println(s3.foldLeft(0, x -> y -> x + y));
    }

    @Test
    public void foldRightTest() {
        Stream<Integer> s1 = Stream.from(0).take(100000);
//        System.out.println(s1.foldRight(() -> 0, x -> st -> st.get() + x));

        TailCall<Integer> t = Stream.foldRight(s1, 0, x -> st -> x + st);
        System.out.println(t);
        System.out.println(t.eval());
        System.out.println(s1.foldRight(0, x -> y -> x + y));
    }

}
