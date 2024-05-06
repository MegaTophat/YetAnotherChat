package me.mega.yetanotherchat.network.chat.client.packet;

import me.mega.yetanotherchat.data.User;
import me.mega.yetanotherchat.network.chat.client.PacketHandlerChatOut;
import me.mega.yetanotherchat.network.Packet;
import me.mega.yetanotherchat.network.PacketDataSerializer;

import java.io.IOException;

public class PacketChatOutMessage implements Packet<PacketHandlerChatOut> {
    private final String message;
    private final User sender;

    public PacketChatOutMessage(final PacketDataSerializer packetDataSerializer) {
        this.message = packetDataSerializer.readString(Short.MAX_VALUE);
        this.sender = new User(packetDataSerializer.readString(16), packetDataSerializer.readBoolean());
    }

    public PacketChatOutMessage(final String message, final User sender) {
        this.message = message;
        this.sender = sender;
    }

    public User getSender() {
        return this.sender;
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    public void writeData(final PacketDataSerializer packetDataSerializer) throws IOException {
        packetDataSerializer.writeString(this.message);
        packetDataSerializer.writeString(this.sender.getName());
        packetDataSerializer.writeBoolean(this.sender.isAdmin());
    }

    @Override
    public void handle(final PacketHandlerChatOut packetHandler) {
        packetHandler.handle(this);
    }
}
