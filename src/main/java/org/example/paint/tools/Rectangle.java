package org.example.paint.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Rectangle implements Tool {
    private double startX, startY;
    private final Color color;

    public Rectangle(Color color) {
        this.color = color;
    }

    @Override
    public void onPress(GraphicsContext drawShape, MouseEvent mouse) {
        startX = mouse.getX();
        startY = mouse.getY();
    }

    @Override
    public void onDrag(GraphicsContext drawShape, MouseEvent mouse, double size) {
    }

    @Override
    public void onRelease(GraphicsContext drawShape, MouseEvent mouse, double size) {
        double endX = mouse.getX();
        double endY = mouse.getY();

        drawShape.setStroke(color);
        drawShape.setLineWidth(size);
        drawShape.strokeRect(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX), Math.abs(endY - startY));
    }
}