package me.mega.yetanotherchat.server;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import me.mega.yetanotherchat.network.ProtocolDirection;
import me.mega.yetanotherchat.network.handshake.server.ServerHandshakeHandler;
import me.mega.yetanotherchat.network.NetworkManager;
import me.mega.yetanotherchat.network.PacketDecoder;

import java.net.InetAddress;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class ServerConnection {
    private static final boolean EPOLL_AVAILABLE;
    private final YacServer yacServer;
    private final ChannelFuture channelFuture;
    private final List<NetworkManager> networkManagers;

    static {
        EPOLL_AVAILABLE = Epoll.isAvailable();
    }

    public ServerConnection(final YacServer yacServer, final InetAddress bindAddress, final int bindPort) {
        this.yacServer = yacServer;
        this.networkManagers = new CopyOnWriteArrayList<>();
         this.channelFuture = new ServerBootstrap().channel(getSocketChannelClass()).childHandler(new ChannelInitializer<>() {
            @Override
            protected void initChannel(final Channel channel) {
                final NetworkManager networkManager = new NetworkManager();

                channel.pipeline().addLast("decoder", new PacketDecoder(ProtocolDirection.SERVERBOUNDED));
                ServerConnection.this.networkManagers.add(networkManager);
                channel.pipeline().addLast("packet_handler", networkManager);
                networkManager.setPacketListener(new ServerHandshakeHandler(ServerConnection.this.yacServer, networkManager));
            }
        }).group(getEventLoopGroup()).localAddress(bindAddress, bindPort).bind().syncUninterruptibly();
    }

    public void closeListener() {
        try {
            this.channelFuture.channel().close().sync();
        } catch (final InterruptedException ex) {
            throw new IllegalStateException("Thread responsible for closing listener channel was interrupted?", ex);
        }
    }

    private static Class<? extends ServerChannel> getSocketChannelClass() {
        if (EPOLL_AVAILABLE) {
            return EpollServerSocketChannel.class;
        }

        return NioServerSocketChannel.class;
    }

    private static EventLoopGroup getEventLoopGroup() {
        final ThreadFactoryBuilder threadFactoryBuilder = new ThreadFactoryBuilder().setDaemon(true);

        if (EPOLL_AVAILABLE) {
            threadFactoryBuilder.setNameFormat("Server Netty Epoll IO #%d");

            return new EpollEventLoopGroup(threadFactoryBuilder.build());
        }

        threadFactoryBuilder.setNameFormat("Server Epoll IO #%d");

        return new NioEventLoopGroup(threadFactoryBuilder.build());
    }
}
