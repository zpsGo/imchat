package com.zps.imchat.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @author: zps
 * @desc : 创建Netty服务，开启服务端的入口：8080
 **/
public class NettyServer {

    //定义服务端口
    private static int port = 8082;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workGroup;
    private ServerBootstrap boot;

    public NettyServer(){
        bossGroup = new NioEventLoopGroup();
        workGroup = new NioEventLoopGroup();
        boot = new ServerBootstrap();
    }

    /**
     * 使用静态内部类实现单例
     */
    private static class InnerServer{
        private static final NettyServer nettyServer = new NettyServer();
    }
    //提供获取单例的方法
    public static NettyServer getInstance(){
        return InnerServer.nettyServer;
    }

    public void start(){
        try {
            boot.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {

                            ChannelPipeline pipeline = ch.pipeline();

                            /**  websocket 基于http协议，所以要有http编解码器 */
                            pipeline.addLast(new HttpServerCodec());  //Outbound

                            /**主要是将同一个http请求或响应的多个消息对象变成一个 fullHttpRequest完整的消息对象*/
                            pipeline.addLast(new HttpObjectAggregator(64 * 1024));//Inbound

                            /**主要用于处理大数据流,比如一个1G大小的文件如果你直接传输肯定会撑暴jvm内存的 ,
                                加上这个handler我们就不用考虑这个问题了*/
                            pipeline.addLast(new ChunkedWriteHandler());//Inbound、Outbound

                            /** 增加心跳支持 针对客户端，如果在1分钟时没有向服务端发送读写心跳(ALL)，则发送心跳检测包是否连接*/
                            pipeline.addLast(new IdleStateHandler(120,120,300));

                            /** 空闲处理 */
                            pipeline.addLast(new NettyFreeHandler());

                            /** 解析WebSocket请求 */
                            pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));    //Inbound

                            /** 消息核心处理类*/
                            pipeline.addLast(new WebSocketServerHandler()); //Inbound

                        }
                    });
            ChannelFuture f = boot.bind(this.port).sync();
            System.out.println("服务已经启动！");
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new NettyServer().start();
    }

}
