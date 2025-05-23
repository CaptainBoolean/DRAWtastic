package org.example.paint.tools.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.example.paint.tools.Opaqueable;

public class Rectangle extends Shape implements Opaqueable {
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

        dg.strokeRect(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX), Math.abs(endY - startY));
        dg.setGlobalAlpha(1);
    }

    @Override
    public void onRelease(GraphicsContext drawShape, GraphicsContext dg, MouseEvent mouse, double size, Color color) {
        if (drawing) {
            double endX = mouse.getX();
            double endY = mouse.getY();

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
            onDrag(preview, null, mouse, size, currColor);
        }
    }

}