package org.example.paint.tools.pens;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class SquareEraser extends Pen {

    @Override
    protected void drawAt(GraphicsContext g, double x, double y, double size, Color color, double opacity) {
        //TODO center erasor
        g.clearRect(x, y, size, size);
    }

    @Override
    public void drawPreviewAt(GraphicsContext og, double x, double y, double size) {
        og.setStroke(Color.GRAY);
        og.setLineWidth(1);
        og.strokeRect(x - size / 2, y - size / 2, size, size);
    }
}
