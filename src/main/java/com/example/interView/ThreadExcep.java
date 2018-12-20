package com.example.interView;

/**
 * 主线程中创建一个子线程，默认情况下这两个线程属于同一个线程组
 * 如果子线程发生异常，主线程可以直接使用try catch捕获到
 *   ---->不推荐
 *   线程设计理念：线程的问题应该线程自己本身来解决，而不要委托到外部
 */
public class ThreadExcep extends ThreadGroup {
    private ThreadExcep() {
        super("线程组的名字");
    }

    public void uncaughtException(Thread thread, Throwable exception) {
        System.out.println(thread.getId());
        exception.printStackTrace();//example,   print   stack   trace
    }

    public static void main(String[] args) {
        ThreadExcep excep = new ThreadExcep();
        //声明子线程是另一个线程组
        Thread thread = new Thread(excep, () -> {
            throw new NullPointerException("空指针异常");
        });
//        Thread interView = new Thread(() -> {
//            throw new NullPointerException("空指针异常");
//        });
        thread.start();
    }
}
