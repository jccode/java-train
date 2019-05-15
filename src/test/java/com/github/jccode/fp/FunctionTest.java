package com.github.jccode.fp;

import com.github.jccode.fp.common.Function;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;


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
}
