package com.example.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.errors.AuthorizationException;
import org.apache.kafka.common.errors.OutOfOrderSequenceException;
import org.apache.kafka.common.errors.ProducerFencedException;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Test;

import java.util.Properties;

public class ProducerDemo {
    /**
     * 生成器包含以下部分
     *    1.一个缓冲区空间池：保存尚未传输到服务器的记录
     *    2.一个后台I/O线程：将记录转换为请求并传输到集群
     * send()是异步的
     *    1.将记录添加到待处理记录发送的缓冲区中并返回
     *      --->允许生产者将各个记录一起批处理
     *
     *请求失败，则生产者可以自动重试（重试指定为0不会）
     *  --->有重复的可能性
     *
     * KafkaProducer支持另外两种模式：
     *   1.幂等生成器
     *     1.1 将kafka的交付语义从至少一次加强到一次交付，特别生产者重试不再引入重复
     *     1.2 启用幂等性，必须将enable.idempotence配置设置为true
     *        --->重试配置默认为Integer.MAX_VALUE ,acks配置默认为all；
     *            避免应用程序级别重新发送；
     *            建议关闭生产者并检查上次生成的消息的内容以确保没有重复；
     *            生产者只保证单个会话发送消息的幂等性
     *   2.事务生成器
     *     2.1 允许应用程序以原子方式将消息发送到多个分区(和主题)
     *     2.2 设置transaction.id配置属性 ---> 自动启用idempotence以及idempotence所依赖的生产者配置
     *          --->单个生产者实例的多个会话中启用事务恢复（从分区的有状态应用程序中的分片标识符派生）
     *              --->在分区应用程序中运行的每个生产者实例应该是唯一的
     *     2.3 交易中包含的主题应配置为持久性，replication.factor至少为3，主题的min.insync,replicas设置为2
     *     2.4 为了端到端实现事务保证，消费者必须配置为只读取已提交的消息
     */
    @Test
    public void test1(){
        Properties props = new Properties();
        props.put("bootstrap.servers", "47.107.128.64:9092");
        props.put("acks", "all");
        props.put("delivery.timeout.ms", 30000);
        props.put("batch.size", 16384);//生产者为每个分区维护未发送的缓冲区
        props.put("linger.ms", 1);//生产者在发送请求前等待该毫秒数 --->希望更多记录填满同一批次（类似于TCP的Nagle算法）
        props.put("buffer.memory", 33554432);//控制生产者可用于缓冲的总内存量，当缓冲区耗尽，额外的调用被阻止 --->阻止事件的阈值由max.block.ms确定
        //key.serializer和value.serializer指示如何将用户提供的键和值对象及其ProducerRecord转换为字节
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 100; i++)
            producer.send(new ProducerRecord<String, String>("test", Integer.toString(i), Integer.toString(i)));

        producer.close();
    }

    /**
     * 1.事务生成器使用异常来传达错误状态
     * 2.不需要为返回的Future指定producer.send()或调用get()回调
     * @param args
     */
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "47.107.128.64:9092");
        props.put("transactional.id", "my-transactional-id");
        Producer<String, String> producer = new KafkaProducer<>(props, new StringSerializer(), new StringSerializer());

        producer.initTransactions();

        try {
            producer.beginTransaction();
            for (int i = 0; i < 100; i++)
                producer.send(new ProducerRecord<>("test", Integer.toString(i), Integer.toString(i)));
            producer.commitTransaction();
        } catch (ProducerFencedException | OutOfOrderSequenceException | AuthorizationException e) {
            // We can't recover from these exceptions, so our only option is to close the producer and exit.
            producer.close();
        } catch (KafkaException e) {
            //确保将任何成功的写入标记为中止
            // For all other exceptions, just abort the transaction and try again.
            producer.abortTransaction();
        }
        producer.close();
    }
}
