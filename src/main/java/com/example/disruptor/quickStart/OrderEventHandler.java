package com.example.disruptor.quickStart;

import com.lmax.disruptor.EventHandler;

/**
 * 订单（消息）的消费者
 */
public class OrderEventHandler implements EventHandler<OrderEvent> {
    /**
     * 事件驱动（监听）模式
     * @param orderEvent
     * @param l
     * @param b
     * @throws Exception
     */
    @Override
    public void onEvent(OrderEvent orderEvent, long l, boolean b) throws Exception {
        System.out.println("消费者："+orderEvent.getValue());
    }
}
