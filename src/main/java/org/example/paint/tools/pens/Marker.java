package org.example.paint.tools.pens;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Marker extends Pen {

  @Override
  protected void drawAt(GraphicsContext g, double x, double y, double size, Color color, double opacity) {
    g.fillOval(x - size / 2, y - size / 2, size, size);
  }

  @Override
  public void drawPreviewAt(GraphicsContext og, double x, double y, double size) {
    og.setStroke(Color.GRAY);
    og.setLineWidth(1);
    og.strokeOval(x - size / 2, y - size / 2, size, size);
  }


}
