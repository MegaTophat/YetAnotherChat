package me.mega.yetanotherchat.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Random;

public class YacClientHandler extends ChannelInboundHandlerAdapter {
    private static final Random RANDOM = new Random();

    @Override
    public void channelActive(final ChannelHandlerContext context) {
        System.out.println("Client just established connection to server!");
        context.channel().writeAndFlush(RANDOM.nextInt(50000) + "");
        System.out.println("test");
    }

    @Override
    public void channelRead(final ChannelHandlerContext context, final Object message) {
        final ByteBuf buffer = (ByteBuf) message;

        try {
            byte[] bytes = new byte[buffer.readableBytes()];
            buffer.readBytes(bytes);
            final String messageText = new String(bytes);
            System.out.println("Message from Server: " + messageText);
        } finally {
            buffer.release();
        }
    }

    @Override
    public void exceptionCaught(final ChannelHandlerContext context, final Throwable throwable) {
        throwable.printStackTrace();
        context.close();
    }
}
