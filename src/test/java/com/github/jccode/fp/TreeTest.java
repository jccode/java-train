package com.github.jccode.fp;

import com.github.jccode.fp.ch10.Tree;
import org.junit.Test;

/**
 * TreeTest
 *
 * @author 01372461
 */
public class TreeTest {

    @Test
    public void simple() {
        Tree<Integer> t1 = Tree.empty();
        System.out.println(t1);
        Tree<Integer> t2 = Tree.tree(2, 3, 6, 10, 1, 5, 4, 7);
        System.out.println(t2);
        System.out.println(t2.member(11));
        System.out.println(t2.size());
        System.out.println(t2.height());
        System.out.println(t2.remove(5));
    }
}
