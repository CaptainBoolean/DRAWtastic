package org.example.paint.tools.pens;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class RoundEraser extends DrawingTool {


  @Override
  protected void drawAt(GraphicsContext g, GraphicsContext dg, double x, double y, double size, Color color) {
    double radius = size / 2;
    int step = 1; // could be adjusted for performance reasons

    for (double dx = -radius; dx <= radius; dx += step) {
      for (double dy = -radius; dy <= radius; dy += step) {
        if (dx * dx + dy * dy <= radius * radius) {
          g.clearRect(x+dx , y+dy , step, step);
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
