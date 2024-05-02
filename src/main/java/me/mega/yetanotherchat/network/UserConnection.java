package me.mega.yetanotherchat.network;

import me.mega.yetanotherchat.packet.PacketChatInMessage;
import me.mega.yetanotherchat.packet.PacketHandlerChatIn;
import me.mega.yetanotherchat.server.YacServer;

public class UserConnection implements PacketHandlerChatIn {
    private final YacServer yacServer;
    private final NetworkManager networkManager;

    public UserConnection(final YacServer yacServer, final NetworkManager networkManager) {
        this.yacServer = yacServer;
        this.networkManager = networkManager;
    }

    @Override
    public void handle(final PacketChatInMessage packetChatInMessage) {

    }
}
