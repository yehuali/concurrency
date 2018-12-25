package com.example.netty.shutdown;

import java.util.concurrent.TimeUnit;

/**
 * https://blog.csdn.net/u013256816/article/details/50394923
 */
public class HookTest{
   public void start(){
       Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
           @Override
           public void run() {
               System.out.println("Execute Hook .....");
           }
       }));
   }

    public static void main(String[] args) {
        new HookTest().start();
        System.out.println("The Application is doing something");
//        System.out.println(10/0);
        try {
            TimeUnit.MILLISECONDS.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
