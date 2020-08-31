package com.my.pdf.tool;

import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileFilter;


/**
 * @author: mayong
 * @createAt: 2020/08/28
 */
public class DirectoryChooser {
    private javafx.stage.DirectoryChooser directoryChooser;

    private FileFilter filter;

    private String[] fileExtensions;

    public DirectoryChooser(@NotNull javafx.stage.DirectoryChooser fileChooser) {
        this.directoryChooser = fileChooser;
    }

    public DirectoryChooser initialDirectory(@NotNull String initialDirectory) {
        directoryChooser.setInitialDirectory(new File(initialDirectory));
        return this;
    }

    public DirectoryChooser initialCurrentDirectory() {
        return initialDirectory(".");
    }

    public DirectoryChooser filter(@NotNull FileFilter fileFilter) {
        this.filter = fileFilter;
        return this;
    }

    public DirectoryChooser filterDirectoryNullOrExists() {
        return filter((file) -> file != null && file.exists() && file.isDirectory());
    }

    public void showOnStage(Stage stage, com.my.PDFTool.CompletionCall<Directory> call) {
        File file = directoryChooser.showDialog(stage);
        if (filter == null || filter.accept(file)) {
            call.call(new Directory(file));
        }
    }
}
