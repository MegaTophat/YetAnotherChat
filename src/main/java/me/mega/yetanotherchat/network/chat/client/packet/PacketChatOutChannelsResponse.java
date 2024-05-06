package me.mega.yetanotherchat.network.chat.client.packet;

import me.mega.yetanotherchat.data.Channel;
import me.mega.yetanotherchat.network.Packet;
import me.mega.yetanotherchat.network.PacketDataSerializer;
import me.mega.yetanotherchat.network.chat.client.PacketHandlerChatOut;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PacketChatOutChannelsResponse implements Packet<PacketHandlerChatOut> {
    private final List<String> channelNames;

    public PacketChatOutChannelsResponse(final PacketDataSerializer packetDataSerializer) {
        this.channelNames = new ArrayList<>();
        final String[] channelNames = packetDataSerializer.readString(Short.MAX_VALUE).split(",");

        this.channelNames.addAll(Arrays.stream(channelNames).toList());
    }

    public PacketChatOutChannelsResponse(final List<Channel> channels) {
        this.channelNames = channels.stream().map(Channel::getChannelName).toList();
    }

    public List<String> getChannelNames() {
        return this.channelNames;
    }

    @Override
    public void writeData(final PacketDataSerializer packetDataSerializer) throws IOException {
        final String channelNamesString = this.channelNames.toString();

        packetDataSerializer.writeString(channelNamesString.substring(1, channelNamesString.length() - 1));
    }

    @Override
    public void handle(final PacketHandlerChatOut packetHandler) {
        packetHandler.handle(this);
    }
}
