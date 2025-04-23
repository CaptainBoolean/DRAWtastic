package org.example.paint.tools.pens;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Marker extends Pen {

  @Override
  protected void drawAt(GraphicsContext g, double x, double y, double size, Color color, double opacity) {
    g.setFill(new Color(color.getRed(), color.getGreen(), color.getBlue(), opacity));
    g.fillOval(x - size / 2, y - size / 2, size, size);
  }


}
