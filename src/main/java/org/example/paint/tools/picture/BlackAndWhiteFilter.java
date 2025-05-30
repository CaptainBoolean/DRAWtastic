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

public class BlackAndWhiteFilter implements Tool {

    @Override
    public void onPress(GraphicsContext g, GraphicsContext dg, MouseEvent e, double size) {
        Canvas canvas = g.getCanvas();

        // Snapshot des Canvas in ein Bild
        WritableImage snapshot = canvas.snapshot(new SnapshotParameters(), null);
        PixelReader pr = snapshot.getPixelReader();

        // Neues Bild zum Schreiben
        WritableImage bwImage = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
        PixelWriter pw = bwImage.getPixelWriter();

        for (int x = 0; x < (int)canvas.getWidth(); x++) {
            for (int y = 0; y < (int)canvas.getHeight(); y++) {
                Color co = pr.getColor(x, y);
                double luminance = 0.299 * co.getRed()
                        + 0.587 * co.getGreen()
                        + 0.114 * co.getBlue();
                Color gray = new Color(luminance, luminance, luminance, co.getOpacity());
                pw.setColor(x, y, gray);
            }
        }

        // Ergebnis auf das Canvas zeichnen
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(bwImage, 0, 0);
    }
}