package com.journalfrontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Stage currentStage;
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        currentStage = stage;
        scene = new Scene(loadFXML("primary-screen"), 1080, 720);
        stage.setScene(scene);

        // Show app to maxmized size
        stage.setMaximized(true);

        stage.setTitle("My Journal");
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    static void showAlert(Alert alert) throws IOException {
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(currentStage);
        alert.initStyle(StageStyle.TRANSPARENT);
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(480, 240);

        alert.showAndWait();
    }

    public static Stage getCurrentStage() {
        return currentStage;
    }

    public static void setTitle(String title) {
        if (!title.isBlank())
            currentStage.setTitle(title);
    }

    public static void main(String[] args) {
        launch();
    }

}