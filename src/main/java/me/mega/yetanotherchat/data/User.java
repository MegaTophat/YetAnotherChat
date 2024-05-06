package me.mega.yetanotherchat.data;

public class User {
    private boolean isAdmin;

    public String getName() {
        return name;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    private String name;

    public User(final String name, final boolean isAdmin) {
        this.isAdmin = isAdmin;
        this.name = name;
    }

    public void setAdmin(final boolean admin) {
        isAdmin = admin;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void connectRequest(final Server server) {

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
