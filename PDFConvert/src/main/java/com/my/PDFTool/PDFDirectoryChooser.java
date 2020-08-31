package com.my.PDFTool;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

/**
 * @author: mayong
 * @createAt: 2020/08/28
 */
public class PDFDirectoryChooser {
    private DirectoryChooser directoryChooser;

    private FileFilter filter;

    private String[] fileExtensions;

    public PDFDirectoryChooser(@NotNull DirectoryChooser fileChooser) {
        this.directoryChooser = fileChooser;
    }

    public PDFDirectoryChooser initialDirectory(@NotNull String initialDirectory) {
        directoryChooser.setInitialDirectory(new File(initialDirectory));
        return this;
    }

    public PDFDirectoryChooser initialCurrentDirectory() {
        return initialDirectory(".");
    }

    public PDFDirectoryChooser filter(@NotNull FileFilter fileFilter) {
        this.filter = fileFilter;
        return this;
    }

    public PDFDirectoryChooser filterDirectoryNullOrExists() {
        return filter((file) -> file != null && file.exists() && file.isDirectory());
    }

    public void showOnStage(Stage stage, CompletionCall<PDFDirectory> call) {
        File file = directoryChooser.showDialog(stage);
        if (filter == null || filter.accept(file)) {
            call.call(new PDFDirectory(file));
        }
    }
}
