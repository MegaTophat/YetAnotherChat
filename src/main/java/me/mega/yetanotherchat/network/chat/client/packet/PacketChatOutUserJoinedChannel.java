package me.mega.yetanotherchat.network.chat.client.packet;

import me.mega.yetanotherchat.data.Channel;
import me.mega.yetanotherchat.data.User;
import me.mega.yetanotherchat.network.Packet;
import me.mega.yetanotherchat.network.PacketDataSerializer;
import me.mega.yetanotherchat.network.chat.client.PacketHandlerChatOut;

import java.io.IOException;

public class PacketChatOutUserJoinedChannel implements Packet<PacketHandlerChatOut> {
    private final User userJoining;
    private final Channel channel;

    public PacketChatOutUserJoinedChannel(final PacketDataSerializer packetDataSerializer) {
        this.userJoining = new User(packetDataSerializer.readString(16), packetDataSerializer.readBoolean());
        this.channel = new Channel(packetDataSerializer.readString(16));
    }

    public PacketChatOutUserJoinedChannel(final User userLeaving, final Channel channel) {
        this.userJoining = userLeaving;
        this.channel = channel;
    }

    public String getJoiningUserName() {
        return this.userJoining.getName();
    }

    @Override
    public void writeData(final PacketDataSerializer packetDataSerializer) throws IOException {
        packetDataSerializer.writeString(this.userJoining.getName());
        packetDataSerializer.writeBoolean(this.userJoining.isAdmin());
        packetDataSerializer.writeString(this.channel.getChannelName());
    }

    @Override
    public void handle(final PacketHandlerChatOut packetHandler) {
        packetHandler.handle(this);
    }
}
