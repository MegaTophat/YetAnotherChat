package me.mega.yetanotherchat.network.chat.server.packet;

import me.mega.yetanotherchat.data.Channel;
import me.mega.yetanotherchat.network.Packet;
import me.mega.yetanotherchat.network.PacketDataSerializer;
import me.mega.yetanotherchat.network.chat.server.PacketHandlerChatIn;

import java.io.IOException;

public class PacketChatInRequestJoinChannel implements Packet<PacketHandlerChatIn> {
    private final Channel requestedChannel;

    public PacketChatInRequestJoinChannel(final PacketDataSerializer packetDataSerializer) {
        this.requestedChannel = new Channel(packetDataSerializer.readString(16));
    }

    public Channel getRequestedChannel() {
        return this.requestedChannel;
    }

    @Override
    public void writeData(final PacketDataSerializer packetDataSerializer) throws IOException {
        packetDataSerializer.writeString(this.requestedChannel.getChannelName());
    }

    @Override
    public void handle(final PacketHandlerChatIn packetHandler) {
        packetHandler.handle(this);
    }
}
