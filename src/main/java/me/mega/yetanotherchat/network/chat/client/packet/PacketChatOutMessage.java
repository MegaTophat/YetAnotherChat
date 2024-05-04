package me.mega.yetanotherchat.network.chat.client.packet;

import me.mega.yetanotherchat.network.chat.client.PacketHandlerChatOut;
import me.mega.yetanotherchat.network.Packet;
import me.mega.yetanotherchat.network.PacketDataSerializer;

import java.io.IOException;

public class PacketChatOutMessage implements Packet<PacketHandlerChatOut> {
    public PacketChatOutMessage(final PacketDataSerializer packetDataSerializer) {

    }

    @Override
    public void writeData(final PacketDataSerializer packetDataSerializer) throws IOException {

    }

    @Override
    public void handle(final PacketHandlerChatOut packetHandler) {
        packetHandler.handle(this);
    }
}
