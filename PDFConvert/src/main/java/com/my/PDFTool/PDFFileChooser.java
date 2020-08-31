package com.my.PDFTool;

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
public class PDFFileChooser {
    private FileChooser fileChooser;

    private FileFilter filter;

    public PDFFileChooser(@NotNull FileChooser fileChooser) {
        this.fileChooser = fileChooser;
    }

    public PDFFileChooser initialDirectory(@NotNull String initialDirectory) {
        fileChooser.setInitialDirectory(new File(initialDirectory));
        return this;
    }

    public PDFFileChooser initialCurrentDirectory() {
        return initialDirectory(".");
    }

    public PDFFileChooser extensionFilter(String description, String[] extensions) {
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter(description, extensions)
        );
        return this;
    }

    public PDFFileChooser filter(@NotNull FileFilter fileFilter) {
        this.filter = fileFilter;
        return this;
    }

    public PDFFileChooser filterFileNullOrExists() {
        return filter((file) -> file != null && file.exists() && file.isFile());
    }

    public void showSingleOnStage(Stage stage, CompletionCall<File> call) {
        File file = fileChooser.showOpenDialog(stage);
        if (filter == null || filter.accept(file)) {
            call.call(file);
        }
    }

    public void showMultipleOnStage(Stage stage, CompletionCall<File[]> call) {
        List<File> retList = fileChooser.showOpenMultipleDialog(stage);
        if (filter != null) {
            call.call((File[]) retList.stream().filter(file -> filter.accept(file)).toArray());
        } else {
            call.call((File[]) retList.toArray());
        }
    }
}
