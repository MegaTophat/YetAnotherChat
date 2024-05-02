package me.mega.yetanotherchat.network.client;

import me.mega.yetanotherchat.client.YacClient;
import me.mega.yetanotherchat.network.NetworkManager;
import me.mega.yetanotherchat.network.UserConnection;
import me.mega.yetanotherchat.packet.PacketHandshakingInStart;
import me.mega.yetanotherchat.packet.PacketHandshakingOutDisconnect;
import me.mega.yetanotherchat.packet.PacketHandshakingOutHandler;
import me.mega.yetanotherchat.packet.PacketHandshakingOutReady;
import me.mega.yetanotherchat.server.YacServer;

public class HandshakeHandler implements PacketHandshakingOutHandler {
    private final YacClient yacClient;
    private final NetworkManager networkManager;
    private final HandshakeState currentState;

    public HandshakeHandler(final YacClient yacClient, final NetworkManager networkManager) {
        this.yacClient = yacClient;
        this.networkManager = networkManager;
        this.currentState = HandshakeState.HELLO;
    }

    @Override
    public void handle(final PacketHandshakingInStart packetHandshakingInStart) {
        final int protocolVersion = packetHandshakingInStart.getProtocolVersion();

        if (protocolVersion > YacServer.PROTOCOL_VERSION) {
            this.networkManager.handle(new PacketHandshakingOutDisconnect("Unsupported version! This server hasn't updated yet!"));
            this.networkManager.close();

            return;
        }
        if (protocolVersion < YacServer.PROTOCOL_VERSION) {
            this.networkManager.handle(new PacketHandshakingOutDisconnect("Unsupported version! Your client is out of date!"));
            this.networkManager.close();

            return;
        }

        this.networkManager.handle(new PacketHandshakingOutReady());
        this.networkManager.setPacketListener(new UserConnection(this.yacClient, this.networkManager));
    }

    @Override
    public void handle(final PacketHandshakingOutDisconnect packetHandshakingOutDisconnect) {
        System.out.println("Server rejected the connection! Reason: " + packetHandshakingOutDisconnect.getDisconnectReason());

        this.networkManager.close();
    }

    @Override
    public void handle(final PacketHandshakingOutReady packetHandshakingOutReady) {
        System.out.println("Server accepted our handshake! We're doing nothing for now though");
        this.networkManager.setPacketListener(new UserConnection());

    }

    private enum HandshakeState {
        HELLO, WAITING_FOR_CONFIRM
    }
}
