package me.mega.yetanotherchat.data;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final List<Permission> permissions;
    private String name;

    public User() {
        this.permissions = new ArrayList<>();
    }

    public void connectRequest(final Server server) {

    }

    public void sendMessage(final Message message) {

    }

    public void gainAccess(final Channel channel) {

    }

    public void selectUser(final User user) {

    }

    public void selectChannel(final Channel channel) {

    }

    public void blockRequest(final User user) {

    }

    public void confirm() {

    }

    public void writeCreation() {

    }

    public void sendCreation() {

    }

    public void join(final Channel channel) {

    }
}
