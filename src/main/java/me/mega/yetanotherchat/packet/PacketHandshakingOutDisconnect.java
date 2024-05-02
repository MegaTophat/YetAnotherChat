package me.mega.yetanotherchat.packet;

public class PacketHandshakingOutDisconnect implements Packet<PacketHandshakingOutHandler> {
    private String disconnectReason;

    public PacketHandshakingOutDisconnect(final String disconnectReason) {
        this.disconnectReason = disconnectReason;
    }

    public String getDisconnectReason() {
        return this.disconnectReason;
    }

    @Override
    public void readData(final PacketDataSerializer packetDataSerializer) {
        this.disconnectReason = packetDataSerializer.readString(Short.MAX_VALUE);
    }

    @Override
    public void writeData(final PacketDataSerializer packetDataSerializer) {
        packetDataSerializer.writeString(this.disconnectReason);
    }

    @Override
    public void handle(final PacketHandshakingOutHandler packetHandshakingOutListener) {
        packetHandshakingOutListener.handle(this);
    }
}
