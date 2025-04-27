package org.example.paint.tools.pens;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PaintBrush extends Pen{


  private double lastX = -1;
  private double lastY = -1;
  private double avSpeed = 2;
  private double avSize;

  @Override
  protected void drawAt(GraphicsContext g, double x, double y, double size, Color color, double opacity) {
    getNewSize(x, y, size);
    g.fillOval(x - avSize / 2, y - avSize / 2, avSize, avSize);
  }

  private void getNewSize(double x, double y, double size) {
    final double minSize = size*minSizeFactor;
    final double maxSize = size*maxSizeFactor;

    if (recalculate()) {
      if (lastX != -1 && lastY != -1) {
        double dx = x - lastX;
        double dy = y - lastY;
        double distance = Math.hypot(dx, dy);

        avSpeed = Math.min((avSpeed*9 + distance / (timeElapsed/1000000.0)) / 10, 10);
        avSize = Math.max(minSize, Math.min(maxSize, maxSize / avSpeed));

      }
      lastX = x;
      lastY = y;
    }
  }

  @Override
  protected void drawPreviewAt(GraphicsContext og, double x, double y, double size) {
    getNewSize(x, y, size);
    og.setStroke(Color.GRAY);
    og.setLineWidth(1);
    og.strokeOval(x - avSize / 2, y - avSize / 2, avSize, avSize);
  }


}
