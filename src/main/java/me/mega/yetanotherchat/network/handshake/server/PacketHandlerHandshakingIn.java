package me.mega.yetanotherchat.network.handshake.server;

import me.mega.yetanotherchat.network.PacketHandler;
import me.mega.yetanotherchat.network.handshake.server.packet.PacketHandshakingInStart;

public interface PacketHandlerHandshakingIn extends PacketHandler {
    void handle(final PacketHandshakingInStart packetHandshakingInStart);
}
