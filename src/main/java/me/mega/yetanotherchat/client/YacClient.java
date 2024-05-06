package me.mega.yetanotherchat.client;

import me.mega.yetanotherchat.data.Channel;

import java.net.InetAddress;

public final class YacClient {
    private final InetAddress host;
    private final int port;
    private boolean running;
    private Channel currentChannel;

    public Channel getCurrentChannel() {
        return currentChannel;
    }

    public void setCurrentChannel(final Channel currentChannel) {
        this.currentChannel = currentChannel;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    private String name;

    public static void main(final String[] args) throws java.net.UnknownHostException {
        new YacClient(InetAddress.getLoopbackAddress(), 6000).run();
    }

    public YacClient(final InetAddress host, final int port) {
        this.host = host;
        this.port = port;
        this.running = true;
        new ClientConnection(this, host, port);
    }

    public void run() {
        while (this.running) {
            try {
                Thread.sleep(10);
            } catch (final InterruptedException ignored) {

            }
        }
    }
}
