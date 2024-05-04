package me.mega.yetanotherchat.network.handshake.server.packet;

import me.mega.yetanotherchat.network.handshake.server.PacketHandlerHandshakingIn;
import me.mega.yetanotherchat.network.Packet;
import me.mega.yetanotherchat.network.PacketDataSerializer;

public class PacketHandshakingInStart implements Packet<PacketHandlerHandshakingIn> {
    private final int protocolVersion;

    @Override
    public void writeData(final PacketDataSerializer packetDataSerializer) {
        packetDataSerializer.writeInt(this.protocolVersion);
    }

    public PacketHandshakingInStart(final PacketDataSerializer packetDataSerializer) {
        this.protocolVersion = packetDataSerializer.readInt();
    }

    @Override
    public void handle(final PacketHandlerHandshakingIn packetHandshakingInListener) {
        packetHandshakingInListener.handle(this);
    }

    public int getProtocolVersion() {
        return this.protocolVersion;
    }
}
