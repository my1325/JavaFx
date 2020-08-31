package com.my.pdf.convertUI;

import com.my.pdf.convert.*;
import com.my.pdf.tool.*;
import com.my.pdf.convert.Error;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.*;


/**
 * @author: mayong
 * @createAt: 2020/08/21
 */
public class Application extends javafx.application.Application {

    private final FileChooser fileChooser = new FileChooser();

    private final DirectoryChooser directoryChooser = new DirectoryChooser();

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
            File directory = directoryChooser.showDialog(this.primaryStage);
            if (directory != null) {
                this.outputDirectoryPath = directory.getPath();
            }
        });
        return outputButton;
    }

    private Button getStartButton() {
        startButton.setOnAction(event -> {
            Convert convert = new Convert(new Path(outputDirectoryPath));
            Error error = convert.convertImageFilesToPDF(files.toArray(new File[]{}));
            if (error.equalTo(com.my.pdf.convert.Error.noError)) {
                showDialog("转换成功");
            } else {
                showDialog(error.getDescription());
            }
        });
        return startButton;
    }

    private void handleFiles(List<File> fileList) {
        System.out.println("fileList = " + fileList);
        files.addAll(fileList);
    }

    private void showDialog(String text) {
        DialogPane dialogPane = new DialogPane();
        dialogPane.setContent(new Label(text));
        dialogPane.getButtonTypes().add(ButtonType.YES);

        Stage dialogStage = new Stage();
        dialogStage.setScene(new Scene(dialogPane));
        dialogStage.show();

        Button button = (Button) dialogPane.lookupButton(ButtonType.YES);
        button.setOnAction(event1 -> {
            dialogStage.close();
        });
    }
}
