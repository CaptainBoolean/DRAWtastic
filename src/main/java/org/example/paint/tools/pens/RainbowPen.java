package org.example.paint.tools.pens;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.example.paint.tools.Opaqueable;

public class RainbowPen extends Pen implements Opaqueable {

  private double lastX = -1;
  private double lastY = -1;
  private double avAngle = 0;
  private double dirX,  dirY;


  @Override
  protected void drawAt(GraphicsContext g, GraphicsContext dg, double x, double y, double size) {
    getNewAngle(x, y);
    int segments = 50; //adjustable for performance

    for (int i = 0; i < segments; i++) {
      double t = (double) i / (segments - 1) - 0.5;
      double offsetX = Math.cos(avAngle) * t * size;
      double offsetY = Math.sin(avAngle) * t * size;


      double hue = 360.0 * i / segments;
      Color rainbowColor = Color.hsb(hue, 1.0, 1.0, 1);
      dg.setFill(rainbowColor);
      dg.fillOval(x + offsetX, y + offsetY, size / segments+1, size / segments+1);
    }
  }

  private void getNewAngle(double x, double y) {
    boolean noMovement = false;
    if (lastMouseEvent != null) {
      noMovement = checkNoMovement(lastMouseEvent);
    }
    if (recalculate() || noMovement) {
      if (lastX != -1 && lastY != -1) {
        double dx = x - lastX;
        double dy = y - lastY;

        double len = Math.sqrt(dx * dx + dy * dy);
        if (len > 0.0001) {
          dx /= len;
          dy /= len;

          dirX = dirX + dx;
          dirY = dirY + dy;

          double dirLen = Math.sqrt(dirX * dirX + dirY * dirY);
          if (dirLen > 0.0001) {
            dirX /= dirLen;
            dirY /= dirLen;
          }
        }
        avAngle = Math.atan2(dirY, dirX) + Math.PI / 2;
      }
      lastX = x;
      lastY = y;
    }
  }

  @Override
  protected void drawPreviewAt(GraphicsContext og, double x, double y, double size) {
    og.setGlobalAlpha(0.5);
    drawAt(og,og,x,y,size);
    og.setGlobalAlpha(1);
  }
}
