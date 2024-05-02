package me.mega.yetanotherchat.packet;

public interface PacketHandshakingOutHandler extends PacketHandler {
    void handle(final PacketHandshakingOutDisconnect packetHandshakingOutDisconnect);

    void handle(final PacketHandshakingOutReady packetHandshakingOutReady);
}
