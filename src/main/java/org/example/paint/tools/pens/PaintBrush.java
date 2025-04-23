package org.example.paint.tools.pens;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PaintBrush extends Pen{


  private double lastX = -1;
  private double lastY = -1;
  private long lastTimestamp = 0;

  @Override
  protected void drawAt(GraphicsContext g, double x, double y, double size, Color color, double opacity) {
    final double minSize = size/2;
    final double maxSize = size*4;

    long currentTime = (long) (System.nanoTime() / 1000.0);
    long timeElapsed = currentTime - lastTimestamp;

    if (lastX != -1 && lastY != -1) {
      double dx = x - lastX;
      double dy = y - lastY;
      double distance = Math.hypot(dx, dy);

      double speed = distance / (timeElapsed);
      System.out.println(speed);

      size = Math.max(minSize, Math.min(maxSize, maxSize / speed));
    }

    g.setFill(color);
    g.setGlobalAlpha(opacity);

    g.fillOval(x - size / 2, y - size / 2, size, size);

    lastTimestamp = currentTime;

    lastX = x;
    lastY = y;
  }

  @Override
  protected void drawPreviewAt(GraphicsContext og, double x, double y, double size) {
    og.setStroke(Color.GRAY);
    og.setLineWidth(1);
    og.strokeOval(x - size / 2, y - size / 2, size, size);
  }


}
