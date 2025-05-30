package org.example.paint.tools.picture;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.example.paint.tools.Tool;

public class BlurFilter implements Tool {

    @Override
    public void onPress(GraphicsContext gr, GraphicsContext dg, MouseEvent e, double size) {
        Canvas canvas = gr.getCanvas();
        // Snapshot des Canvas
        WritableImage snapshot = canvas.snapshot(new SnapshotParameters(), null);
        PixelReader pr = snapshot.getPixelReader();

        // Neues Bild zum Schreiben
        WritableImage blurredImage = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
        PixelWriter pw = blurredImage.getPixelWriter();

        for (int y = 1; y < (int)canvas.getHeight() - 1; y++) {
            for (int x = 1; x < (int)canvas.getWidth() - 1; x++) {
                double r = 0, g = 0, b = 0, a = 0;

                // 3x3 Nachbarschaft durchgehen
                for (int dy = -1; dy <= 1; dy++) {
                    for (int dx = -1; dx <= 1; dx++) {
                        Color c = pr.getColor(x + dx, y + dy);
                        r += c.getRed();
                        g += c.getGreen();
                        b += c.getBlue();
                        a += c.getOpacity();
                    }
                }

                // Mittelwert der 9 Pixel berechnen
                Color avg = new Color(r / 9, g / 9, b / 9, a / 9);
                pw.setColor(x, y, avg);
            }
        }

        // Bild auf das Canvas zeichnen
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(blurredImage, 0, 0);
    }
}