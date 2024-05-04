package me.mega.yetanotherchat.network;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;

import java.net.SocketAddress;

public class NetworkManager extends SimpleChannelInboundHandler<Packet<PacketHandler>> {
    public static final int PROTOCOL_VERSION = 1;
    public static final AttributeKey<Protocol> currentProtocol;

    static {
        currentProtocol = AttributeKey.valueOf("protocol");
    }

    private Channel channel;
    private SocketAddress socketAddress;
    private PacketHandler packetHandler;

    @Override
    protected void channelRead0(final ChannelHandlerContext ctx, final Packet<PacketHandler> msg) {
        if (this.channel.isOpen()) {
            msg.handle(this.packetHandler);
        }
    }

    @SuppressWarnings("ProhibitedExceptionDeclared") //Exception is from super class!
    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);

        this.channel = ctx.channel();
        this.socketAddress = this.channel.remoteAddress();

        try {
            this.setProtocol(Protocol.HANDSHAKING);
        } catch (final Throwable throwable) {
            throw new AssertionError("Failed to set protocol on new channel?", throwable);
        }
    }

    public void setProtocol(final Protocol protocol) {
        this.channel.attr(currentProtocol).set(protocol);
    }

    public void sendPacket(final Packet<?> packet) {
        if (this.channel.eventLoop().inEventLoop()) {
            final ChannelFuture channelFuture = this.channel.writeAndFlush(packet);

            channelFuture.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
        } else {
            this.channel.eventLoop().execute(() -> {
                final ChannelFuture channelFuture = NetworkManager.this.channel.writeAndFlush(packet);

                channelFuture.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
            });
        }
    }

    public void close() {
        if (!this.channel.isOpen()) {
            return;
        }

        this.channel.close().awaitUninterruptibly();
    }

    public void setPacketHandler(final PacketHandler packetHandler) {
        this.packetHandler = packetHandler;
    }
}
