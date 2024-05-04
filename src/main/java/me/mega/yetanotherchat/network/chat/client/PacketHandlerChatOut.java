package me.mega.yetanotherchat.network.chat.client;

import me.mega.yetanotherchat.network.chat.client.packet.PacketChatOutMessage;
import me.mega.yetanotherchat.network.PacketHandler;

public interface PacketHandlerChatOut extends PacketHandler {
    void handle(final PacketChatOutMessage packetChatOutMessage);
}
