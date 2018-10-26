package com.fcs.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition 在队列中的应用
 * Created by fengcs on 2018/10/15.
 */
public class BoundedQueue<T> {

    private Object[] items;
    private int addIndex, removeIndex, count;
    private Lock lock = new ReentrantLock();
    private Condition notFull = lock.newCondition();
    private Condition notEmpty = lock.newCondition();

    public BoundedQueue(int length) {
        items = new Object[length];
    }

    public void add(T t) throws InterruptedException {
        lock.lock();
        try {
            if (count == items.length) {
                // 会释放锁？
                notFull.await();
            }
            items[addIndex] = t;
            if (++addIndex == items.length) {
                addIndex = 0;
            }
            count++;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public T remove() throws InterruptedException {
        lock.lock();
        try {
            if (count == 0) {
                notEmpty.await();
            }
            Object x = items[removeIndex];
            if (++removeIndex == items.length) {
                removeIndex = 0;
            }
            count--;
            notFull.signal();
            return (T) x;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        final BoundedQueue boundedQueue = new BoundedQueue(1);
        int a = 1;
        try {
            boundedQueue.add(a);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep( 3);
                        boundedQueue.add(6);
//                        boundedQueue.remove();
                        System.out.println("remove..");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            boundedQueue.add(a + 1);
            boundedQueue.add(a + 2);
            boundedQueue.add(a + 3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
