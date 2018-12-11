package com.example.disruptor.quickStart;

/**
 * 订单
 * 因为走纯内存，无需序列化
 */
public class OrderEvent {
    private long value;

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
