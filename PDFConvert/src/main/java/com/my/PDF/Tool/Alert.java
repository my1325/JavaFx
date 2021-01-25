package com.my.pdf.tool;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author: mayong
 * @createAt: 2020/09/01
 */
public class Alert {

    private DialogPane pane;
    private Stage dialogStage = new Stage();

    public Alert(DialogPane pane) {
        this.pane = pane;
    }

    public DialogPane getPane() {
        return pane;
    }

    public interface AlertContentCall {
        public Node content();
    }
    
    public Alert setContent(AlertContentCall contentCall) {
        pane.setContent(contentCall.content());
        return this;
    }

    public Alert setContent(String content) {
        return setContent(() -> new Label(content));
    }

    public interface ButtonCall {
        public void callAction(ActionEvent actionEvent, Stage stage);
    }

    public Alert lookButtonType(ButtonType buttonType, ButtonCall value) {
        pane.getButtonTypes().add(buttonType);
        Button button = (Button) pane.lookupButton(buttonType);
        button.setOnAction((event) -> {
            value.callAction(event, dialogStage);
        });
        return this;
    }

    public void show(Modality modality) {
        dialogStage.initModality(modality);
        dialogStage.setScene(new Scene(pane));
        dialogStage.show();
    }

    public void show() {
        show(Modality.APPLICATION_MODAL);
    }
}
