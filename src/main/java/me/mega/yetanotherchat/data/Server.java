package me.mega.yetanotherchat.data;

import java.util.ArrayList;
import java.util.List;

public class Server {
    private final List<User> connectedClients;
    private final List<Message> messagePostQueue;
    private final List<Channel> channels;
    private final int port;

    public Server(final int port) {
        this.port = port;
        this.connectedClients = new ArrayList<>();
        this.messagePostQueue = new ArrayList<>();
        this.channels = new ArrayList<>();
    }

    public void connectUser(final User user) {
        this.connectedClients.add(user);
    }

    public void receiveMessage(final Message message) {
        this.messagePostQueue.add(message);
    }

    public void postMessage(final Message message) {
        //broadcast to connected clients
    }

    public void verify(final User user) {

    }

    public void block(final User user) {

    }

    public void createChannel(final String channelName) {
        final Channel channel = new Channel(channelName);

        this.channels.add(channel);
    }

    public void postChannel(final Channel channel) {
        //broadcast channel's existence to connected clients
    }
}
