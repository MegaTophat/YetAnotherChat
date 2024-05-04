package me.mega.yetanotherchat.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.EncoderException;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketPrepender extends MessageToByteEncoder<ByteBuf> {
    private static final byte NUM_PREPEND_BYTES = Short.BYTES;
    private static final int MAX_MESSAGE_BYTES = (Short.MAX_VALUE + 1) * 2; // 2^16

    @Override
    public void encode(final ChannelHandlerContext ctx, final ByteBuf msg, final ByteBuf out) {
        System.out.println("length prefixing a message");
        final int readableBytes = msg.readableBytes();

        if (readableBytes > MAX_MESSAGE_BYTES) {
            throw new EncoderException("message too big to length prefix with 2 bytes");
        }

        out.ensureWritable(readableBytes + NUM_PREPEND_BYTES);
        out.writeShort(readableBytes);
        out.writeBytes(msg, readableBytes);
    }
}
