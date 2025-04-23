package org.example.paint.tools.pens;

import javafx.scene.canvas.GraphicsContext;

public class SquareEraser extends Pen {

    public SquareEraser() {
        super(null);
    }

    @Override
    protected void drawAt(GraphicsContext g, double x, double y, double size) {
        g.clearRect(x, y, size, size);
    }
}
