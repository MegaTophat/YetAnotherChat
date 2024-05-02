package me.mega.yetanotherchat.packet;

public interface PacketHandshakingInHandler extends PacketHandler {
    void handle(final PacketHandshakingInStart packetHandshakingInStart);
}
