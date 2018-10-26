package com.fcs;

import org.junit.Test;

/**
 * Created by fengcs on 2018/7/3.
 */
public class OtherTest {

    @Test
    public void simpleTest(){
//        int a = 5_2;
//        int b = 32;
//        System.out.println(a-b);

        int a = 0 << 29;
        int b = 1 << 29;
        int c = 2 << 29;
        int d = 3 << 29;
        int e = -1 << 29;

        int w = 1 << 29 -1;
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println(d);
        System.out.println(e);
        System.out.println(w & 1);
    }

}
