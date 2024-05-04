package me.mega.yetanotherchat.network.handshake.server;

import me.mega.yetanotherchat.network.NetworkManager;
import me.mega.yetanotherchat.network.UserConnection;
import me.mega.yetanotherchat.network.handshake.server.packet.PacketHandshakingInStart;
import me.mega.yetanotherchat.network.handshake.client.packet.PacketHandshakingOutDisconnect;
import me.mega.yetanotherchat.network.handshake.client.packet.PacketHandshakingOutReady;
import me.mega.yetanotherchat.server.YacServer;

public class ServerHandshakeHandler implements PacketHandlerHandshakingIn {
    private final YacServer yacServer;
    private final NetworkManager networkManager;
    private HandshakeState currentState;

    public ServerHandshakeHandler(final YacServer yacServer, final NetworkManager networkManager) {
        this.yacServer = yacServer;
        this.networkManager = networkManager;
        this.currentState = HandshakeState.HELLO;
    }

    @Override
    public void handle(final PacketHandshakingInStart packetHandshakingInStart) {
        final int protocolVersion = packetHandshakingInStart.getProtocolVersion();

        if (protocolVersion > NetworkManager.PROTOCOL_VERSION) {
            this.networkManager.sendPacket(new PacketHandshakingOutDisconnect("Unsupported version! This server hasn't updated yet!"));
            this.networkManager.close();

            return;
        }
        if (protocolVersion < NetworkManager.PROTOCOL_VERSION) {
            this.networkManager.sendPacket(new PacketHandshakingOutDisconnect("Unsupported version! Your client is out of date!"));
            this.networkManager.close();

            return;
        }

        this.networkManager.sendPacket(new PacketHandshakingOutDisconnect("test"));
        this.networkManager.close();
        System.out.println("closed connection");

        if (true) {
            return;
        }

        this.networkManager.sendPacket(new PacketHandshakingOutReady());
        this.networkManager.setPacketHandler(new UserConnection(this.yacServer, this.networkManager));
    }

    private enum HandshakeState {
        HELLO, WAITING_FOR_CONFIRM
    }
}
