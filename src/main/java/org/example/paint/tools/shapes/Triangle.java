package org.example.paint.tools.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.example.paint.tools.Opaqueable;

public class Triangle extends Shape implements Opaqueable {
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

        double topX = (startX + endX) / 2;
        double topY = startY - Math.abs(endY - startY);

        double[] xPoints = { startX, endX, topX };
        double[] yPoints = { startY, endY, topY };

        dg.strokePolygon(xPoints, yPoints, 3);
    }

    @Override
    public void onRelease(GraphicsContext drawShape, GraphicsContext dg, MouseEvent mouse, double size, Color color) {
        if (drawing) {
            double endX = mouse.getX();
            double endY = mouse.getY();

            double topX = (startX + endX) / 2;
            double topY = startY - Math.abs(endY - startY);

            double[] xPoints = { startX, endX, topX };
            double[] yPoints = { startY, endY, topY };

            drawShape.setStroke(currColor);
            drawShape.setLineWidth(size);
            drawShape.strokePolygon(xPoints, yPoints, 3);

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
