package com.my.pdf.convertUI;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author: mayong
 * @createAt: 2020/09/01
 */
public class Home extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(getSceneNode()));
        primaryStage.show();
    }

    private Parent getSceneNode() {
        VBox rootBox = new VBox();
        rootBox.setPadding(new Insets(8));
        // 第一行， 选择文件、或者文件夹
        rootBox.getChildren().add(getChooseFileOrDirectory());
        // 第二行，选择文件
        // 第三行，选择输出的目录
        // 第四行，转换按钮
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


        group.selectedToggleProperty().addListener(
            (ObservableValue<? extends Toggle> ov, Toggle old_Toggle,
             Toggle new_Toggle) -> {
                if (group.getSelectedToggle() != null) {
                    // TODO
                }
            });

        Label titleLabel = new Label("类型:");

        HBox hBox = new HBox();
        hBox.setSpacing(8);
        hBox.getChildren().addAll(titleLabel, fileButton, directoryButton);

        return hBox;
    }
}
