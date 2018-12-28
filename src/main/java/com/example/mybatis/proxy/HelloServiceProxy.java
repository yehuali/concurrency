package com.example.mybatis.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 代理类须实现InvocationHandler
 * JDK代理必须提供接口
 */
public class HelloServiceProxy implements InvocationHandler {
    private Object target;//真实服务对象

    /**
     * 绑定委托对象并返回一个代理类
     * @param target
     * @return
     */
    public Object bind(Object target){
        this.target = target;
        //取得代理对象
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),target.getClass().getInterfaces(),this);
    }

    /**
     * 通过代理对象调用方法首先进入这个方法
     * @param proxy 代理对象
     * @param method 被调用方法
     * @param args  方法的参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("###############我是JDK动态代理#####################");
        Object result = null;
        System.out.println("我准备说hello.");
        result = method.invoke(target,args);
        System.out.println("我说过hello了");
        return result;
    }

    public static void main(String[] args) {
        HelloServiceProxy helloHandler = new HelloServiceProxy();
        HelloService proxy = (HelloService) helloHandler.bind(new HelloServiceImpl());
        proxy.sayHello("张三");
    }
}
