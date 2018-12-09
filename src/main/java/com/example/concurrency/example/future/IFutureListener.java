package com.example.concurrency.example.future;

public interface IFutureListener<V> {
    public void operationCompleted(IFuture<V> future) throws Exception;


}
