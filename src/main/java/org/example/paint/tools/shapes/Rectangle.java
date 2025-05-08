package org.example.paint.tools.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Rectangle extends Shape {
    private double startX, startY;
    private double lastPreviewX = -1;
    private double lastPreviewY = -1;
    protected double maxSizeFactor = 4;
    private boolean drawing = false;
    private Color currColor = Color.BLACK;
    private double currOpacity = 1;

    @Override
    public void onPress(GraphicsContext drawShape, MouseEvent mouse) {
        startX = mouse.getX();
        startY = mouse.getY();
        drawing = true;
    }

    @Override
    public void onDrag(GraphicsContext drawShape, MouseEvent mouse, double size, Color color, double opacity) {
        if (!drawing) {
            return;
        }
        currColor = color;
        currOpacity = opacity;

        if (lastPreviewX != -1 && lastPreviewY != -1) {
            double saveSize = size * maxSizeFactor;
            double clearX = Math.min(startX, lastPreviewX);
            double clearY = Math.min(startY, lastPreviewY);
            double clearWidth = Math.abs(lastPreviewX - startX);
            double clearHeight = Math.abs(lastPreviewY - startY);
            drawShape.clearRect(clearX - saveSize / 2, clearY - saveSize / 2, clearWidth + saveSize, clearHeight + saveSize);
        }

        double endX = mouse.getX();
        double endY = mouse.getY();
        drawShape.setGlobalAlpha(opacity);
        drawShape.setStroke(color);
        drawShape.setLineWidth(size);
        drawShape.strokeRect(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX), Math.abs(endY - startY));
        drawShape.setGlobalAlpha(1.0);

        lastPreviewX = endX;
        lastPreviewY = endY;
    }

    @Override
    public void onRelease(GraphicsContext drawShape, MouseEvent mouse, double size) {
        if (drawing) {
            double endX = mouse.getX();
            double endY = mouse.getY();

            drawShape.setGlobalAlpha(currOpacity);
            drawShape.setStroke(currColor);
            drawShape.setLineWidth(size);
            drawShape.strokeRect(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX), Math.abs(endY - startY));
            drawShape.setGlobalAlpha(1);

            lastPreviewX = -1;
            lastPreviewY = -1;
            drawing = false;
        }
    }

    @Override
    public void drawPreviewAt(GraphicsContext drawShape, MouseEvent mouse, double size) {
        if (drawing) {
            onDrag(drawShape, mouse, size, currColor, currOpacity);
        }
    }

}