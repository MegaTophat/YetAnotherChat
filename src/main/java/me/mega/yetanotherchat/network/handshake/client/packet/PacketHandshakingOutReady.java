package me.mega.yetanotherchat.network.handshake.client.packet;

import me.mega.yetanotherchat.network.Packet;
import me.mega.yetanotherchat.network.PacketDataSerializer;
import me.mega.yetanotherchat.network.handshake.client.PacketHandlerHandshakingOut;

public class PacketHandshakingOutReady implements Packet<PacketHandlerHandshakingOut> {
    public PacketHandshakingOutReady(final PacketDataSerializer packetDataSerializer) {

    }

    public PacketHandshakingOutReady() {

    }

    @Override
    public void writeData(final PacketDataSerializer packetDataSerializer) {

    }

    @Override
    public void handle(final PacketHandlerHandshakingOut packetListener) {
        packetListener.handle(this);
    }
}
