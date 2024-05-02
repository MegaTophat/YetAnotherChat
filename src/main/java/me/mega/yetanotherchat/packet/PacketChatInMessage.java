package me.mega.yetanotherchat.packet;

public class PacketChatInMessage implements Packet<PacketHandlerChatIn> {
    private String messageReceived;

    @Override
    public void writeData(final PacketDataSerializer packetDataSerializer) {
        packetDataSerializer.writeString(this.messageReceived);
    }

    @Override
    public void readData(final PacketDataSerializer packetDataSerializer) {
        this.messageReceived = packetDataSerializer.readString(Short.MAX_VALUE);
    }

    @Override
    public void handle(final PacketHandlerChatIn packetListener) {
        packetListener.handle(this);
    }
}
