package com.fcs.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by fengcs on 2018/10/12.
 */
public class LockSupportTest {

    public static void main(String[] args) {

        Thread threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("=====================");
                LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(10));
            }
        });

        Thread threadTwo = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("=====================");
//                LockSupport.parkNanos(, TimeUnit.SECONDS.toNanos(10));
            }
        });

        threadOne.start();
        threadTwo.start();

    }

}
