package me.mega.yetanotherchat.network.handshake.server.packet;

import me.mega.yetanotherchat.network.Packet;
import me.mega.yetanotherchat.network.PacketDataSerializer;
import me.mega.yetanotherchat.network.handshake.server.PacketHandlerHandshakingIn;

public class PacketHandshakingInStart implements Packet<PacketHandlerHandshakingIn> {
    private final int protocolVersion;

    public PacketHandshakingInStart(final PacketDataSerializer packetDataSerializer) {
        this.protocolVersion = packetDataSerializer.readInt();
    }

    public PacketHandshakingInStart(final int protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    @Override
    public void writeData(final PacketDataSerializer packetDataSerializer) {
        packetDataSerializer.writeInt(this.protocolVersion);
    }

    @Override
    public void handle(final PacketHandlerHandshakingIn packetHandler) {
        packetHandler.handle(this);
    }

    public int getProtocolVersion() {
        return this.protocolVersion;
    }
}
