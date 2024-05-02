package me.mega.yetanotherchat.client;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import me.mega.yetanotherchat.network.client.HandshakeHandler;
import me.mega.yetanotherchat.network.NetworkManager;
import me.mega.yetanotherchat.network.ProtocolDirection;
import me.mega.yetanotherchat.packet.PacketDecoder;
import me.mega.yetanotherchat.packet.PacketEncoder;

import java.net.InetAddress;

public class ClientConnection {
    private static final boolean EPOLL_AVAILABLE;
    private final YacClient yacClient;

    static {
        EPOLL_AVAILABLE = Epoll.isAvailable();
    }

    public ClientConnection(final YacClient yacClient, final InetAddress ip, final int port) {
        this.yacClient = yacClient;
        final NetworkManager networkManager = new NetworkManager();

        new Bootstrap().channel(getSocketChannelClass()).group(getEventLoopGroup()).handler(new ChannelInitializer<>() {
            @Override
            protected void initChannel(final Channel channel) {
                final NetworkManager networkManager = new NetworkManager();

                channel.pipeline()
                        .addLast("decoder", new PacketDecoder(ProtocolDirection.CLIENTBOUNDED))
                        .addLast("encoder", new PacketEncoder(ProtocolDirection.SERVERBOUNDED))
                        .addLast("packet_handler", networkManager);
                networkManager.setPacketListener(new HandshakeHandler(ClientConnection.this.yacClient, networkManager));
            }
        }).connect(ip, port).syncUninterruptibly();
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
            threadFactoryBuilder.setNameFormat("Client Netty Epoll IO #%d");

            return new EpollEventLoopGroup(threadFactoryBuilder.build());
        }

        threadFactoryBuilder.setNameFormat("Client Epoll IO #%d");

        return new NioEventLoopGroup(threadFactoryBuilder.build());
    }
}
