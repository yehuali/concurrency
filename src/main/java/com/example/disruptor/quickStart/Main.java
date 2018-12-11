package com.example.disruptor.quickStart;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        /**
         * Disruptor类似于BlockArrayQueue --->有界容器
         * 支持单生产者or多生产者
         * 等待策略：
         */
        OrderEventFactory orderEventFactory = new OrderEventFactory();
        int ringBufferSize = 1024 * 1024;
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        Disruptor<OrderEvent> disruptor =
                new Disruptor<OrderEvent>(orderEventFactory,ringBufferSize,executor, ProducerType.SINGLE,new BlockingWaitStrategy());
        try{
            //添加消费者的监听
            disruptor.handleEventsWith(new OrderEventHandler());

            disruptor.start();

            //获取实际存储数据的容器：RingBuffer
            RingBuffer<OrderEvent> ringBuffer = disruptor.getRingBuffer();
            OrderEventProducer producer = new OrderEventProducer(ringBuffer);
            ByteBuffer bb = ByteBuffer.allocate(8);
            for(long i=0;i<100;i++){
                bb.putLong(0,i);
                producer.sendData(bb);
            }
        }finally {
            disruptor.shutdown();
            executor.shutdown();
        }



    }
}
