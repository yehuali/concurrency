package com.example.disruptor;


import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueue4Test {
    public static void main(String[] args) {
        final ArrayBlockingQueue<Data> queue = new ArrayBlockingQueue<Data>(100000000);
        final long startTime = System.currentTimeMillis();
        //向容器中添加元素
        new Thread(new Runnable() {
            @Override
            public void run() {
                long i = 0;
                while(i<Constants.EVENT_NUM_OM){
                    Data data = new Data(i,"c"+i);
                    try {
                        queue.put(data);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    i++;
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                long i = 0;
                while(i<Constants.EVENT_NUM_OM){
                    Data data = new Data(i,"c"+i);
                    try {
                        queue.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    i++;
                }
                long endTime = System.currentTimeMillis();
                System.out.println("ArrayBlockingQueue costTime = " +(endTime -startTime) +" ms");
            }
        }).start();

    }

}
