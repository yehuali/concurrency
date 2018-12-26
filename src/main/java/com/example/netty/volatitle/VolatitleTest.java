package com.example.netty.volatitle;

/**
 * https://mp.weixin.qq.com/s?__biz=MzIxMTA5NjQyMg==&mid=2647802076&idx=1&sn=ec4882701f93bdc275cf4183e04b673e&chksm=8f7c66d5b80befc3056b0086b07c3b74cf089edced522ffa13ff4784a19ee0b4fa26d0fda91c&mpshare=1&scene=1&srcid=1226RHla5J47HwhcxMe0z0eG#rd
 * volatile声明的变量，直接写入内存，直接从内存读取
 * ---->适合单线程写，一个或者多个其他线程读
 */
public class VolatitleTest {
    private static  int value = 1;
    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            int temp = 0;
            while(true){
                if(temp !=value){
                    temp = value;
                    System.out.println("reader:value = " + value);
                }
            }
        });

        Thread thread2 = new Thread(()->{
            for(int i =0;i<5;i++){
                value ++ ;
                System.out.println("write:change value to " + value);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.exit(0);
        });
        thread1.start();
        thread2.start();
    }
}
