package com.example.mybatis.proxy;


import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class HelloServiceCgLib implements MethodInterceptor {

    private Object target;

    public Object getInstance(Object targer){
        this.target = targer;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.target.getClass());
        //回调方法
        enhancer.setCallback(this);
        //创建代理对象
        return enhancer.create();
    }

    //回调方法
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("###############我是CGLIB的动态代理#####################");
        //反射方法前调用
        System.out.println("我准备说hello");
        Object returnObj = proxy.invokeSuper(obj,args);
        //反射方法后调用
        System.out.println("我说过hello");
        return returnObj;
    }

    public static void main(String[] args) {
        HelloServiceCgLib helloHandler = new HelloServiceCgLib();
        HelloServiceCgLibImpl proxy = (HelloServiceCgLibImpl) helloHandler.getInstance(new HelloServiceCgLibImpl());
        proxy.sayHello("张三");
    }
}
