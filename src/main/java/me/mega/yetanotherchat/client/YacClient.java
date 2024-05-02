package me.mega.yetanotherchat.client;

import com.google.common.base.Charsets;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.Random;

public final class YacClient {
    private final InetAddress host;
    private final int port;

    public static void main(String[] args) throws IOException, InterruptedException {
        new YacClient(InetAddress.getLocalHost(), 6000).run();
    }

    public YacClient(final InetAddress host, final int port) {
        this.host = host;
        this.port = port;
    }

    public void run() throws IOException, InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            final Bootstrap bootstrap = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new YacClientInitializer());

            final Channel channel = bootstrap.connect(this.host, this.port).sync().channel();
            final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                final String lineRead = in.readLine();

                if (lineRead.equals("!exit")) {
                    break;
                }

                channel.writeAndFlush(lineRead + '\n');
            }
        } finally {
            group.shutdownGracefully();
        }
    }
}
