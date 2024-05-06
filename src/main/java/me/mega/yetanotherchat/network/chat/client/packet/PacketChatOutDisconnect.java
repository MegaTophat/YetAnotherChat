package me.mega.yetanotherchat.network.chat.client.packet;

import me.mega.yetanotherchat.network.Packet;
import me.mega.yetanotherchat.network.PacketDataSerializer;
import me.mega.yetanotherchat.network.chat.client.PacketHandlerChatOut;

import java.io.IOException;

public class PacketChatOutDisconnect implements Packet<PacketHandlerChatOut> {
    private final String disconnectReason;

    public PacketChatOutDisconnect(final PacketDataSerializer packetDataSerializer) {
        this.disconnectReason = packetDataSerializer.readString(Short.MAX_VALUE);
    }

    public PacketChatOutDisconnect(final String disconnectReason) {
        this.disconnectReason = disconnectReason;
    }

    public String getDisconnectReason() {
        return this.disconnectReason;
    }

    @Override
    public void writeData(final PacketDataSerializer packetDataSerializer) throws IOException {
        packetDataSerializer.writeString(this.disconnectReason);
    }

    @Override
    public void handle(final PacketHandlerChatOut packetHandler) {
        packetHandler.handle(this);
    }
}
