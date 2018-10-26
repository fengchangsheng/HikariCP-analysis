package com.fcs.common;

import java.util.concurrent.CountDownLatch;

/**
 * Created by fengcs on 2018/10/25.
 */
public class MultiTask {

    public long timeTasks(int nThreads, final Runnable task) throws InterruptedException {
        final CountDownLatch startGate = new CountDownLatch(1);//起始门
        final CountDownLatch endGate = new CountDownLatch(nThreads);//结束门

        for (int i = 0; i < nThreads; i++) {
            Thread t = new Thread(){
                @Override
                public void run() {
                    try {
                        startGate.await();//一直阻塞直到计数器为零
                        try {
                            task.run();
                        }finally {
                            endGate.countDown();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            t.start();
        }

        long start = System.nanoTime();
        startGate.countDown();
        endGate.await();
        long end = System.nanoTime();
        return end - start;
    }

}
