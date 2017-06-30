package com.test.others;

import org.junit.Test;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author Hinsteny
 * @Describtion
 * @date 2017/6/20
 * @copyright: 2016 All rights reserved.
 */
public class TestCallback {

    @Test
    public void testThreadPool () throws InterruptedException, ExecutionException {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 10, 1000, TimeUnit.SECONDS, new LinkedBlockingDeque());
        Future<String> result = poolExecutor.submit(()->{
            System.err.println("Do work!");
            return "xxx";
        });
        while (!result.isDone()){
            System.err.println("Not finished!");
            Thread.sleep(1000);
        }
        System.err.println(result.get());
        TimeUnit.SECONDS.sleep(100);
        poolExecutor.shutdown();
    }

    @Test
    public void testCountResult () {
        System.err.println((byte)211);
    }

    @Test
    public void testForEachLinkedList () {
        List<String> data = new LinkedList<>();
        data.add("one");
        data.add("two");
        data.add("three");
        for (String str : data){
            System.err.println(str);
        }
        for (Iterator<String> item = data.iterator(); item.hasNext();){
            System.err.println(item.next());
        }
        data.forEach((k)->System.err.println(k));

    }

    @Test
    public void testRef () {
        Byte bx = 1;
        Byte by = 1;
        System.err.println(bx == by);

        Short sx = 1;
        Short sy = 1;
        System.err.println(sx == sy);

        Character cx = 1;
        Character cy = 1;
        System.err.println(cx == cy);

        Integer ix = 1;
        Integer iy = 1;
        System.err.println(ix == iy);

        Long lx = 1L;
        Long ly = 1L;
        System.err.println(lx == ly);

        Float fx = 1.0f;
        Float fy = 1.0f;
        System.err.println(fx == fy);

        Double dx = 1.0;
        Double dy = 1.0;
        System.err.println(dx == dy);

    }

}
