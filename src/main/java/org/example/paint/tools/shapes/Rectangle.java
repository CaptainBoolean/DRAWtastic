package org.example.paint.tools.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.example.paint.tools.Tool;

public class Rectangle implements Tool {
    private double startX, startY;
    private final Color color;

    public Rectangle() {
        this.color = Color.BLACK;
    }

    @Override
    public void onPress(GraphicsContext drawShape, MouseEvent mouse) {
        startX = mouse.getX();
        startY = mouse.getY();
    }

    @Override
    public void onDrag(GraphicsContext drawShape, MouseEvent mouse, double size, Color color, double opacity) {
    }

    @Override
    public void onRelease(GraphicsContext drawShape, MouseEvent mouse, double size) {
        double endX = mouse.getX();
        double endY = mouse.getY();

        drawShape.setStroke(color);
        drawShape.setLineWidth(size);
        drawShape.strokeRect(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX), Math.abs(endY - startY));
    }

    @Override
    public void drawPreviewAt(GraphicsContext og, MouseEvent e, double size) {
        //TODO implement (for inspiration see e.g. Pen)
    }


}