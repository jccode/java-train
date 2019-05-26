package com.github.jccode.fp;

import com.github.jccode.fp.common.CollectionUtilities;
import com.github.jccode.fp.common.List;
import org.junit.Test;

/**
 * ListTest
 *
 * @author 01372461
 */
public class ListTest {

    @Test
    public void simpleUsageTest() {
        List<Object> empty = List.list();
        List<Integer> list1 = List.list(1, 2, 3, 4);
        List<Integer> list2 = list1.cons(5);

        System.out.println("empty:"+empty);
        System.out.println("list1:"+list1);
        System.out.println("list2:"+list2);
        System.out.println("-------------");

        System.out.println(empty.length());
        System.out.println(list1.length());
        System.out.println(list2.length());

        System.out.println(list1.foldLeft(0, x -> y -> x + y));
        System.out.println(list1.reverse());

        System.out.println("-------------");
        List<Integer> list3 = List.list(1,2,3,4,5,6,7,8,9,10);
        System.out.println(list3.take(5));
        System.out.println(list3.drop(5));

    }
}
