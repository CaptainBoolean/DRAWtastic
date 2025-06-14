package org.example.paint.tools.pens;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.example.paint.tools.Opaqueable;

public class RoundPen extends Pen implements Opaqueable {

  @Override
  protected void drawAt(GraphicsContext g, GraphicsContext dg, double x, double y, double size) {
    dg.fillOval(x - size / 2, y - size / 2, size, size);
  }

  @Override
  public void drawPreviewAt(GraphicsContext og, double x, double y, double size) {
    og.setStroke(Color.GRAY);
    og.setLineWidth(1);
    og.strokeOval(x - size / 2, y - size / 2, size, size);
  }

}
