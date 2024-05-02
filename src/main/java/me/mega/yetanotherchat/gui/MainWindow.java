package me.mega.yetanotherchat.gui;

import io.qt.core.QRect;
import io.qt.widgets.QApplication;
import io.qt.widgets.QWidget;

public class MainWindow {
    public MainWindow(final String [] argv) {
        QApplication.initialize(argv);
        final QWidget window = new QWidget();
        window.setGeometry(new QRect(100, 100, 280, 200));
        window.setWindowTitle("YAC");
        window.show();

        QApplication.exec();
        QApplication.shutdown();


    }
}
