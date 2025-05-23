package org.example.paint.tools.picture;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.example.paint.tools.Tool;

public class InvertFilter implements Tool {
    @Override
    public void onPress(GraphicsContext g, GraphicsContext dg, MouseEvent e, double size, Color color) {
        Canvas canvas = g.getCanvas();
        int width = (int) canvas.getWidth();
        int height = (int) canvas.getHeight();

        WritableImage image = canvas.snapshot(null, null);
        PixelReader reader = image.getPixelReader();

        WritableImage output = new WritableImage(width, height);
        PixelWriter writer = output.getPixelWriter();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color c = reader.getColor(x, y);
                Color inverted = new Color(
                        1.0 - c.getRed(),
                        1.0 - c.getGreen(),
                        1.0 - c.getBlue(),
                        c.getOpacity()
                );
                writer.setColor(x, y, inverted);
            }
        }

        g.clearRect(0, 0, width, height);
        g.drawImage(output, 0, 0);
    }
}
