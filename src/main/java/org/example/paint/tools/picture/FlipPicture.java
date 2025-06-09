package org.example.paint.tools.picture;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import org.example.paint.core.FileService;
import org.example.paint.tools.Tool;

public class FlipPicture implements Tool {
    private final String mode;

    public FlipPicture(String mode) {
        this.mode = mode;
    }

    @Override
    public void onPress(GraphicsContext g, GraphicsContext dg, MouseEvent e, double size) {
        if (mode.equals("vertical")) {
            flipVertical(g);
        } else {
            flipHorizontal(g);
        }
    }

    public void flipVertical(GraphicsContext g) {
        Canvas canvas = g.getCanvas();
        Image snapshot = FileService.getTransparentSnapshot(g);

        g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        g.save();
        g.translate(0, canvas.getHeight()); // verschiebe Ursprung ganz nach unten
        g.scale(1, -1);                     // flip
        g.drawImage(snapshot, 0, 0);
        g.restore();
    }

    public void flipHorizontal(GraphicsContext g) {
        Canvas canvas = g.getCanvas();
        Image snapshot = FileService.getTransparentSnapshot(g);

        g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        g.save();
        g.translate(canvas.getWidth(), 0);  // verschiebe Ursprung ganz nach rechts
        g.scale(-1, 1);                     // flip
        g.drawImage(snapshot, 0, 0);
        g.restore();
    }


}
