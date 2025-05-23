package org.example.paint.tools.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Triangle extends Shape {
    @Override
    public void drawShape(GraphicsContext graphicsContext, double startX, double startY, double endX, double endY, Color color) {
        double topX = (startX + endX) / 2;
        double topY = startY - Math.abs(endY - startY);

        double[] xPoints = { startX, endX, topX };
        double[] yPoints = { startY, endY, topY };

        graphicsContext.strokePolygon(xPoints, yPoints, 3);
    }

}
