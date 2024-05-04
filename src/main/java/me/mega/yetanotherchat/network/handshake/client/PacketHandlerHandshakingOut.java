package me.mega.yetanotherchat.network.handshake.client;

import me.mega.yetanotherchat.network.handshake.client.packet.PacketHandshakingOutDisconnect;
import me.mega.yetanotherchat.network.handshake.client.packet.PacketHandshakingOutReady;
import me.mega.yetanotherchat.network.PacketHandler;

public interface PacketHandlerHandshakingOut extends PacketHandler {
    void handle(final PacketHandshakingOutDisconnect packetHandshakingOutDisconnect);

    void handle(final PacketHandshakingOutReady packetHandshakingOutReady);
}
