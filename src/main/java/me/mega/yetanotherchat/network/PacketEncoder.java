package me.mega.yetanotherchat.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.IOException;

public class PacketEncoder extends MessageToByteEncoder<Packet<?>> {
    private final ProtocolDirection protocolDirection;

    public PacketEncoder(final ProtocolDirection protocolDirection) {
        this.protocolDirection = protocolDirection;
    }

    @Override
    protected void encode(final ChannelHandlerContext ctx, final Packet<?> msg, final ByteBuf out) throws IOException {
        final int packetID = ctx.channel().attr(NetworkManager.currentProtocol).get().getPacketID(this.protocolDirection, msg);

        if (packetID == Protocol.INVALID_ID) {
            throw new IOException("Unregistered packet was committed to the pipeline?");
        }

        final PacketDataSerializer packetDataSerializer = new PacketDataSerializer(out);

        packetDataSerializer.writeInt(packetID);

        try {
            msg.writeData(packetDataSerializer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (final Throwable throwable) {

        }
    }
}
