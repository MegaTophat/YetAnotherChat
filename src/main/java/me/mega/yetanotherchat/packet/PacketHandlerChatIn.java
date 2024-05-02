package me.mega.yetanotherchat.packet;

public interface PacketHandlerChatIn extends PacketHandler {
    void handle(final PacketChatInMessage packetChatInMessage);
}
