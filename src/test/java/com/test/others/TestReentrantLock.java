package com.test.others;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Hinsteny
 * @Describtion
 * @date 2017/6/19
 * @copyright: 2016 All rights reserved.
 */
public class TestReentrantLock {

    @Test
    public void testTryNonfairSyncLock () {
        final Lock lock = new ReentrantLock(true);
        final int count = 10;
        final CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i=0, j=count; i<j; i++){
            final int x = i;
            new Thread(()->{
                try {
                    Thread.currentThread().setName("Thread" + x);
                    while (!lock.tryLock()){
                        System.err.println("Not get lock by " + Thread.currentThread().getName());
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.err.println("Get lock by " + Thread.currentThread().getName());
                }finally {
                    lock.unlock();
                }
//                lock.notifyAll();
                countDownLatch.countDown();
            }).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testNonfairSyncLock () {
        final Lock lock = new ReentrantLock();
        final int count = 10;
        final CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i=0, j=count; i<j; i++){
            final int x = i;
            new Thread(()->{
                try {
                    Thread.currentThread().setName("Thread" + x);
                    lock.lock();
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.err.println("Get lock by " + Thread.currentThread().getName());
                }finally {
                    lock.unlock();
                }
//                lock.notifyAll();
                countDownLatch.countDown();
            }).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new TestReentrantLock().testNonfairSyncLock();
    }
}
