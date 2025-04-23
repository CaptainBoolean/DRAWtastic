package org.example.paint.tools.pens;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class RoundPen extends Pen{

  @Override
  protected void drawAt(GraphicsContext g, double x, double y, double size, Color color, double opacity) {
    g.fillOval(x - size / 2, y - size / 2, size, size);
  }

  @Override
  public void drawPreview() {
    double x =

    double size = getBrushSize();
    overlayGC.clearRect(0, 0, overlayCanvas.getWidth(), overlayCanvas.getHeight());

    overlayGC.setStroke(Color.GRAY);
    overlayGC.setLineWidth(1);
    overlayGC.strokeOval(x - size / 2, y - size / 2, size, size);
  }

}
