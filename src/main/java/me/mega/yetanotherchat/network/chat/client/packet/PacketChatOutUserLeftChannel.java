package me.mega.yetanotherchat.network.chat.client.packet;

import me.mega.yetanotherchat.data.Channel;
import me.mega.yetanotherchat.data.User;
import me.mega.yetanotherchat.network.Packet;
import me.mega.yetanotherchat.network.PacketDataSerializer;
import me.mega.yetanotherchat.network.chat.client.PacketHandlerChatOut;

import java.io.IOException;

public class PacketChatOutUserLeftChannel implements Packet<PacketHandlerChatOut> {
    private final User userLeaving;
    private final Channel channel;

    public PacketChatOutUserLeftChannel(final PacketDataSerializer packetDataSerializer) {
        this.userLeaving = new User(packetDataSerializer.readString(16), packetDataSerializer.readBoolean());
        this.channel = new Channel(packetDataSerializer.readString(16));
    }

    public PacketChatOutUserLeftChannel(final User userLeaving, final Channel channel) {
        this.userLeaving = userLeaving;
        this.channel = channel;
    }

    public String getLeavingUserName() {
        return this.userLeaving.getName();
    }

    @Override
    public void writeData(final PacketDataSerializer packetDataSerializer) throws IOException {
        packetDataSerializer.writeString(this.userLeaving.getName());
        packetDataSerializer.writeBoolean(this.userLeaving.isAdmin());
        packetDataSerializer.writeString(this.channel.getChannelName());
    }

    @Override
    public void handle(final PacketHandlerChatOut packetHandler) {
        packetHandler.handle(this);
    }
}
