package com.fcs.threadPool;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * Created by fengcs on 2018/7/4.
 */
public class ThreadPoolTest {

    private static CountDownLatch latch = new CountDownLatch(100);

    @Test
    public void testFixedThreadPool(){
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<Integer> future = executorService.submit(new MyCallable());
        try {
            Integer num = future.get();
            System.out.println(num);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

//        executorService.submit(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println(1/0);
//            }
//        });
    }

    @Test
    public void testMustThreadWork() throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; i++) {
            executorService.submit(new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    TimeUnit.SECONDS.sleep(10);
                    System.out.println("end the work.....");
                    latch.countDown();
                    return null;
                }
            });
        }
        latch.await();

    }

    private class MyCallable implements Callable{

        @Override
        public Object call() throws Exception {
            return 1 / 0;
        }
    }

}
