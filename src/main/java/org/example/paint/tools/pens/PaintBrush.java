package org.example.paint.tools.pens;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PaintBrush extends Pen{


  private double lastX = -1;
  private double lastY = -1;
  private long lastTimestamp = System.nanoTime();
  private double avSpeed = 2;
  private double avSize;

  @Override
  protected void drawAt(GraphicsContext g, double x, double y, double size, Color color, double opacity) {
    final double minSize = size/2;
    final double maxSize = size*4;

    long currentTime = System.nanoTime();
    long timeElapsed = currentTime - lastTimestamp;



    if (timeElapsed > 500000) {
      if (lastX != -1 && lastY != -1) {
        double dx = x - lastX;
        double dy = y - lastY;
        double distance = Math.hypot(dx, dy);

        avSpeed = Math.min((avSpeed*9 + distance / (timeElapsed/1000000.0)) / 10, 10);
        avSize = Math.max(minSize, Math.min(maxSize, maxSize / avSpeed));

        System.out.println(String.format("TimeElapsed: %.2f Distance: %.2f AvSpeed: %.2f AvSize: %.2f", timeElapsed / 1000000.0, distance, avSpeed, avSize));
      }
      lastX = x;
      lastY = y;

      g.fillOval(x - avSize / 2, y - avSize / 2, avSize, avSize);

    } else {
      g.fillOval(x - avSize / 2, y - avSize / 2, avSize, avSize);
    }
    lastTimestamp = currentTime;
  }

  @Override
  protected void drawPreviewAt(GraphicsContext og, double x, double y, double size) {
    og.setStroke(Color.GRAY);
    og.setLineWidth(1);
    og.strokeOval(x - size / 2, y - size / 2, size, size);
  }


}
