package org.example.paint.tools.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Rectangle extends Shape {
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
        if (!drawing) {
            return;
        }
        //TODO don't draw preview on main canvas -> call drawPreviewAt and just preview canvas
        dg.clearRect(0, 0, g.getCanvas().getWidth(), g.getCanvas().getHeight());

        currColor = color;

        double endX = mouse.getX();
        double endY = mouse.getY();

        dg.setStroke(color);
        dg.setLineWidth(size);
        dg.strokeRect(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX), Math.abs(endY - startY));
        dg.setGlobalAlpha(1);
    }

    @Override
    public void onRelease(GraphicsContext drawShape, GraphicsContext dg, MouseEvent mouse, double size, Color color) {
        if (drawing) {
            double endX = mouse.getX();
            double endY = mouse.getY();

            dg.setStroke(currColor);
            dg.setLineWidth(size);
            dg.strokeRect(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX), Math.abs(endY - startY));
            dg.setGlobalAlpha(1);

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