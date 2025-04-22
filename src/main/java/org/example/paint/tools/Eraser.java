package org.example.paint.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

public class Eraser implements Tool{

    @Override
    public void onDrag(GraphicsContext g, MouseEvent e, double size) {
        g.clearRect(e.getX(), e.getY(), size, size);
    }
}
