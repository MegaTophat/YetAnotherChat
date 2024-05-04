package me.mega.yetanotherchat.network;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;

import java.net.SocketAddress;

public class NetworkManager extends SimpleChannelInboundHandler<Packet<?>> {
    public static final AttributeKey<Protocol> currentProtocol;
    private Channel channel;
    private SocketAddress socketAddress;
    private PacketHandler packetHandler;

    static {
        currentProtocol = AttributeKey.valueOf("protocol");
    }

    @Override
    protected void channelRead0(final ChannelHandlerContext ctx, final Packet msg) {

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

    public void handle(final Packet<?> packet) {
        final Protocol protocol = Protocol.getAssociatedState(packet);

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

    public void setPacketListener(final PacketHandler packetHandler) {
        this.packetHandler = packetHandler;
    }
}
