package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        AnchorPane pane = new AnchorPane();
        BackgroundFill fill = new BackgroundFill(Paint.valueOf("#ffffff"), null, null);
        pane.setBackground(new Background(fill));

        {
            Button button = new Button("button");

            pane.getChildren().add(button);
            AnchorPane.setLeftAnchor(button, 10.0);
            AnchorPane.setTopAnchor(button, 10.0);
        }

        {
            Label label = new Label("label");
            label.setTextFill(Paint.valueOf("#ff0000"));
            label.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                if (mouseEvent.getClickCount() == 2 && mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    System.out.println("左键双击了label");
                }
            });

            pane.getChildren().add(label);
            AnchorPane.setLeftAnchor(label, 10.0);
            AnchorPane.setTopAnchor(label, 50.0);
        }

        {
            TextField textField = new TextField();
            textField.setPromptText("写点什么");
            textField.textProperty().addListener((string) -> {
                System.out.println(string);
            });

            pane.getChildren().add(textField);
            AnchorPane.setTopAnchor(textField, 70.0);
            AnchorPane.setLeftAnchor(textField, 10.0);
        }

        Scene scene = new Scene(pane);

        primaryStage.setScene(scene);
        primaryStage.setTitle("pdf convert");
        primaryStage.setWidth(1000);
        primaryStage.setHeight(1000);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
