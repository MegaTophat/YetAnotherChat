package me.mega.yetanotherchat.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public final class YacServer {
    private final ServerConnection serverConnection;
    private volatile boolean running;

    public YacServer(final InetAddress address, final int port) throws IOException {
        this.serverConnection = new ServerConnection(this, address, port);
        this.running = true;
    }

    public ServerConnection getServerConnection() {
        return this.serverConnection;
    }

    public static void main(final String[] args) throws IOException, UnknownHostException {
        final InetAddress listenAddress = InetAddress.getLoopbackAddress();
        final int listenPort = 6000;

        final YacServer yacServer = new YacServer(listenAddress, listenPort);
        System.out.println("Server listening on port: " + listenPort);

        while (yacServer.running) {
            try {
                Thread.sleep(10);
            } catch (final InterruptedException ignored) {

            }
        }
    }

//    public void start() throws InterruptedException {
//        final EventLoopGroup bossGroup = new NioEventLoopGroup();
//        final EventLoopGroup workerGroup = new NioEventLoopGroup();
//
//        try {
//            final ServerBootstrap bootstrap = new ServerBootstrap();
//
//            bootstrap
//                    .group(bossGroup, workerGroup)
//                    .channel(NioServerSocketChannel.class)
//                    .childHandler(new ChannelInitializer<>() {
//                        @Override
//                        protected void initChannel(final Channel channel) {
//                            channel.pipeline().addLast("handler", new YacServerHandler());
//                        }
//                    })
//                    .option(ChannelOption.SO_BACKLOG, 128)
//                    .childOption(ChannelOption.SO_KEEPALIVE, true);
//
//            final ChannelFuture channelFuture = bootstrap.bind(this.port).sync();
//
//            System.out.println("Server listening on port " + this.port);
//
//            while (this.running) {
//
//            }
//        } finally {
//            workerGroup.shutdownGracefully();
//            bossGroup.shutdownGracefully();
//        }
//    }
}
