package org.example.paint.tools.pens;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.example.paint.controller.Background;

public class RoundEraser extends Pen {

  @Override
  protected void drawAt(GraphicsContext g, double x, double y, double size, Color color, double opacity) {
    double radius = size / 2;
    double step = 1.0;

    for (double dx = -radius; dx <= radius; dx += step) {
      for (double dy = -radius; dy <= radius; dy += step) {
        if (dx * dx + dy * dy <= radius * radius) {
          // changed this because clearRect deletes background
          g.setFill(Background.getBackgroundColor());
          g.fillOval(x - radius, y - radius, size, size);

          // Use this after implementation!
          // Background.fillRectWithBackground(x, y, size, size);
        }
      }
    }
  }

  @Override
  public void drawPreviewAt(GraphicsContext og, double x, double y, double size) {
    og.setStroke(Color.GRAY);
    og.setLineWidth(1);
    og.strokeOval(x - size / 2, y - size / 2, size, size);
  }
}
