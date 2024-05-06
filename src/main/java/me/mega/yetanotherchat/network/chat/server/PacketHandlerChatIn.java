package me.mega.yetanotherchat.network.chat.server;

import me.mega.yetanotherchat.network.chat.server.packet.PacketChatInCommand;
import me.mega.yetanotherchat.network.chat.server.packet.PacketChatInLeaveChannel;
import me.mega.yetanotherchat.network.chat.server.packet.PacketChatInMessage;
import me.mega.yetanotherchat.network.PacketHandler;
import me.mega.yetanotherchat.network.chat.server.packet.PacketChatInRequestChannels;
import me.mega.yetanotherchat.network.chat.server.packet.PacketChatInRequestJoinChannel;

public interface PacketHandlerChatIn extends PacketHandler {
    void handle(final PacketChatInMessage packetChatInMessage);
    void handle(final PacketChatInCommand packetChatInCommand);
    void handle(final PacketChatInLeaveChannel packetChatInLeaveChannel);
    void handle(final PacketChatInRequestJoinChannel packetChatInRequestJoinChannel);
    void handle(final PacketChatInRequestChannels packetChatInRequestChannels);
}
