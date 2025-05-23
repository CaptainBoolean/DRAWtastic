package org.example.paint.tools.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.example.paint.tools.Opaqueable;

public class Circle extends Shape implements Opaqueable {
    private double startX, startY;
    private boolean drawing = false;
    private Color currColor = Color.BLACK;

    @Override
    public void onPress(GraphicsContext drawShape, GraphicsContext dg, MouseEvent mouse, double size, Color color) {
        startX = mouse.getX();
        startY = mouse.getY();
        drawing = true;
    }

    @Override
    public void onDrag(GraphicsContext g, GraphicsContext dg, MouseEvent mouse, double size, Color color) {
        if (!drawing || dg == null) {
            return;
        }
        dg.clearRect(0, 0, dg.getCanvas().getWidth(), dg.getCanvas().getHeight());

        currColor = color;
        dg.setStroke(color);
        dg.setLineWidth(size);

        double endX = mouse.getX();
        double endY = mouse.getY();

        double centerX = (startX + endX) / 2;
        double centerY = (startY + endY) / 2;

        double dx = endX - startX;
        double dy = endY - startY;
        double radius = Math.sqrt(dx * dx + dy * dy) / 2;

        double leftX = centerX - radius;
        double leftY = centerY - radius;

        dg.strokeOval(leftX, leftY, radius*2, radius*2);
        dg.setGlobalAlpha(1);
    }

    @Override
    public void onRelease(GraphicsContext drawShape, GraphicsContext dg, MouseEvent mouse, double size, Color color) {
        if (drawing) {
            double endX = mouse.getX();
            double endY = mouse.getY();

            double centerX = (startX + endX) / 2;
            double centerY = (startY + endY) / 2;

            double dx = endX - startX;
            double dy = endY - startY;
            double radius = Math.sqrt(dx * dx + dy * dy) / 2;

            double leftX = centerX - radius;
            double leftY = centerY - radius;

            drawShape.setStroke(currColor);
            drawShape.setLineWidth(size);
            drawShape.strokeOval(leftX, leftY, radius*2, radius*2);
            drawShape.setGlobalAlpha(1);

            drawing = false;
        }
    }

    @Override
    public void drawPreviewAt(GraphicsContext preview, MouseEvent mouse, double size) {
        if (drawing) {
            onDrag(preview, null, mouse, size, currColor);
        }
    }

}