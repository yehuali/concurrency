package com.example.netty.start;

import java.util.concurrent.TimeUnit;

/**
 * 所有的非守护线程退出后，JVM才结束
 */
public class DaemonThreadStudy {
    public static void main(String[] args) throws InterruptedException {
        long startTime = System.nanoTime();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.DAYS.sleep(Long.MAX_VALUE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"Daemon-T");
//        t.setDaemon(true);
        t.start();

        TimeUnit.SECONDS.sleep(15);
        System.out.println("系统退出，程序执行" +(System.nanoTime() -startTime)/1000/1000/1000 + " s");
    }
}
