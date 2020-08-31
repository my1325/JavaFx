package com.my.PDFTool;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

/**
 * @author: mayong
 * @createAt: 2020/08/28
 */
public class PDFConvertTool {

    public static PDFFileChooser fileChooser() {
        return new PDFFileChooser(new FileChooser());
    }

    public static PDFDirectoryChooser directoryChooser() {
        return new PDFDirectoryChooser(new DirectoryChooser());
    }
}
