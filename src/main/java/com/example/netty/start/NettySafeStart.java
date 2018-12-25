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
 * 实际项目，很少通过main函数直接调用Netty服务端
 * --->通过某种容器(Tomcat)拉起进程，容器初始化各种业务资源
 * 不建议在启动时候同步阻塞等待服务端端口关闭
 */
@Slf4j
public class NettySafeStart {
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
            ChannelFuture f =  b.bind(18080).sync();


            f.channel().closeFuture().sync().addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    /**
                     * 省略业务代码逻辑
                     *  shutdownGrancefully完成内存队列中积压消息的处理，链路的关闭和EventLoop线程的退出
                     *  --->实现停机不中断业务
                     */
                    bossGroup.shutdownGracefully();
                    workerGroup.shutdownGracefully();
                    log.info(future.channel().toString() + " 链路关闭");
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }finally {
//            bossGroup.shutdownGracefully();
//            workerGroup.shutdownGracefully();
        }

    }
}
