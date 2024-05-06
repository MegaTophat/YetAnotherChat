package me.mega.yetanotherchat.network.chat.client.packet;

import me.mega.yetanotherchat.network.Packet;
import me.mega.yetanotherchat.network.PacketDataSerializer;
import me.mega.yetanotherchat.network.chat.client.PacketHandlerChatOut;

import java.io.IOException;

public class PacketChatOutSystemMessage implements Packet<PacketHandlerChatOut> {
    private final String systemMessage;

    public PacketChatOutSystemMessage(final PacketDataSerializer packetDataSerializer) {
        this.systemMessage = packetDataSerializer.readString(Short.MAX_VALUE);
    }

    public String getSystemMessage() {
        return this.systemMessage;
    }

    @Override
    public void writeData(final PacketDataSerializer packetDataSerializer) throws IOException {
        packetDataSerializer.writeString(this.systemMessage);
    }

    @Override
    public void handle(final PacketHandlerChatOut packetHandler) {
        packetHandler.handle(this);
    }
}
