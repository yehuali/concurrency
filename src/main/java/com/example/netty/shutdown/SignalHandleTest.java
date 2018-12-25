package com.example.netty.shutdown;

import sun.misc.Signal;
import sun.misc.SignalHandler;
/**
 * https://blog.csdn.net/songj0112/article/details/15338149
 */

public class SignalHandleTest implements SignalHandler {
    private SignalHandler oldHandler;

    public static SignalHandler install(String signalName) {
        Signal diagSignal = new Signal(signalName);
        SignalHandleTest instance = new SignalHandleTest();
        instance.oldHandler = Signal.handle(diagSignal, instance);
        return instance;
    }
    /**
     * 将实例化之后的SignalHandle注册到JDK的Signal，接受到事件回调handle接口
     * 如果SignalHandler执行的操作比较耗时，建议异步或放到ShutdownHook中执行
     * @param signal
     */
    @Override
    public void handle(Signal signal) {
        System.out.println("Signal handler called for signal "
                + signal);
        try {

            signalAction(signal);

            // Chain back to previous handler, if one exists
            if (oldHandler != SIG_DFL && oldHandler != SIG_IGN) {
                oldHandler.handle(signal);
            }

        } catch (Exception e) {
            System.out.println("handle|Signal handler failed, reason " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void signalAction(Signal signal) {
        System.out.println("Handling " + signal.getName());
        System.out.println("Just sleep for 5 seconds.");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.println("Interrupted: "
                    + e.getMessage());
        }
    }
    public static void main(String[] args) {
        SignalHandleTest.install("TERM");
        SignalHandleTest.install("INT");
        SignalHandleTest.install("ABRT");

        System.out.println("Signal handling example.");
        try {
            Thread.sleep(50000);
        } catch (InterruptedException e) {
            System.out.println("Interrupted: " + e.getMessage());
        }
    }
}
