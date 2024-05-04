package me.mega.yetanotherchat.client;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import me.mega.yetanotherchat.network.NetworkManager;
import me.mega.yetanotherchat.network.PacketDecoder;
import me.mega.yetanotherchat.network.PacketEncoder;
import me.mega.yetanotherchat.network.PacketPreparer;
import me.mega.yetanotherchat.network.PacketPrepender;
import me.mega.yetanotherchat.network.ProtocolDirection;
import me.mega.yetanotherchat.network.handshake.client.ClientHandshakeHandler;
import me.mega.yetanotherchat.network.handshake.server.packet.PacketHandshakingInStart;

import java.net.InetAddress;

public class ClientConnection {
    private static final boolean EPOLL_AVAILABLE;
    private static int connectionID;

    static {
        EPOLL_AVAILABLE = Epoll.isAvailable();
        connectionID = 1;
    }

    private final YacClient yacClient;
    private final NetworkManager networkManager;

    public ClientConnection(final YacClient yacClient, final InetAddress ip, final int port) {
        this.yacClient = yacClient;
        this.networkManager = new NetworkManager();

        new Thread(new Connector(yacClient, this.networkManager, ip, port), "Server Connector #" + connectionID++).start();
    }

    private static Class<? extends Channel> getSocketChannelClass() {
        if (EPOLL_AVAILABLE) {
            return EpollSocketChannel.class;
        }

        return NioSocketChannel.class;
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

    private record Connector(YacClient yacClient, NetworkManager networkManager, InetAddress ip,
                             int port) implements Runnable {
        @Override
            public void run() {
                new Bootstrap().channel(getSocketChannelClass()).group(getEventLoopGroup()).handler(new ChannelInitializer<>() {
                    @Override
                    protected void initChannel(final Channel channel) {
                        channel.pipeline()
                                .addLast("preparer", new PacketPreparer())
                                .addLast("decoder", new PacketDecoder(ProtocolDirection.CLIENTBOUNDED))
                                .addLast("prepender", new PacketPrepender())
                                .addLast("encoder", new PacketEncoder(ProtocolDirection.SERVERBOUNDED))
                                .addLast("packet_handler", Connector.this.networkManager);
                    }
                }).connect(ip, port).syncUninterruptibly();

            this.networkManager.setPacketHandler(new ClientHandshakeHandler(this.yacClient, this.networkManager));
                this.networkManager.sendPacket(new PacketHandshakingInStart(NetworkManager.PROTOCOL_VERSION));
            }
        }
}
