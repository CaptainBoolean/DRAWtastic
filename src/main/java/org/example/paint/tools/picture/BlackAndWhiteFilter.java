package org.example.paint.tools.picture;

import org.example.paint.tools.Tool;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.canvas.*;
import javafx.scene.SnapshotParameters;

public class BlackAndWhiteFilter implements Tool {
    private final Canvas canvas;

    public BlackAndWhiteFilter(Canvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void onPress(GraphicsContext g, MouseEvent e) {

        // Snapshot des Canvas in ein Bild
        WritableImage snapshot = canvas.snapshot(new SnapshotParameters(), null);
        PixelReader pr = snapshot.getPixelReader();

        // Neues Bild zum Schreiben
        WritableImage bwImage = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
        PixelWriter pw = bwImage.getPixelWriter();

        for (int x = 0; x < (int)canvas.getWidth(); x++) {
            for (int y = 0; y < (int)canvas.getHeight(); y++) {
                Color color = pr.getColor(x, y);
                double luminance = 0.299 * color.getRed()
                        + 0.587 * color.getGreen()
                        + 0.114 * color.getBlue();
                Color gray = new Color(luminance, luminance, luminance, color.getOpacity());
                pw.setColor(x, y, gray);
            }
        }

        // Ergebnis auf das Canvas zeichnen
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(bwImage, 0, 0);
    }
}
