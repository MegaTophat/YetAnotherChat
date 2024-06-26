package me.mega.yetanotherchat.network.chat.client.packet;

import me.mega.yetanotherchat.network.Packet;
import me.mega.yetanotherchat.network.PacketDataSerializer;
import me.mega.yetanotherchat.network.chat.client.PacketHandlerChatOut;

import java.io.IOException;

public class PacketChatOutNotInChannel implements Packet<PacketHandlerChatOut> {
    public PacketChatOutNotInChannel(final PacketDataSerializer packetDataSerializer) {

    }
    @Override
    public void writeData(final PacketDataSerializer packetDataSerializer) throws IOException {

    }

    @Override
    public void handle(final PacketHandlerChatOut packetHandler) {
        packetHandler.handle(this);
    }
}
