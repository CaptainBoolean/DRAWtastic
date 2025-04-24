package org.example.paint.graphics.picture;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

import java.io.File;

public class PictureEditor {

    public static void loadImage(Canvas canvas) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Bild auswählen");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Bilddateien", "*.png", "*.jpg", "*.jpeg")
        );

        File file = fileChooser.showOpenDialog(canvas.getScene().getWindow());
        if (file != null) {
            Image image = new Image(file.toURI().toString());

            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

            // Bild an Canvas-Größe anpassen
            double widthRatio = canvas.getWidth() / image.getWidth();
            double heightRatio = canvas.getHeight() / image.getHeight();
            double scale = Math.min(widthRatio, heightRatio);

            gc.drawImage(image, 0, 0);
        }
    }

}
