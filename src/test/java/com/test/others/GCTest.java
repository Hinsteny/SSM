package com.test.others;

import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hinsteny
 * @Describtion
 * @date 2017/6/12
 * @copyright: 2016 All rights reserved.
 */
public class GCTest {

    private static Instrumentation instrumentation;

    public static void main(String[] args) {
        BigObject bigObject = new BigObject(1024);
        if (bigObject != null)
            System.out.println("=====================================================================================" + instrumentation.getObjectSize(bigObject));
        List<Double[]> datas = new ArrayList<>();
        Double[] item;
        while (true){
            item = new Double[1280];
            datas.add(item);
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    }

    private static class BigObject{

        private int count;

        // one double employ 8 B, so 128 double employ 1K
        private int number = 128;

        private List<Double[]> data = new ArrayList<>();

        public BigObject(int count) {
            this.count = count > 0 ? count : 1;
        }

        private void init (){
            for (int i=0; i<count; i++){
                data.add(new Double[number]);
            }
        }
    }
}
