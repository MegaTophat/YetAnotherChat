package me.mega.yetanotherchat.packet;

import java.io.IOException;

public interface Packet<T extends PacketHandler> {
    void writeData(final PacketDataSerializer packetDataSerializer) throws IOException;

    void readData(final PacketDataSerializer packetDataSerializer) throws IOException;

    void handle(final T packetListener);
}
