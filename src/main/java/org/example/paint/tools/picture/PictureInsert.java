package org.example.paint.tools.picture;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import org.example.paint.tools.Tool;

import java.io.File;

public class PictureInsert implements Tool {

    //TODO implement a preview with dragging (just an outline - see select and move for inspo)
    //TODO resize the picture with downscaling and insert in correct format after drag (maybe take y axis from mouse and set x so it is not distorted)

    @Override
    public void onDrag(GraphicsContext g, MouseEvent e, double size, Color color, double opacity) {

    }

    @Override
    public void onRelease(GraphicsContext g, MouseEvent e, double size) {
        Canvas canvas = g.getCanvas();
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

            double drawWidth = image.getWidth() * scale;
            double drawHeight = image.getHeight() * scale;

            gc.drawImage(image, 0, 0);
        }
    }

    @Override
    public void drawPreviewAt(GraphicsContext og, MouseEvent e, double size) {

    }
}
