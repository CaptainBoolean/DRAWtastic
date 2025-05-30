package org.example.paint.tools.pens;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.example.paint.tools.Opaqueable;

public class FountainPen extends Pen implements Opaqueable {
  private double width = 0;
  private double height = 0;

  @Override
  protected void drawAt(GraphicsContext g, GraphicsContext dg, double x, double y, double size) {
    getNewSize(size);
    dg.fillOval(x-width / 2, y-height / 2, width, height);
  }

  private void getNewSize(double size) {
    width = size * maxSizeFactor/4;
    height = size * minSizeFactor/2;
  }

  @Override
  protected void drawPreviewAt(GraphicsContext og, double x, double y, double size) {
    getNewSize(size);
    og.setStroke(Color.GRAY);
    og.setLineWidth(1);
    og.strokeOval(x-width / 2, y-height / 2, width, height);
  }
}
