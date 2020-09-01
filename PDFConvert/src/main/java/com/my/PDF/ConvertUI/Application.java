package com.my.pdf.convertUI;

import com.my.pdf.convert.*;
import com.my.pdf.convert.Error;
import com.my.pdf.tool.*;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.util.*;


/**
 * @author: mayong
 * @createAt: 2020/08/21
 */
public class Application extends javafx.application.Application {

    private final Button fileSelectButton = new Button("选择图片（可以多选）");

    private final Button directorySelectButton = new Button("选择文件夹(选择包含图片的文件夹)");

    private final Button startButton = new Button("开始");

    private final Button outputButton = new Button("保存目录");

    private final ArrayList<File> files = new ArrayList();

    private String outputDirectoryPath = null;
    private Stage primaryStage = null;

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.primaryStage = primaryStage;

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));
        vBox.getChildren().addAll(getFileSelectButton(), getDirectorySelectButton(), new HBox() {
            {
                setSpacing(8);
                getChildren().addAll(getStartButton(), getOutputButton());
            }
        });

        Scene scene = new Scene(vBox);
        primaryStage.setScene(scene);
        primaryStage.setWidth(300);
        primaryStage.setHeight(200);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private Button getFileSelectButton() {
        fileSelectButton.setOnAction(event -> {
            Tool
                .fileChooser()
                .initialCurrentDirectory()
                .filterFileNullOrExists()
                .extensionFilter("IMAGE FILES", new String[]{"*.jpg", "*.png", "*.jpeg"})
                .showMultipleOnStage(primaryStage, fileList -> {
                    handleFiles(Arrays.asList(fileList));
                });
        });
        return fileSelectButton;
    }

    private Button getDirectorySelectButton() {
        directorySelectButton.setOnAction(event -> {
            Tool
                .directoryChooser()
                .initialCurrentDirectory()
                .filterDirectoryNullOrExists()
                .showOnStage(this.primaryStage, (directory) -> {
                    File[] files = directory
                        .setFileExtensionsFilter(Arrays.asList("png", "jpg", "jpeg"))
                        .onlyFile()
                        .getFileList();

                    handleFiles(Arrays.asList(files));
                });
        });
        return directorySelectButton;
    }

    public Button getOutputButton() {
        outputButton.setOnAction(event -> {
            Tool
                .directoryChooser()
                .initialCurrentDirectory()
                .showOnStage(primaryStage, directory -> {
                    this.outputDirectoryPath = directory.getDirectory().getPath();
                });
        });
        return outputButton;
    }

    private Button getStartButton() {
        startButton.setOnAction(event -> {
            Convert convert = new Convert(new Path(outputDirectoryPath));
            files.sort((file1, file2) -> (int) (file1.lastModified() - file2.lastModified()));
            Error error = convert.convertImageFilesToPDF(files.toArray(new File[]{}));
            showDialog(error.getDescription());
        });
        return startButton;
    }

    private void handleFiles(List<File> fileList) {
        files.addAll(fileList);
    }

    private void showDialog(String text) {
        Tool
            .alert()
            .setContent(text)
            .lookButtonType(ButtonType.YES, (actionEvent, dialogStage) -> dialogStage.close())
            .show();
    }
}
