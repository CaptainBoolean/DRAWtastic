package org.example.paint.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Pencil implements Tool {

    private Color color;

    public Pencil(Color color) {
        this.color = color;
    }

    @Override
    public void onDrag(GraphicsContext g, MouseEvent e, double size) {
        g.setFill(color);
        g.fillRect(e.getX(), e.getY(), size, size);
    }
}
