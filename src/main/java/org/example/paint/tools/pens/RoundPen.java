package org.example.paint.tools.pens;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class RoundPen extends Pen{

  @Override
  protected void drawAt(GraphicsContext g, double x, double y, double size, Color color, double opacity) {
    g.fillOval(x - size / 2, y - size / 2, size, size);
  }

  @Override
  public void drawPreview(GraphicsContext og, MouseEvent e, double size) {
    double x = e.getX();
    double y = e.getY();

    og.clearRect(0, 0, og.getCanvas().getWidth(), og.getCanvas().getHeight());

    og.setStroke(Color.GRAY);
    og.setLineWidth(1);
    og.strokeOval(x - size / 2, y - size / 2, size, size);
  }

}
