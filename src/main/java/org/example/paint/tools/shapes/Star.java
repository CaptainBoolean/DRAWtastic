package org.example.paint.tools.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Star extends Shape{
    @Override
    public void drawShape(GraphicsContext graphicsContext, double startX, double startY, double endX, double endY, Color color) {
        double centerX = (startX + endX) / 2;
        double centerY = (startY + endY) / 2;

        double dX = endX - startX;
        double dY = endY - startY;
        double outerRadius = Math.sqrt(dX * dX + dY * dY) / 2;
        double innerRadius = outerRadius * 0.5;

        int numPoints = 5;
        double[] xPoints = new double[numPoints * 2];
        double[] yPoints = new double[numPoints * 2];

        for (int i = 0; i < numPoints * 2; i++) {
            double angleDeg = 360.0 / (numPoints * 2) * i - 90;
            double angleRad = Math.toRadians(angleDeg);
            double radius = (i % 2 == 0) ? outerRadius : innerRadius;

            xPoints[i] = centerX + Math.cos(angleRad) * radius;
            yPoints[i] = centerY + Math.sin(angleRad) * radius;
        }

        graphicsContext.strokePolygon(xPoints, yPoints, numPoints * 2);
    }
}