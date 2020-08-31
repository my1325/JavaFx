package com.my.pdf.tool;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

/**
 * @author: mayong
 * @createAt: 2020/08/28
 */
public class Tool {

    public static com.my.pdf.tool.FileChooser fileChooser() {
        return new com.my.pdf.tool.FileChooser(new FileChooser());
    }

    public static com.my.pdf.tool.DirectoryChooser directoryChooser() {
        return new com.my.pdf.tool.DirectoryChooser(new DirectoryChooser());
    }
}
