package me.mega.yetanotherchat.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class PacketPreparer extends ByteToMessageDecoder {
    @Override
    protected void decode(final ChannelHandlerContext ctx, final ByteBuf in, final List<Object> out) {
        if (in.readableBytes() < 2) {
            System.err.println("Packet doesn't have enough bytes to extrapolate any meaning!");

            return;
        }

        final short dataLength = in.readShort();

        if (in.readableBytes() < dataLength) {
            System.err.println("Packet's length prefix lied about the data length");

            return;
        }

        out.add(in.readBytes(dataLength));
    }
}
