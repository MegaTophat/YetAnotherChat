package me.mega.yetanotherchat.network;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public interface Packet<T extends PacketHandler> {
    void writeData(final PacketDataSerializer packetDataSerializer) throws IOException;

    void handle(final T packetHandler);

    static Packet<?> readData(final PacketDataSerializer packetDataSerializer, final Class<? extends Packet<?>> packetClass) {
        if (packetClass == null) {
            return null;
        }

        final Constructor<? extends Packet<?>> packetConstructor;

        try {
            packetConstructor = packetClass.getConstructor(PacketDataSerializer.class);
        } catch (final NoSuchMethodException ex) {
            throw new AssertionError("Packet: " + packetClass + " missing serializer constructor?", ex);
        }

        try {
            return packetConstructor.newInstance(packetDataSerializer);
        } catch (final InstantiationException | IllegalAccessException | InvocationTargetException ex) {
            return null;
        }
    }
}
