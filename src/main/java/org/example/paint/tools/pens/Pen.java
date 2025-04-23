package org.example.paint.tools.pens;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.example.paint.tools.Tool;

public abstract class Pen implements Tool {

    protected double lastX = -1;
    protected double lastY = -1;
    private double lastPreviewX = -1;
    private double lastPreviewY = -1;

    //no constructor necessary because every drag data should be updated

    public void onDrag(GraphicsContext g, MouseEvent e, double size, Color color, double opacity) {
        double x = e.getX();
        double y = e.getY();

        g.setFill(new Color(color.getRed(), color.getGreen(), color.getBlue(), opacity));

        if (lastX != -1 && lastY != -1) {
            double dx = x - lastX;
            double dy = y - lastY;
            double distance = Math.hypot(dx, dy);
            int steps = (int) distance;

            for (int i = 0; i <= steps; i++) {
                double t = (double) i / steps;
                double interpX = lastX + t * dx;
                double interpY = lastY + t * dy;
                drawAt(g, interpX, interpY, size, color, opacity);
            }
        } else {
            drawAt(g, x, y, size, color, opacity);
        }

        lastX = x;
        lastY = y;
    }

    //TODO get rid od the unecessary on release variables
    @Override
    public void onRelease(GraphicsContext g, MouseEvent e, double size) {
        lastX = -1;
        lastY = -1;
    }

    protected abstract void drawAt(GraphicsContext g, double x, double y, double size, Color color, double opacity);

    @Override
    public void drawPreviewAt(GraphicsContext og, MouseEvent e, double size) {
        double x = e.getX();
        double y = e.getY();

        if (lastPreviewX != -1 && lastPreviewY != -1) {
            double clearX = lastPreviewX - size / 2;
            double clearY = lastPreviewY - size / 2;
            og.clearRect(clearX - 1, clearY - 1, size + 2, size + 2);
        }

        drawPreviewAt(og, x, y, size);

        lastPreviewX = x;
        lastPreviewY = y;
    };

    //TODO reset preview and fully clear plane
    protected abstract void drawPreviewAt(GraphicsContext g, double x, double y, double size);
}

