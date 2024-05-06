package me.mega.yetanotherchat.network;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import me.mega.yetanotherchat.network.chat.server.packet.PacketChatInRequestChannels;
import me.mega.yetanotherchat.network.chat.client.packet.PacketChatOutMessage;
import me.mega.yetanotherchat.network.chat.client.packet.PacketChatOutSystemMessage;
import me.mega.yetanotherchat.network.chat.server.packet.PacketChatInCommand;
import me.mega.yetanotherchat.network.chat.server.packet.PacketChatInMessage;
import me.mega.yetanotherchat.network.chat.client.packet.PacketChatOutChannelsResponse;
import me.mega.yetanotherchat.network.chat.server.packet.PacketChatInRequestJoinChannel;
import me.mega.yetanotherchat.network.handshake.server.packet.PacketHandshakingInStart;
import me.mega.yetanotherchat.network.handshake.client.packet.PacketHandshakingOutDisconnect;
import me.mega.yetanotherchat.network.handshake.client.packet.PacketHandshakingOutReady;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public enum Protocol {
    HANDSHAKING(0) {
        {
            this.registerPacket(ProtocolDirection.SERVERBOUNDED, PacketHandshakingInStart.class);
            this.registerPacket(ProtocolDirection.CLIENTBOUNDED, PacketHandshakingOutDisconnect.class);
            this.registerPacket(ProtocolDirection.CLIENTBOUNDED, PacketHandshakingOutReady.class);
        }
    },
    CHAT(1) {
        {
            this.registerPacket(ProtocolDirection.SERVERBOUNDED, PacketChatInMessage.class);
            this.registerPacket(ProtocolDirection.SERVERBOUNDED, PacketChatInRequestJoinChannel.class);
            this.registerPacket(ProtocolDirection.SERVERBOUNDED, PacketChatInCommand.class);
            this.registerPacket(ProtocolDirection.SERVERBOUNDED, PacketChatOutChannelsResponse.class);
            this.registerPacket(ProtocolDirection.CLIENTBOUNDED, PacketChatInRequestChannels.class);
            this.registerPacket(ProtocolDirection.CLIENTBOUNDED, PacketChatOutMessage.class);
            this.registerPacket(ProtocolDirection.CLIENTBOUNDED, PacketChatOutSystemMessage.class);
        }
    };

    public static final int INVALID_ID = Integer.MIN_VALUE;
    private static final Map<Class<? extends Packet<?>>, Protocol> packetMap;
    private final Map<ProtocolDirection, BiMap<Integer, Class<? extends Packet<?>>>> packetRegistrations;
    private final int stateId;

    static {
        packetMap = new HashMap<>();

        final Protocol[] protocols = values();
        final int numProtocols = protocols.length;

        for (final Protocol protocol : protocols) {
            for (final BiMap<Integer, Class<? extends Packet<?>>> integerClassBiMap : protocol.packetRegistrations.values()) {
                Class<? extends Packet<?>> clazz;

                for (final Iterator<Class<? extends Packet<?>>> iterator =
                     integerClassBiMap.values().iterator(); iterator.hasNext();
                     Protocol.packetMap.put(clazz, protocol)) {
                    clazz = iterator.next();

                    if (Protocol.packetMap.containsKey(clazz) && Protocol.packetMap.get(clazz) != protocol) {
                        throw new AssertionError("Can't reassign " + protocol + " to protocol! Offending packet: " + clazz);
                    }

                    try {
                        clazz.getDeclaredConstructor(PacketDataSerializer.class);
                    } catch (final NoSuchMethodException ex) {
                        throw new AssertionError("Packet registration failed checks! Packet: " + clazz, ex);
                    }
                }
            }
        }
    }


    Protocol(final int stateId) {
        this.stateId = stateId;
        this.packetRegistrations = new EnumMap<>(ProtocolDirection.class);

        for (final ProtocolDirection protocolDirection : ProtocolDirection.values()) {
            this.packetRegistrations.put(protocolDirection, HashBiMap.create());
        }
    }

    void registerPacket(final ProtocolDirection protocolDirection,
                                        final Class<? extends Packet<?>> packetClass) {
        final BiMap<Integer, Class<? extends Packet<?>>> registeredPackets = this.packetRegistrations.get(protocolDirection);

        if (registeredPackets.containsValue(packetClass)) {
            final String errorMessage = "Duplicate packet registration! Packet " +
                    protocolDirection + ": " + packetClass + " is already registered with id: " +
                    registeredPackets.inverse().get(packetClass);

            throw new IllegalArgumentException(errorMessage);
        }

        registeredPackets.put(registeredPackets.size(), packetClass);
    }

    @Nullable
    public Class<? extends Packet<?>> getPacketClass(final ProtocolDirection protocolDirection, final int packetID) {
        return this.packetRegistrations.get(protocolDirection).get(packetID);
    }

    public int getPacketID(final ProtocolDirection protocolDirection, final Packet<?> packet) {
        return this.packetRegistrations.get(protocolDirection).inverse().getOrDefault(packet.getClass(), Protocol.INVALID_ID);
    }

    public int getStateId() {
        return this.stateId;
    }

    public static Protocol getAssociatedState(final Packet<?> packet) {
        return Protocol.packetMap.get(packet.getClass());
    }
}
