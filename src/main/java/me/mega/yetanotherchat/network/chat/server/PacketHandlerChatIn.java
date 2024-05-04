package me.mega.yetanotherchat.network.chat.server;

import me.mega.yetanotherchat.network.chat.server.packet.PacketChatInMessage;
import me.mega.yetanotherchat.network.PacketHandler;

public interface PacketHandlerChatIn extends PacketHandler {
    void handle(final PacketChatInMessage packetChatInMessage);
}
