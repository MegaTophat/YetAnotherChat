package me.mega.yetanotherchat.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.EncoderException;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketPrepender extends MessageToByteEncoder<ByteBuf> {
    private static final byte NUM_PREPEND_BYTES = Short.BYTES;

    @Override
    public void encode(final ChannelHandlerContext ctx, final ByteBuf msg, final ByteBuf out) {
        final int readableBytes = msg.readableBytes();

        if (readableBytes > NUM_PREPEND_BYTES) {
            throw new EncoderException("message to big to length prefix with 2 bytes");
        }

        out.ensureWritable(readableBytes + NUM_PREPEND_BYTES);
        out.writeShort(readableBytes);
        out.writeBytes(msg, readableBytes);
    }
}
