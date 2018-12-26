package com.example.netty.volatitle;

import java.util.concurrent.TimeUnit;

/**
 * while() 等价于 if() while(true)
 * https://www.infoq.cn/article/netty-concurrent-programming-analysis
 * 可能会出现work线程一直重复执行，因为stop不可见
 * --->但由于JVM刷新策略问题不会重现
 */
public class ReOrderDemo {
    private static boolean stop;

    public static void main(String[] args) throws InterruptedException {
        Thread workThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int i =0;
                while(!stop){
                    i++;
                    System.out.println("i = " + i);
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        workThread.start();
        TimeUnit.SECONDS.sleep(3);
        System.out.println("执行结束");
        stop = true;
    }
}
