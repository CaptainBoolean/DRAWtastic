package org.example.paint;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("drawtastic.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 720);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("Style.css")).toExternalForm());
        stage.setScene(scene);
        stage.setTitle("DRAWtastic");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
