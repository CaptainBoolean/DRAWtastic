package org.example.paint.tools.shapes;

import javafx.scene.canvas.GraphicsContext;

public class Triangle extends Shape {
    @Override
    public void drawShape(GraphicsContext graphicsContext, double startX, double startY, double endX, double endY) {
        double centerX = (startX + endX) / 2;
        double centerY = (startY + endY) / 2;

        double dx = endX - startX;
        double dy = endY - startY;
        double radius = Math.sqrt(dx * dx + dy * dy) / 2;
        double height = Math.sqrt(3) * radius;

        double[] xPoints = new double[3];
        double[] yPoints = new double[3];

        xPoints[0] = centerX;
        yPoints[0] = centerY - (2.0 / 3.0) * height;

        xPoints[1] = centerX - radius;
        yPoints[1] = centerY + (1.0 / 3.0) * height;

        xPoints[2] = centerX + radius;
        yPoints[2] = centerY + (1.0 / 3.0) * height;

        graphicsContext.strokePolygon(xPoints, yPoints, 3);
    }

}