package org.example.paint.tools.shapes;

import javafx.scene.canvas.GraphicsContext;

public class Circle extends Shape {

    @Override
    public void drawShape(GraphicsContext graphicsContext, double startX, double startY, double endX, double endY) {
        double centerX = (startX + endX) / 2;
        double centerY = (startY + endY) / 2;

        double dX = endX - startX;
        double dY = endY - startY;
        double radius = Math.sqrt(dX * dX + dY * dY) / 2;

        double leftX = centerX - radius;
        double leftY = centerY - radius;

        graphicsContext.strokeOval(leftX, leftY, radius*2, radius*2);
    }

}