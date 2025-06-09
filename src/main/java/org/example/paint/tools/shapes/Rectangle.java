package org.example.paint.tools.shapes;

import javafx.scene.canvas.GraphicsContext;

public class Rectangle extends Shape {

    @Override
    public void drawShape(GraphicsContext graphicsContext, double startX, double startY, double endX, double endY) {
        double x = Math.min(startX, endX);
        double y = Math.min(startY, endY);
        double width = Math.abs(endX - startX);
        double height = Math.abs(endY - startY);

        graphicsContext.strokeRect(x, y, width, height);
    }
}