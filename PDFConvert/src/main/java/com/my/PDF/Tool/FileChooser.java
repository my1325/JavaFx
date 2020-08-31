package com.my.pdf.tool;

import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

/**
 * @author: mayong
 * @createAt: 2020/08/28
 */
public class FileChooser {
    private javafx.stage.FileChooser fileChooser;

    private FileFilter filter;

    public FileChooser(@NotNull javafx.stage.FileChooser fileChooser) {
        this.fileChooser = fileChooser;
    }

    public FileChooser initialDirectory(@NotNull String initialDirectory) {
        fileChooser.setInitialDirectory(new File(initialDirectory));
        return this;
    }

    public FileChooser initialCurrentDirectory() {
        return initialDirectory(".");
    }

    public FileChooser extensionFilter(String description, String[] extensions) {
        fileChooser.getExtensionFilters().add(
            new javafx.stage.FileChooser.ExtensionFilter(description, extensions)
        );
        return this;
    }

    public FileChooser filter(@NotNull FileFilter fileFilter) {
        this.filter = fileFilter;
        return this;
    }

    public FileChooser filterFileNullOrExists() {
        return filter((file) -> file != null && file.exists() && file.isFile());
    }

    public void showSingleOnStage(Stage stage, com.my.PDFTool.CompletionCall<File> call) {
        File file = fileChooser.showOpenDialog(stage);
        if (filter == null || filter.accept(file)) {
            call.call(file);
        }
    }

    public void showMultipleOnStage(Stage stage, com.my.PDFTool.CompletionCall<File[]> call) {
        List<File> retList = fileChooser.showOpenMultipleDialog(stage);
        if (filter != null) {
            call.call((File[]) retList.stream().filter(file -> filter.accept(file)).toArray(File[]::new));
        } else {
            call.call((File[]) retList.toArray());
        }
    }
}
