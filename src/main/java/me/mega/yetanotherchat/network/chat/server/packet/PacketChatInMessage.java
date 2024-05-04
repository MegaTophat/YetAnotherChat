package me.mega.yetanotherchat.network.chat.server.packet;

import me.mega.yetanotherchat.network.chat.server.PacketHandlerChatIn;
import me.mega.yetanotherchat.network.Packet;
import me.mega.yetanotherchat.network.PacketDataSerializer;

public class PacketChatInMessage implements Packet<PacketHandlerChatIn> {
    private final String messageReceived;

    public PacketChatInMessage(final PacketDataSerializer packetDataSerializer) {
        this.messageReceived = packetDataSerializer.readString(Short.MAX_VALUE);
    }

    @Override
    public void writeData(final PacketDataSerializer packetDataSerializer) {
        packetDataSerializer.writeString(this.messageReceived);
    }

    @Override
    public void handle(final PacketHandlerChatIn packetHandler) {
        packetHandler.handle(this);
    }
}
