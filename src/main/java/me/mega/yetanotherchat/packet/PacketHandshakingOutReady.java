package me.mega.yetanotherchat.packet;

public class PacketHandshakingOutReady implements Packet<PacketHandshakingOutHandler> {
    @Override
    public void writeData(final PacketDataSerializer packetDataSerializer) {

    }

    @Override
    public void readData(final PacketDataSerializer packetDataSerializer) {

    }

    @Override
    public void handle(final PacketHandshakingOutHandler packetListener) {
        packetListener.handle(this);
    }
}
