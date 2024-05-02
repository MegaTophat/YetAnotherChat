package me.mega.yetanotherchat.data;

public record Message(Channel associatedChannel, String text, User author, long timestamp) {
}
