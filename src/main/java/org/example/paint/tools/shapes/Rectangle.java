package org.example.paint.tools.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Rectangle extends Shape {
    private double startX, startY;
    private boolean drawing = false;
    private Color currColor = Color.BLACK;
    private double currOpacity = 1;

    @Override
    public void onPress(GraphicsContext drawShape, GraphicsContext dg, MouseEvent mouse, double size, Color color, double opacity) {
        startX = mouse.getX();
        startY = mouse.getY();
        drawing = true;
    }

    @Override
    public void onDrag(GraphicsContext preview, GraphicsContext dg, MouseEvent mouse, double size, Color color, double opacity) {
        if (!drawing) {
            return;
        }

        preview.clearRect(0, 0, preview.getCanvas().getWidth(), preview.getCanvas().getHeight());

        currColor = color;
        currOpacity = opacity;

        double endX = mouse.getX();
        double endY = mouse.getY();

        preview.setGlobalAlpha(opacity);
        preview.setStroke(color);
        preview.setLineWidth(size);
        preview.strokeRect(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX), Math.abs(endY - startY));
        preview.setGlobalAlpha(1);
    }

    @Override
    public void onRelease(GraphicsContext drawShape, GraphicsContext dg, MouseEvent mouse, double size, Color color, double opacity) {
        if (drawing) {
            double endX = mouse.getX();
            double endY = mouse.getY();

            drawShape.setGlobalAlpha(currOpacity);
            drawShape.setStroke(currColor);
            drawShape.setLineWidth(size);
            drawShape.strokeRect(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX), Math.abs(endY - startY));
            drawShape.setGlobalAlpha(1);

            drawing = false;
        }
    }

    @Override
    public void drawPreviewAt(GraphicsContext preview, MouseEvent mouse, double size) {
        if (drawing) {
            onDrag(preview, null, mouse, size, currColor, currOpacity);
        }
    }

}