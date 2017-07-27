package com.test.util;

import com.hisoka.utils.DateTimeUtil;
import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Auther Hinsteny
 * @Desc 对 DateTimeUtil 工具类进行方法测试
 * @Date 2017-07-27
 * @copyright: 2017 All rights reserved.
 */
public class DateTimeUtilTest {

    @Test
    public void testFormat () throws InterruptedException {
        Calendar calendar = Calendar.getInstance();
        // 创建时间对象2017-07-27 15:15:30  月份是从0开始算起
        calendar.set(2017, 06, 27, 15, 15, 30);
        calendar.set(Calendar.MILLISECOND, 123);
        Date date = calendar.getTime();

        Assert.assertEquals(DateTimeUtil.format(date, DateTimeUtil.SHORT_DATE_FORMAT_STR), "2017-07-27");
        Assert.assertEquals(DateTimeUtil.format(date, DateTimeUtil.MAX_LONG_DATE_FORMAT_STR), "2017-07-27 15:15:30.123");

        ExecutorService service = Executors.newFixedThreadPool(10);
        final AtomicInteger reallyExecutTimes = new AtomicInteger(0);
        int needExecutTimes = 100;

        Runnable task = ()->{
            Assert.assertEquals(DateTimeUtil.format(date, DateTimeUtil.SHORT_DATE_FORMAT_STR), "2017-07-27");
            Assert.assertEquals(DateTimeUtil.format(date, DateTimeUtil.MAX_LONG_DATE_FORMAT_STR), "2017-07-27 15:15:30.123");
            reallyExecutTimes.incrementAndGet();
        };

        while (needExecutTimes-- > 0) {
            service.submit(task);
        }
        service.shutdown();
        service.awaitTermination(10, TimeUnit.SECONDS);

        Assert.assertEquals(reallyExecutTimes.get(), 100);
    }

}
