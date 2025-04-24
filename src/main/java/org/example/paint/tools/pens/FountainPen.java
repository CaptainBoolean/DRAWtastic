package org.example.paint.tools.pens;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class FountainPen extends Pen {

  private double lastX = -1;
  private double lastY = -1;
  private double avAngle = 0;
  private double width = 0;
  private double height = 0;

  @Override
  protected void drawAt(GraphicsContext g, double x, double y, double size, Color color, double opacity) {
    getNewSize(x, y, size);
    g.fillOval(x-width / 2, y-height / 2, width, height);
  }

  private void getNewSize(double x, double y, double size) {
    double dx = x - lastX;
    double dy = y - lastY;

    if (recalculate()) {
      if (lastX != -1 && lastY != -1) {
        double angle = Math.atan2(dy, dx) * 180/Math.PI;
        avAngle = (avAngle + angle) / 2;
      }
      lastX = x;
      lastY = y;
      width = size * maxSizeFactor/4;
      height = size * minSizeFactor/2;
    }
  }

  @Override
  protected void drawPreviewAt(GraphicsContext og, double x, double y, double size) {
    getNewSize(x, y, size);
    og.setStroke(Color.GRAY);
    og.setLineWidth(1);
    og.strokeOval(x-width / 2, y-height / 2, width, height);
  }
}
