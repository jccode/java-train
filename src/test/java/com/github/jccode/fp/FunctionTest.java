package com.github.jccode.fp;

import com.github.jccode.fp.common.Function;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static com.github.jccode.fp.common.CollectionUtilities.*;


/**
 * FunctionTest
 *
 * @author 01372461
 */
public class FunctionTest {

    @Test
    public void test01() {
        Function<Integer, Integer> triple = arg -> arg * 3;
        assertThat(triple.apply(3), equalTo(9));

        Function<Integer, Integer> square = arg -> arg * arg;
        assertThat(square.apply(2), equalTo(4));

        // 3 * (2 ^ 2)
        assertThat(triple.compose(square).apply(2), equalTo(12));
        assertThat(Function.compose(triple, square).apply(2), equalTo(12));

        // (3 * 2) ^ 2
        assertThat(triple.andThen(square).apply(2), equalTo(36));
        assertThat(Function.andThen(triple, square).apply(2), equalTo(36));
    }

    @Test
    public void testCurry() {
        Function<Integer, Function<Integer, Integer>> multip = x -> y -> x * y;
        Function<Integer, Integer> triple = multip.apply(3);
        assertThat(triple.apply(3), equalTo(9));

        Function<String, Function<Integer, String>> f = x -> y -> String.valueOf(x) + y;
        Function<Integer, String> foo = f.apply("foo");
        assertThat(foo.apply(3), equalTo("foo3"));
    }

    @Test
    public void testCollectionUtilities() {
        Integer sum = foldLeft(list(1, 2, 3, 4, 5), 0, x -> y -> x + y);
        assertThat(sum, equalTo(15));

        assertThat(append(list(1,2,3), 4), equalTo(list(1,2,3,4)));
        assertThat(prepend(list(1,2,3), 4), equalTo(list(4,1,2,3)));
        assertThat(range(1,5), equalTo(list(1,2,3,4)));
    }
}
