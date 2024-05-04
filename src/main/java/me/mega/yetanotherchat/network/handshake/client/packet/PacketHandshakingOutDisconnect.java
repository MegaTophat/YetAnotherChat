package me.mega.yetanotherchat.network.handshake.client.packet;

import me.mega.yetanotherchat.network.Packet;
import me.mega.yetanotherchat.network.PacketDataSerializer;
import me.mega.yetanotherchat.network.handshake.client.PacketHandlerHandshakingOut;

public class PacketHandshakingOutDisconnect implements Packet<PacketHandlerHandshakingOut> {
    private final String disconnectReason;

    public PacketHandshakingOutDisconnect(final String disconnectReason) {
        this.disconnectReason = disconnectReason;
    }

    public PacketHandshakingOutDisconnect(final PacketDataSerializer packetDataSerializer) {
        this.disconnectReason = packetDataSerializer.readString(Short.MAX_VALUE);
    }

    public String getDisconnectReason() {
        return this.disconnectReason;
    }

    @Override
    public void writeData(final PacketDataSerializer packetDataSerializer) {
        packetDataSerializer.writeString(this.disconnectReason);
    }

    @Override
    public void handle(final PacketHandlerHandshakingOut packetHandler) {
        packetHandler.handle(this);
    }
}
