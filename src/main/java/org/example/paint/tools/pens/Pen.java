package org.example.paint.tools.pens;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.example.paint.tools.Tool;

public abstract class Pen implements Tool {

  protected double lastX = -1;
  protected double lastY = -1;
  private double lastPreviewX = -1;
  private double lastPreviewY = -1;
  protected double minSizeFactor = 0.5;
  protected double maxSizeFactor = 4;
  long lastTimestamp = System.nanoTime();
  int recalculateTime = 5000000;
  long timeElapsed = 0;


  //no constructor necessary because every drag data should be updated
  public void onDrag(GraphicsContext g, MouseEvent e, double size, Color color, double opacity) {
    double x = e.getX();
    double y = e.getY();

    //TODO maybe create temporary canvas to write on and then put that on top to merge colors
    g.setGlobalAlpha(opacity);
    g.setFill(new Color(color.getRed(), color.getGreen(), color.getBlue(), 1));

    if (lastX != -1 && lastY != -1) {
      double dx = x - lastX;
      double dy = y - lastY;
      double distance = Math.hypot(dx, dy);
      int steps = (int) distance;

      for (int i = 0; i <= steps; i++) {
        double t = (double) i / steps;
        double interpX = lastX + t * dx;
        double interpY = lastY + t * dy;
        drawAt(g, interpX, interpY, size, color, opacity);
      }
    } else {
      drawAt(g, x, y, size, color, opacity);
    }

    lastX = x;
    lastY = y;
    g.getCanvas().setEffect(null);
  }

  //TODO get rid od the unecessary on release variables
  @Override
  public void onRelease(GraphicsContext g, MouseEvent e, double size) {
    lastX = -1;
    lastY = -1;
  }

  protected abstract void drawAt(GraphicsContext g, double x, double y, double size, Color color, double opacity);

  @Override
  public void drawPreviewAt(GraphicsContext og, MouseEvent e, double size) {
    double x = e.getX();
    double y = e.getY();

    if (lastPreviewX != -1 && lastPreviewY != -1) {
      double clearX = lastPreviewX - size / 2;
      double clearY = lastPreviewY - size / 2;
      double saveSize = size*(maxSizeFactor*1.5);
      og.clearRect(clearX - saveSize/2, clearY - saveSize/2, saveSize, saveSize);
    }

    drawPreviewAt(og, x, y, size);

    lastPreviewX = x;
    lastPreviewY = y;
  };

  protected abstract void drawPreviewAt(GraphicsContext og, double x, double y, double size);

  protected boolean recalculate() {
    long currentTime = System.nanoTime();
    timeElapsed = currentTime - lastTimestamp;
    if (timeElapsed > recalculateTime) {
      lastTimestamp = currentTime;
      return true;
    }
    return false;
  }

}

