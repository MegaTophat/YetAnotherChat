package me.mega.yetanotherchat.network.server;

import me.mega.yetanotherchat.network.NetworkManager;
import me.mega.yetanotherchat.network.UserConnection;
import me.mega.yetanotherchat.packet.PacketHandshakingInHandler;
import me.mega.yetanotherchat.packet.PacketHandshakingInStart;
import me.mega.yetanotherchat.packet.PacketHandshakingOutDisconnect;
import me.mega.yetanotherchat.packet.PacketHandshakingOutReady;
import me.mega.yetanotherchat.server.YacServer;

public class HandshakeHandler implements PacketHandshakingInHandler {
    private final YacServer yacServer;
    private final NetworkManager networkManager;
    private HandshakeState currentState;

    public HandshakeHandler(final YacServer yacServer, final NetworkManager networkManager) {
        this.yacServer = yacServer;
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
        this.networkManager.setPacketListener(new UserConnection(this.yacServer, this.networkManager));
    }

    private enum HandshakeState {
        HELLO, WAITING_FOR_CONFIRM
    }
}
