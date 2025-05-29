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
    static Image savedImage;
    static double imgWidth, imgHeight, imgX, imgY;
    boolean dragging;

    /**
     *
     * @param g
     * @param e
     */

    @Override
    public void onPress(GraphicsContext g, GraphicsContext dg, MouseEvent e, double size, Color color) {
        startX = e.getX();
        startY = e.getY();
        dragging = true;
    }

    @Override
    public void onRelease(GraphicsContext g, GraphicsContext dg, MouseEvent e, double size, Color color) {
        Canvas canvas = g.getCanvas();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Bild auswählen");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Bilddateien", "*.png", "*.jpg", "*.jpeg", "*.gif")
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
            savedImage = image;
            imgX = x;
            imgY = y;
            imgWidth = w;
            imgHeight = h;
        }
        dragging = false;
    }


    @Override
    public void drawPreviewAt(GraphicsContext og, MouseEvent e, double size) {
      if (dragging) {
        // Rechteck zeichnen für Vorschau
        double x = Math.min(startX, e.getX());
        double y = Math.min(startY, e.getY());
        double w = Math.abs(e.getX() - startX);
        double h = Math.abs(e.getY() - startY);


        og.clearRect(0, 0, og.getCanvas().getWidth(), og.getCanvas().getHeight()); // alternativ: Canvas-Zustand puffern
        og.setStroke(Color.GRAY);
        og.setLineDashes(5);
        og.strokeRect(x, y, w, h);
        og.setLineDashes(0);
      }
    }
}