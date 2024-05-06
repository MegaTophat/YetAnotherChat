package me.mega.yetanotherchat.network.chat.client;

import me.mega.yetanotherchat.client.YacClient;
import me.mega.yetanotherchat.network.NetworkManager;
import me.mega.yetanotherchat.network.chat.client.packet.PacketChatOutChannelsResponse;
import me.mega.yetanotherchat.network.chat.client.packet.PacketChatOutDisconnect;
import me.mega.yetanotherchat.network.chat.client.packet.PacketChatOutMessage;
import me.mega.yetanotherchat.network.chat.client.packet.PacketChatOutNotInChannel;
import me.mega.yetanotherchat.network.chat.client.packet.PacketChatOutSystemMessage;
import me.mega.yetanotherchat.network.chat.client.packet.PacketChatOutUserJoinedChannel;
import me.mega.yetanotherchat.network.chat.client.packet.PacketChatOutUserLeftChannel;

public class ClientChatHandler implements PacketHandlerChatOut {
    private final YacClient yacClient;
    private final NetworkManager networkManager;

    public ClientChatHandler(final YacClient yacClient, final NetworkManager networkManager) {
        this.yacClient = yacClient;
        this.networkManager = networkManager;
    }

    @Override
    public void handle(final PacketChatOutMessage packetChatOutMessage) {
        System.out.println(packetChatOutMessage.getSender() + ": " + packetChatOutMessage.getMessage());
    }

    @Override
    public void handle(final PacketChatOutSystemMessage packetChatOutSystemMessage) {
        System.out.println("System Message: " + packetChatOutSystemMessage.getSystemMessage());
    }

    @Override
    public void handle(final PacketChatOutDisconnect packetChatOutDisconnect) {
        System.out.println("Disconnected by server: " + packetChatOutDisconnect.getDisconnectReason());
        this.networkManager.close();
        this.yacClient.setCurrentChannel(null);
    }

    @Override
    public void handle(final PacketChatOutUserJoinedChannel packetChatOutUserJoinedChannel) {
        System.out.println("User joined your channel: " + packetChatOutUserJoinedChannel.getJoiningUserName());
    }

    @Override
    public void handle(final PacketChatOutUserLeftChannel packetChatOutUserLeftChannel) {
        System.out.println("User left your channel: " + packetChatOutUserLeftChannel.getLeavingUserName());
    }

    @Override
    public void handle(final PacketChatOutNotInChannel packetChatOutNotInChannel) {
        System.out.println("System Message: You are not in any channel! Join a channel before sending a message");
    }

    @Override
    public void handle(final PacketChatOutChannelsResponse packetChatOutChannelsResponse) {
        System.out.println("Joinable channels: " + packetChatOutChannelsResponse.getChannelNames());
    }
}
