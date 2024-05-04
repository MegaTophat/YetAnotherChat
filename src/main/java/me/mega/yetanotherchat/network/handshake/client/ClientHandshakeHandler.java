package me.mega.yetanotherchat.network.handshake.client;

import me.mega.yetanotherchat.client.YacClient;
import me.mega.yetanotherchat.network.NetworkManager;
import me.mega.yetanotherchat.network.chat.client.ClientChatHandler;
import me.mega.yetanotherchat.network.handshake.client.packet.PacketHandshakingOutDisconnect;
import me.mega.yetanotherchat.network.handshake.client.packet.PacketHandshakingOutReady;

public class ClientHandshakeHandler implements PacketHandlerHandshakingOut {
    private final YacClient yacClient;
    private final NetworkManager networkManager;
    private final HandshakeState currentState;

    public ClientHandshakeHandler(final YacClient yacClient, final NetworkManager networkManager) {
        this.yacClient = yacClient;
        this.networkManager = networkManager;
        this.currentState = HandshakeState.HELLO;
    }

    @Override
    public void handle(final PacketHandshakingOutDisconnect packetHandshakingOutDisconnect) {
        System.out.println("Server rejected the connection! Reason: " + packetHandshakingOutDisconnect.getDisconnectReason());

        this.networkManager.close();
    }

    @Override
    public void handle(final PacketHandshakingOutReady packetHandshakingOutReady) {
        System.out.println("Server accepted our handshake! We're doing nothing for now though");
        this.networkManager.setPacketHandler(new ClientChatHandler(this.yacClient, this.networkManager));

    }

    private enum HandshakeState {
        HELLO, WAITING_FOR_CONFIRM
    }
}
