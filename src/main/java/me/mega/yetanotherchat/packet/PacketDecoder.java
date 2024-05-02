package me.mega.yetanotherchat.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import me.mega.yetanotherchat.network.ProtocolDirection;
import me.mega.yetanotherchat.network.NetworkManager;

import java.io.IOException;
import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {
    private final ProtocolDirection protocolDirection;

    public PacketDecoder(final ProtocolDirection protocolDirection) {
        this.protocolDirection = protocolDirection;
    }

    @Override
    protected void decode(final ChannelHandlerContext ctx, final ByteBuf in, final List<Object> out) throws IOException {
        if (in.readableBytes() <= 0) {
            return;
        }

        final PacketDataSerializer packetDataSerializer = new PacketDataSerializer(in);
        final int packetID = packetDataSerializer.readInt();
        final Packet<?> packet = ctx.channel().attr(NetworkManager.currentProtocol).get().createPacket(this.protocolDirection, packetID);

        if (packet == null) {
            throw new IOException("");
        }

        packet.readData(packetDataSerializer);

        if (packetDataSerializer.readableBytes() > 0) {
            throw new IOException("");
        }

        out.add(packet);
    }
}
