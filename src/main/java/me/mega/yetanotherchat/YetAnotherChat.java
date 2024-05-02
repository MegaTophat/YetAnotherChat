package me.mega.yetanotherchat;

import me.mega.yetanotherchat.gui.MainWindow;

public final class YetAnotherChat {
    public static void main(final String[] args) {
        System.out.println("Hello");

        final MainWindow mainWindow = new MainWindow(args);
        System.out.println("Bye!");
    }
}
