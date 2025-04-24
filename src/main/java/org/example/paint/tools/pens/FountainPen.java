package org.example.paint.tools.pens;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class FountainPen extends Pen {

  private double avAngle = 0;

  @Override
  protected void drawAt(GraphicsContext g, double x, double y, double size, Color color, double opacity) {
    //System.out.println("x: " + lastX + " y: " + lastY);
    //System.out.println("lx: " + x + " ly: " + y);
    getNewAngle(x, y);
    double width = size;
    double height = size / 3;

    g.fillOval(-width / 2, -height / 2, width, height);

  }

  private void getNewAngle(double x, double y) {

    if (recalculate()) {
      double dx = x - lastX;
      double dy = y - lastY;
      if (lastX != -1 && lastY != -1) {

        double angle = Math.atan2(dy, dx) * 180/Math.PI;
        avAngle = (avAngle + angle) / 2;

        System.out.printf("dx: %.2f, dy: %.2f, angle: %.2f%n", dx, dy, angle);

      }
      lastX = x;
      lastY = y;

    }
  }

  @Override
  protected void drawPreviewAt(GraphicsContext g, double x, double y, double size) {

  }
}
