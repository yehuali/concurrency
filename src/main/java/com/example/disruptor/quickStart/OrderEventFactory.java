package com.example.disruptor.quickStart;

import com.lmax.disruptor.EventFactory;

/**
 * 订单工厂
 */
public class OrderEventFactory implements EventFactory<OrderEvent> {
    @Override
    public OrderEvent newInstance() {
        return new OrderEvent();
    }
}
