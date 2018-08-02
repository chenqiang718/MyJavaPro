package cq.cq.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.log4j.Logger;

public class TimeServer {
    private static final Logger logger=Logger.getLogger(TimeServer.class);
    private int port;
    public TimeServer(int port){
        this.port=port;
    }

    public void run() throws InterruptedException {
        EventLoopGroup boss=new NioEventLoopGroup(1);
        EventLoopGroup work=new NioEventLoopGroup();
        try{
            ServerBootstrap serverBootstrap=new ServerBootstrap();
            serverBootstrap.group(boss, work)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new TimeServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture cf=serverBootstrap.bind(port).sync();
            cf.channel().closeFuture().sync();
        }finally{
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        int port;
        if(args.length>0){
            port=Integer.parseInt(args[0]);
        }else {
            port=8080;
        }
        try {
            new TimeServer(port).run();
        } catch (InterruptedException e) {
            logger.error(e);
        }
    }
}
