package me.mega.yetanotherchat.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public final class YacServerHandler extends ChannelInboundHandlerAdapter {
    private int id;
    private static final List<Integer> blockedIds = new ArrayList<>();

    @Override
    public void channelRead(final ChannelHandlerContext ctx, final Object msg) {
        System.out.println("Something came through the channel");
        final ByteBuf buffer = (ByteBuf) msg;

        try {
            final byte[] bytes = new byte[buffer.readableBytes()];
            buffer.readBytes(bytes);

            if (id == 0) {
                final String message = new String(bytes, StandardCharsets.UTF_8);
                final int id = Integer.parseInt(message);
                this.id = id;

                System.out.println(id + " just connected!");

                return;
            }

            final String messageText = new String(bytes, StandardCharsets.UTF_8);
            final String messageToSend = id + ": " + messageText;


            if (messageText.startsWith("/block")) {
                String[] parts = messageText.split(" ");
                final String blockID = parts[1];
                final int id = Integer.parseInt(blockID.replace("\n", ""));
                blockedIds.add(id);
                System.out.println("Blocked id: " +id);

                return;
            }
            if (messageText.startsWith("/unblock")) {
                String[] parts = messageText.split(" ");
                final String blockID = parts[1];
                final int id = Integer.parseInt(blockID.replace("\n", ""));
                blockedIds.remove(new Integer(id));
                System.out.println("Unblocked id: " +id);

                return;
            }
            if (blockedIds.contains(id)) {
                System.out.println("Suppressing blocked message!");
                return;
            }
            System.out.println("Message received: " + messageText);
            ctx.channel().writeAndFlush(messageToSend);
        } finally {
            buffer.release();
        }
    }

    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
