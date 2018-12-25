package com.example.netty.start;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Netty 服务端意外退出问题
 *   1.监听Close Future
 */
@Slf4j
public class NettyUnsafeStart {

    public static void main(String[] args){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new LoggingHandler(LogLevel.INFO));
                        }
                    });
            /**
             * 分析：
             *   绑定服务端端口，在NioEventLoop线程执行
             *   尽管会同步阻塞，等待端口绑定结果，但执行很快
             *   --->服务端会退出
             */
            ChannelFuture f =  b.bind(18080).sync();

            /**
             * 监听Close Future
             * 某些场景需要同步阻塞等待一些I/O操作结果 ---> 提供了ChannelFuture
             *   1.通过注册监听器GenericFutureListener,异步等待I/O执行结果
             *   2.通过sync或者await，主动阻塞当前调用方线程，异步转同步
             */

            f.channel().closeFuture().addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    //省略业务代码逻辑
                    log.info(future.channel().toString() + " 链路关闭");
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
