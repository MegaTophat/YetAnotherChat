package me.mega.yetanotherchat.network.chat.client;

import me.mega.yetanotherchat.client.YacClient;
import me.mega.yetanotherchat.network.NetworkManager;
import me.mega.yetanotherchat.network.chat.client.packet.PacketChatOutMessage;

public class ClientChatHandler implements PacketHandlerChatOut {
    private final YacClient yacClient;
    private final NetworkManager networkManager;

    public ClientChatHandler(final YacClient yacClient, final NetworkManager networkManager) {
        this.yacClient = yacClient;
        this.networkManager = networkManager;
    }

    @Override
    public void handle(final PacketChatOutMessage packetChatOutMessage) {

    }
}
