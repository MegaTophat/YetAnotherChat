package me.mega.yetanotherchat.network.chat.server.packet;

import me.mega.yetanotherchat.network.Packet;
import me.mega.yetanotherchat.network.PacketDataSerializer;
import me.mega.yetanotherchat.network.chat.server.PacketHandlerChatIn;

import java.io.IOException;

public class PacketChatInRequestChannels implements Packet<PacketHandlerChatIn> {
    public PacketChatInRequestChannels(final PacketDataSerializer packetDataSerializer) {

    }
    @Override
    public void writeData(final PacketDataSerializer packetDataSerializer) throws IOException {

    }

    @Override
    public void handle(final PacketHandlerChatIn packetHandler) {
        packetHandler.handle(this);
    }
}
