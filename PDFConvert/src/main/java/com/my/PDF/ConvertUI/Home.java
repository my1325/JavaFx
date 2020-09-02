package com.my.pdf.convertUI;

import com.my.pdf.tool.Tool;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import javax.xml.soap.Text;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author: mayong
 * @createAt: 2020/09/01
 */
public class Home extends Application {

    private Stage primaryStage;
    private SelectType selectType = SelectType.FILE;
    private ConvertType convertType = ConvertType.MERGE;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setScene(new Scene(getSceneNode()));
        primaryStage.show();
    }

    private Parent getSceneNode() {
        VBox rootBox = new VBox();
        rootBox.setSpacing(20);
        rootBox.setPadding(new Insets(20));
        rootBox.getChildren().addAll(getChooseFileOrDirectory(), getInputFileSelect(), getOutputFileSelect(), getConvertSelect(), getStartButton());
        return rootBox;
    }

    private Node getChooseFileOrDirectory() {

        final ToggleGroup group = new ToggleGroup();

        RadioButton fileButton = new RadioButton("文件");
        fileButton.setToggleGroup(group);
        fileButton.setSelected(true);

        RadioButton directoryButton = new RadioButton("文件夹");
        directoryButton.setToggleGroup(group);


        fileButton.setUserData("FILE");
        directoryButton.setUserData("DIRECTORY");

        group.selectedToggleProperty().addListener((ov, old_Toggle, new_Toggle) -> {
            Toggle toggle = group.getSelectedToggle();
            if (toggle != null) {
                String userData = (String) toggle.getUserData();
                switch (userData) {
                    case "FILE":
                        this.selectType = SelectType.FILE;
                    case "DIRECTORY":
                        this.selectType = SelectType.DIRECTORY;
                }
            }
        });

        Label titleLabel = new Label("选择:");
        titleLabel.setPrefWidth(80);
        titleLabel.setTextAlignment(TextAlignment.JUSTIFY);

        HBox hBox = new HBox();
        hBox.setSpacing(8);
        hBox.getChildren().addAll(titleLabel, fileButton, directoryButton);

        return hBox;
    }

    private Node getInputFileSelect() {

        final Button inputButton = new Button("选择文件（夹）");
        inputButton.setOnAction(event -> {
            switch (this.selectType) {
                case FILE:
                    Tool
                        .fileChooser()
                        .filterFileNullOrExists()
                        .extensionFilter("IMAGE FILES", new String[]{"*.jpg", "*.png", "*.jpeg"})
                        .showMultipleOnStage(this.primaryStage, files -> {
                            // TODO
                            String[] filePaths = Arrays.stream(files).map(file -> file.getPath()).toArray(String[]::new);
                            Label inputFileLabel = new Label(String.join("\r", filePaths));

                            VBox rootBox = (VBox) this.primaryStage.getScene().getRoot();
                            rootBox.getChildren().add(2, inputFileLabel);
                        });
                    break;
                case DIRECTORY:
                    Tool
                        .directoryChooser()
                        .filterDirectoryNullOrExists()
                        .showOnStage(this.primaryStage, directory -> {
                            // TODO
                        });
                    break;
            }
        });

        final Label titleLabel = new Label("选择文件(夹):");
        titleLabel.setPrefWidth(80);

        final HBox hBox = new HBox();
        hBox.setSpacing(16);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.getChildren().addAll(titleLabel, inputButton);
        return hBox;
    }

    private Node getOutputFileSelect() {

        final TextField outputTextFiled = new TextField();
        outputTextFiled.setPromptText("选择文件(夹)");
        outputTextFiled.textProperty().addListener((observable, oldText, newText) -> {
            /// 拿到了输出的文件夹目录
            System.out.println(newText);
        });

        final Label titleLabel = new Label("选择文件(夹):");
        titleLabel.setPrefWidth(80);

        final HBox hBox = new HBox();
        hBox.setSpacing(16);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.getChildren().addAll(titleLabel, outputTextFiled);
        return hBox;
    }

    private Node getConvertSelect() {

        final ToggleGroup group = new ToggleGroup();

        RadioButton mergeButton = new RadioButton("合成");
        mergeButton.setToggleGroup(group);
        mergeButton.setSelected(true);

        RadioButton singleButton = new RadioButton("单张转换");
        singleButton.setToggleGroup(group);


        mergeButton.setUserData("MERGE");
        singleButton.setUserData("SINGLE");

        group.selectedToggleProperty().addListener(
            (ObservableValue<? extends Toggle> ov, Toggle old_Toggle,
             Toggle new_Toggle) -> {
                if (group.getSelectedToggle() != null) {
                    // TODO
                }
            });

        Label titleLabel = new Label("转换选项:");
        titleLabel.setPrefWidth(80);

        HBox hBox = new HBox();
        hBox.setSpacing(16);
        hBox.getChildren().addAll(titleLabel, mergeButton, singleButton);

        return hBox;
    }

    private Node getStartButton() {
        final Button startButton = new Button("转换");
        startButton.setPrefWidth(100);
        startButton.setPrefHeight(30);
        startButton.setOnAction(event -> {
            // TODO
        });

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(16);
        hBox.getChildren().addAll(startButton);
        return hBox;
    }

    private enum SelectType {
        FILE("FILE"), DIRECTORY("DIRECTORY");

        private String rawValue;

        SelectType(String rawValue) {
            this.rawValue = rawValue;
        }
    }

    private enum ConvertType {
        MERGE("MERGE"), SINGLE("SINGLE");

        private String rawValue;

        ConvertType(String rawValue) {
            this.rawValue = rawValue;
        }
    }
}
