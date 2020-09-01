package com.my.pdf.tool;

import javafx.scene.control.DialogPane;

/**
 * @author: mayong
 * @createAt: 2020/08/28
 */
public class Tool {

    public static FileChooser fileChooser() {
        return new FileChooser(new javafx.stage.FileChooser());
    }

    public static DirectoryChooser directoryChooser() {
        return new DirectoryChooser(new javafx.stage.DirectoryChooser());
    }

    public static Alert alert() {
        return new Alert(new DialogPane());
    }
}
