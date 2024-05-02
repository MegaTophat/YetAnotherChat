package me.mega.yetanotherchat.data;

import java.util.ArrayList;
import java.util.List;

public class Channel {
    private static final int DEFUALT_USER_LIMIT = 20;
    private final List<User> users;
    private String channelName;
    private int userCount;
    private int userLimit;

    public Channel(final String channelName) {
        this.channelName = channelName;
        this.users = new ArrayList<>();
        this.userCount = 0;
        this.userLimit = DEFUALT_USER_LIMIT;
    }

    public void join(final User user) {
        this.users.add(user);
    }

    public void postMessage(final Message message) {
        //broadcast Packet to all clients
    }

    public void terminate() {

    }
}
