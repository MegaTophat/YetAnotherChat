package me.mega.yetanotherchat.network.chat.client;

import me.mega.yetanotherchat.network.chat.client.packet.PacketChatOutChannelsResponse;
import me.mega.yetanotherchat.network.chat.client.packet.PacketChatOutDisconnect;
import me.mega.yetanotherchat.network.chat.client.packet.PacketChatOutMessage;
import me.mega.yetanotherchat.network.PacketHandler;
import me.mega.yetanotherchat.network.chat.client.packet.PacketChatOutNotInChannel;
import me.mega.yetanotherchat.network.chat.client.packet.PacketChatOutSystemMessage;
import me.mega.yetanotherchat.network.chat.client.packet.PacketChatOutUserJoinedChannel;
import me.mega.yetanotherchat.network.chat.client.packet.PacketChatOutUserLeftChannel;

public interface PacketHandlerChatOut extends PacketHandler {
    void handle(final PacketChatOutMessage packetChatOutMessage);
    void handle(final PacketChatOutSystemMessage packetChatOutSystemMessage);
    void handle(final PacketChatOutDisconnect packetChatOutDisconnect);
    void handle(final PacketChatOutUserJoinedChannel packetChatOutUserJoinedChannel);
    void handle(final PacketChatOutUserLeftChannel packetChatOutUserLeftChannel);
    void handle(final PacketChatOutNotInChannel packetChatOutNotInChannel);
    void handle(final PacketChatOutChannelsResponse packetChatOutChannelsResponse);
}
