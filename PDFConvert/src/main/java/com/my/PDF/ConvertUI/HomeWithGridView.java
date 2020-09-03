package com.my.pdf.convertUI;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


public class HomeWithGridView extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        GridPane gridPane = new GridPane();
        gridPane.setVgap(8);
        gridPane.setHgap(8);

        gridPane.add(getLabel().init(label -> { label.setText("选择"); }), 0, 0);
        gridPane.add(getLabel().init(label -> { label.setText("选择文件（夹）"); }), 0, 1);
        gridPane.add(getLabel().init(label -> { label.setText("选择文件（夹）"); }), 0, 2);
        gridPane.add(getLabel().init(label -> { label.setText("转换选项"); }), 0, 3);

        final ToggleGroup toggleGroup = getToggleGroup().init(group -> {
            group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
                Toggle toggle = group.getSelectedToggle();
                String userData = (String) toggle.getUserData();
                if (userData != null) {
                    switch (userData) {
                        case "FILE":
                            // TODO
                        case "DIRECTORY":
                            // TODO
                    }
                }
            });
        });
        gridPane.add(getRadioButton(toggleGroup).init(radioButton -> {
            radioButton.setText("文件");
            radioButton.setSelected(true);
            radioButton.setUserData("FILE");
        }), 1, 0);

        gridPane.add(getRadioButton(toggleGroup).init(radioButton -> {
            radioButton.setText("文件夹");
            radioButton.setUserData("DIRECTORY");
        }), 2, 0);

        Scene scene = new Scene(gridPane);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private interface ReturnCall<R> {
        void call(R element);
    }

    private interface Call<R> {
        R init(ReturnCall<R> returnCall);
    }

    private Call<Label> getLabel() {
        return returnCall -> {
            final Label label = new Label();
            returnCall.call(label);
            return label;
        };
    }

    private Call<RadioButton> getRadioButton(ToggleGroup group) {
        return returnCall -> {
            final RadioButton radioButton = new RadioButton();
            radioButton.setToggleGroup(group);
            returnCall.call(radioButton);
            return radioButton;
        };
    }

    private Call<ToggleGroup> getToggleGroup() {
        return returnCall -> {
            final ToggleGroup group = new ToggleGroup();
            returnCall.call(group);
            return group;
        };
    }
}
