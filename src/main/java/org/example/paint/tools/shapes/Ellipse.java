package org.example.paint.tools.shapes;

import javafx.scene.canvas.GraphicsContext;

public class Ellipse extends Shape {

    @Override
    public void drawShape(GraphicsContext graphicsContext, double startX, double startY, double endX, double endY) {
        double leftX = Math.min(startX, endX);
        double leftY = Math.min(startY, endY);
        double width = Math.abs(endX - startX);
        double height = Math.abs(endY - startY);

        graphicsContext.strokeOval(leftX, leftY, width, height);
    }

}