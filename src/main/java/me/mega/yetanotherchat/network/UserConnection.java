package me.mega.yetanotherchat.network;

import me.mega.yetanotherchat.data.User;
import me.mega.yetanotherchat.network.chat.client.packet.PacketChatOutMessage;
import me.mega.yetanotherchat.network.chat.server.packet.PacketChatInCommand;
import me.mega.yetanotherchat.network.chat.server.packet.PacketChatInLeaveChannel;
import me.mega.yetanotherchat.network.chat.server.packet.PacketChatInMessage;
import me.mega.yetanotherchat.network.chat.server.PacketHandlerChatIn;
import me.mega.yetanotherchat.network.chat.server.packet.PacketChatInRequestChannels;
import me.mega.yetanotherchat.network.chat.server.packet.PacketChatInRequestJoinChannel;
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
        this.yacServer.getServerConnection().sendPacketToAll(new PacketChatOutMessage(packetChatInMessage.getMessage(), new User("Bob", false)));
    }

    @Override
    public void handle(final PacketChatInCommand packetChatInCommand) {
        final String command = packetChatInCommand.getCommand();

        if (command.equalsIgnoreCase("stop")) {
            System.exit(0);
        }
    }

    @Override
    public void handle(final PacketChatInLeaveChannel packetChatInLeaveChannel) {

    }

    @Override
    public void handle(final PacketChatInRequestJoinChannel packetChatInRequestJoinChannel) {

    }

    @Override
    public void handle(final PacketChatInRequestChannels packetChatInRequestChannels) {

    }
}
