package me.mega.yetanotherchat.network.chat.server.packet;

import me.mega.yetanotherchat.network.Packet;
import me.mega.yetanotherchat.network.PacketDataSerializer;
import me.mega.yetanotherchat.network.chat.server.PacketHandlerChatIn;

import java.io.IOException;

public class PacketChatInCommand implements Packet<PacketHandlerChatIn> {
    private final String command;

    public PacketChatInCommand(final PacketDataSerializer packetDataSerializer) {
        this.command = packetDataSerializer.readString(Short.MAX_VALUE);
    }

    public PacketChatInCommand(final String command) {
        this.command = command;
    }

    public String getCommand() {
        return this.command;
    }

    @Override
    public void writeData(final PacketDataSerializer packetDataSerializer) throws IOException {
        packetDataSerializer.writeString(this.command);
    }

    @Override
    public void handle(final PacketHandlerChatIn packetHandler) {
        packetHandler.handle(this);
    }
}
