package me.mega.yetanotherchat.network.chat.server.packet;

import me.mega.yetanotherchat.network.chat.server.PacketHandlerChatIn;
import me.mega.yetanotherchat.network.Packet;
import me.mega.yetanotherchat.network.PacketDataSerializer;

public class PacketChatInMessage implements Packet<PacketHandlerChatIn> {
    private final String message;

    public PacketChatInMessage(final PacketDataSerializer packetDataSerializer) {
        this.message = packetDataSerializer.readString(Short.MAX_VALUE);
    }

    public PacketChatInMessage(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    public void writeData(final PacketDataSerializer packetDataSerializer) {
        packetDataSerializer.writeString(this.message);
    }

    @Override
    public void handle(final PacketHandlerChatIn packetHandler) {
        packetHandler.handle(this);
    }
}
