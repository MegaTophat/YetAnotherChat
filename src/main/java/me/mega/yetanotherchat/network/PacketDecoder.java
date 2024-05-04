package me.mega.yetanotherchat.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DecoderException;

import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {
    private final ProtocolDirection protocolDirection;

    public PacketDecoder(final ProtocolDirection protocolDirection) {
        this.protocolDirection = protocolDirection;
    }

    @Override
    protected void decode(final ChannelHandlerContext ctx, final ByteBuf in, final List<Object> out) {
        if (in.readableBytes() <= 0) {
            return;
        }

        final PacketDataSerializer packetDataSerializer = new PacketDataSerializer(in);
        final int packetID = packetDataSerializer.readInt();
        final Class<? extends Packet<?>> packetClass =
                ctx.channel().attr(NetworkManager.currentProtocol).get().getPacketClass(this.protocolDirection, packetID);
        final Packet<?> packet = Packet.readData(packetDataSerializer, packetClass);

        if (packet == null) {
            throw new DecoderException("");
        }

        if (packetDataSerializer.readableBytes() > 0) {
            throw new DecoderException("");
        }

        out.add(packet);
    }
}
