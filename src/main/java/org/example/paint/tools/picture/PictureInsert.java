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
    double startX, startY;

    //TODO implement a preview with dragging (just an outline - see select and move for inspo)
    //TODO resize the picture with downscaling and insert in correct format after drag (maybe take y axis from mouse and set x so it is not distorted)
    //mergetest

    @Override
    public void onPress(GraphicsContext g, MouseEvent e) {
        startX = e.getX();
        startY = e.getY();
    }

    @Override
    public void onDrag(GraphicsContext g, MouseEvent e, double size, Color color, double opacity) {
        // Rechteck zeichnen für Vorschau
        double x = Math.min(startX, e.getX());
        double y = Math.min(startY, e.getY());
        double w = Math.abs(e.getX() - startX);
        double h = Math.abs(e.getY() - startY);

        Canvas canvas = g.getCanvas();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // alternativ: Canvas-Zustand puffern
        gc.setStroke(Color.GRAY);
        gc.setLineDashes(5);
        gc.strokeRect(x, y, w, h);
        gc.setLineDashes(0);
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

            double endX = e.getX();
            double endY = e.getY();

            // Rechteck-Koordinaten berechnen
            double x = Math.min(startX, endX);
            double y = Math.min(startY, endY);
            double h = Math.abs(endY - startY);

            // Seitenverhältnis beibehalten
            double aspectRatio = image.getWidth() / image.getHeight();
            double w = h * aspectRatio;

            // Bild zeichnen
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.drawImage(image, x, y, w, h);
        }
    }


    @Override
    public void drawPreviewAt(GraphicsContext og, MouseEvent e, double size) {

    }
}
