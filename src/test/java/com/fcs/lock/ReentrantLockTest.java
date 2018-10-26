package com.fcs.lock;

import com.fcs.common.MultiTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by fengcs on 2018/10/17.
 */
public class ReentrantLockTest {

    private ReentrantLock lock = new ReentrantLock();
    private List<Integer> list = new ArrayList<>();

    public void add(Integer num) {
        System.out.println("=================");
        lock.lock();
        try {
            list.add(num);
        }finally {
            lock.unlock();
        }
    }


    public static void main(String[] args) {
        final ReentrantLockTest reentrantLockTest = new ReentrantLockTest();
        try {
            MultiTask multiTask = new MultiTask();
            multiTask.timeTasks(5, new Runnable() {
                @Override
                public void run() {
                    reentrantLockTest.add(5);
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (Integer num : reentrantLockTest.list) {
            System.out.println(num);
        }
    }


}
