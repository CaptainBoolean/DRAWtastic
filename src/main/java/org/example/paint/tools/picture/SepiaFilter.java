package org.example.paint.tools.picture;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import org.example.paint.tools.Tool;

public class SepiaFilter implements Tool {
    private final Canvas canvas;

    public SepiaFilter(Canvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void onPress(GraphicsContext g, GraphicsContext dg, javafx.scene.input.MouseEvent e, double size, Color color) {
        WritableImage snapshot = canvas.snapshot(null, null);
        PixelReader reader = snapshot.getPixelReader();
        PixelWriter writer = g.getPixelWriter();

        int width = (int)canvas.getWidth();
        int height = (int)canvas.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color original = reader.getColor(x, y);

                double r = original.getRed();
                double gVal = original.getGreen();
                double b = original.getBlue();

                // Sepia transformation
                double newR = Math.min(1.0, 0.393 * r + 0.769 * gVal + 0.189 * b);
                double newG = Math.min(1.0, 0.349 * r + 0.686 * gVal + 0.168 * b);
                double newB = Math.min(1.0, 0.272 * r + 0.534 * gVal + 0.131 * b);

                Color sepia = new Color(newR, newG, newB, original.getOpacity());
                writer.setColor(x, y, sepia);
            }
        }
    }
}
