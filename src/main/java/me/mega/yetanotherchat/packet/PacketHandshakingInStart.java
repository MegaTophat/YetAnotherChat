package me.mega.yetanotherchat.packet;

public class PacketHandshakingInStart implements Packet<PacketHandshakingInHandler> {
    private int protocolVersion;

    @Override
    public void writeData(final PacketDataSerializer packetDataSerializer) {
        packetDataSerializer.writeInt(this.protocolVersion);
    }

    @Override
    public void readData(final PacketDataSerializer packetDataSerializer) {
        this.protocolVersion = packetDataSerializer.readInt();
    }

    @Override
    public void handle(final PacketHandshakingInHandler packetHandshakingInListener) {
        packetHandshakingInListener.handle(this);
    }

    public int getProtocolVersion() {
        return this.protocolVersion;
    }
}
