package com.test.jdk.ConcurrentHashMap;

import com.test.base.NormalBase;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * @author Hinsteny
 * @Describtion
 * @date 2017/2/10
 * @copyright: 2016 All rights reserved.
 */
public class ConcurrentHashMapTest extends NormalBase{

    /**
     * 当并发线程数较多时, 可能会产生死循环, 但不保证一定会发生
     */
    @Test
    public void useHashMap() throws InterruptedException{
        final HashMap<String, String> map = new HashMap(2);
        final int threadSize = 10;
        final CountDownLatch cdLatch = new CountDownLatch(threadSize);
        for (int i = 0; i < threadSize; i++) {
            final int j = i;
            new Thread(() -> {
                logger.info("ThreadId is {}, map content is {}",Thread.currentThread().getId(), map.toString());
//              String key = UUID.randomUUID().toString();
                String key = String.valueOf(j);
                String value = UUID.randomUUID().toString();
                map.put(key, key);
                logger.info("ThreadId is {}, key is {}, map size is {}", Thread.currentThread().getId(), key, map.size());
                map.get(key);
                cdLatch.countDown();
            }, "ftf" + i).start();
        }
        cdLatch.await();
        logger.info("Finished, thred name is {}, map size is {}", Thread.currentThread().getName(), map.size());
    }

    /**
     * 内部所有获取与添加, 包含, 比较等方法都加了同步锁, 在多线程环境下可以正常工作
     * @throws InterruptedException
     */
    @Test
    public void useHashTable() throws InterruptedException {
        final Hashtable<String, String> map = new Hashtable(2);
        final int threadSize = 10;
        final CountDownLatch cdLatch = new CountDownLatch(threadSize);
        for (int i = 0; i < threadSize; i++) {
            final int j = i;
            new Thread(() -> {
                String key = UUID.randomUUID().toString();
//                String key = String.valueOf(j);
                map.put(key, "");
                logger.info("ThreadId is {}, key is {}, table size is {}", Thread.currentThread().getId(), key, map.size());
                logger.info("ThreadId is {}, table content is {}",Thread.currentThread().getId(), map.toString());
                cdLatch.countDown();
            }, "ftf" + i).start();
        }
        cdLatch.await();
        logger.info("Finished, thred name is {}, table size is {}", Thread.currentThread().getName(), map.size());
    }

    /**
     * 使用了分段锁技术, 正常工作于多线程环境
     * @throws InterruptedException
     * @throws IOException
     */
    @Test
    public void useConcurrentHashMap() throws InterruptedException, IOException{
        final Map<String, String> map = new ConcurrentHashMap();
        final int threadSize = 10;
        final CountDownLatch cdLatch = new CountDownLatch(threadSize);
        for (int i = 0; i < threadSize; i++) {
            final int j = i;
            new Thread(() ->  {
                String key = UUID.randomUUID().toString();
//                String key = String.valueOf(j);
                map.put(key, "");
                logger.info("ThreadId is {}, key is {}, ConcurrentHashMap size is {}", Thread.currentThread().getId(), key, map.size());
                logger.info("ThreadId is {}, ConcurrentHashMap content is {}",Thread.currentThread().getId(), map.toString());
                cdLatch.countDown();
            }, "ftf" + i).start();
        }
        cdLatch.await();
        logger.info("Finished, thred name is {}, ConcurrentHashMap size is {}", Thread.currentThread().getName(), map.size());
    }

    @Test
    public void testPowerTwo() {
        System.out.println(1 << 30);
        int[] data = new int[]{1, 5, 8, 12, 19, 28, 33, 58, 80, 121, 180, 1000, 1073741820};
        for (int x : data) {
            logger.info("original is {}, novel is {}", x, tableSizeFor(x));
            logger.info("\n {} \n {}", getIntBinaryStr(x), getIntBinaryStr(tableSizeFor(x)));
        }
    }

    public String getIntBinaryStr(int x){
        return Integer.toBinaryString(x);
    }

    /**
     * Returns a power of two table size for the given desired capacity.
     * See Hackers Delight, sec 3.2
     */
    private static final int tableSizeFor(int c) {
        int MAXIMUM_CAPACITY = 1 << 30;
        int n = c - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }
}
