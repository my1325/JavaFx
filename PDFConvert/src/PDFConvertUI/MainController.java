package PDFConvertUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.*;
import java.util.prefs.Preferences;

/**
 * @author: mayong
 * @createAt: 2020/08/21
 */
public class MainController extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private FileChooser fileChooser = new FileChooser();

    private DirectoryChooser directoryChooser = new DirectoryChooser();

    private Button fileSelectButton = new Button("选择图片（可以多选）");

    private Button directorySelectButton = new Button("选择文件夹(选择包含图片的文件夹)");

    private Button startButton = new Button("开始");

    private Button outputButton = new Button("保存目录");

    private String ouputDirectoryPath = null;

    private Stage primaryStage = null;

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.primaryStage = primaryStage;

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));
        vBox.getChildren().addAll(getFileSelectButton(), getDirectorySelectButton(), new HBox(){
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

    private FileChooser getFileChooser() {
        Preferences preferences = Preferences.userNodeForPackage(MainController.class);
        String filePath = preferences.get("lastFilePath", null);
        if (filePath != null && !filePath.isEmpty()) {
            fileChooser.setInitialDirectory(new File(filePath));
        } else {
            fileChooser.setInitialDirectory(new File("."));
        }
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("IMAGE FILES", "*.jpg", "*.png", "*.jpeg")
        );
        return fileChooser;
    }

    private DirectoryChooser getDirectoryChooser() {
        Preferences preferences = Preferences.userNodeForPackage(MainController.class);
        String filePath = preferences.get("lastFilePath", null);
        if (filePath != null && !filePath.isEmpty()) {
            directoryChooser.setInitialDirectory(new File(filePath));
        } else {
            directoryChooser.setInitialDirectory(new File("."));
        }
        return directoryChooser;
    }

    private Button getFileSelectButton() {
        fileSelectButton.setOnAction(event -> {
            List<File> files = fileChooser.showOpenMultipleDialog(this.primaryStage);
            if (files != null && !files.isEmpty()) {
                handleFiles(files);
            }
        });
        return fileSelectButton;
    }

    private Button getDirectorySelectButton() {
        directorySelectButton.setOnAction(event -> {
            File dir = directoryChooser.showDialog(this.primaryStage);
            if (dir == null) {
                return;
            }

            ArrayList<String> acceptExtensions = new ArrayList() {
                {
                    addAll(Arrays.asList(".png", ".jpg", ".jpeg"));
                }
            };

            File[] files = dir.listFiles(file -> {

                String fileName = file.getName();
                int index = fileName.lastIndexOf(".");

                if (index == -1) return false;

                String extension = fileName.substring(index);

                return acceptExtensions.contains(extension) ;
            });
            if (files != null) {
                handleFiles(Arrays.asList(files));
            }
        });
        return directorySelectButton;
    }

    public Button getOutputButton() {
        outputButton.setOnAction(event -> {
            File directory = directoryChooser.showDialog(this.primaryStage);
            if (directory != null) {
                this.ouputDirectoryPath = directory.getPath();
            }
        });
        return outputButton;
    }

    private Button getStartButton() {
        return startButton;
    }

    private void handleFiles(List<File> fileList) {
        System.out.println("fileList = " + fileList);
    }
}
