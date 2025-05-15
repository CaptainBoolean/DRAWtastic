package org.example.paint.tools.pens;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.example.paint.tools.Tool;

/**
This is the abstract form of a Pen providing all necessary common fields, managing the position of drawing, preview and recalculation time to preserve performance.
 */
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


  /**
   * Draws the sepcific kind of shape when dragging over the canvas.
   * Between the registered points interpolation happens to allow pens to draw one continuos line.
   *
   * @param g     The GraphicsContect to draw on.
   * @param dg
   * @param e     The MouseEvent necessary to grab the location of drawing.
   * @param size  The size that the pen should use for it's shape.
   * @param color The color that the pen should draw in if it has changable colors.
   */
  public void onDrag(GraphicsContext g, GraphicsContext dg, MouseEvent e, double size, Color color) {
    if (dg.getCanvas().getWidth() == 0 || dg.getCanvas().getHeight() == 0) {
      dg.getCanvas().setHeight(g.getCanvas().getHeight());
      dg.getCanvas().setWidth(g.getCanvas().getWidth());
    }
    double x = e.getX();
    double y = e.getY();

    dg.setFill(color);
    g.setGlobalAlpha(1);
    //TODO maybe make it so the line on the temp canvas gets deleted when pen is held at the end and straight line is drawn

    if (lastX != -1 && lastY != -1) {
      double dx = x - lastX;
      double dy = y - lastY;
      double distance = Math.hypot(dx, dy);
      int steps = (int) distance;

      for (int i = 0; i <= steps; i++) {
        double t = (double) i / steps;
        double interpX = lastX + t * dx;
        double interpY = lastY + t * dy;
        drawAt(g, dg, interpX, interpY, size, color);
      }
    } else {
      drawAt(g, dg, x, y, size, color);
    }

    lastX = x;
    lastY = y;
    g.getCanvas().setEffect(null);
  }

  /**
   * Resets the parameters necessary for interpolation.
   *
   * @param g     -
   * @param dg
   * @param e     -
   * @param size  -
   * @param color
   */
  @Override
  public void onRelease(GraphicsContext g, GraphicsContext dg, MouseEvent e, double size, Color color) {
    lastX = -1;
    lastY = -1;


  }

  /**
   * Draws at the provided coordinated with the specified characteristics
   *
   * @param g     GraphicsContext to draw on.
   * @param dg
   * @param x     The x coordinate to draw on.
   * @param y     The y coordinate to draw on.
   * @param size  The size to draw the shape in.
   * @param color The color to draw the shape in.
   */
  protected abstract void drawAt(GraphicsContext g, GraphicsContext dg, double x, double y, double size, Color color);

  /**
   * Draws a preview at the correct position and removes the last drawn one.
   * @param og GraphicsContext to draw the preview on.
   * @param e The MouseEvent necessary to grab the location of drawing.
   * @param size The size of the drawn preview.
   */
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

  /**
   * Draws a preview at the provided coordinates.
   * @param og GraphicsContext to draw on.
   * @param x The x coordinate to draw on.
   * @param y The y coordinate to draw on.
   * @param size The size of the Preview to be drawn.
   */
  protected abstract void drawPreviewAt(GraphicsContext og, double x, double y, double size);

  /**
   * Sets a specific timeframe to recalculate the size of speed dependant pens.
   * @return True if it is necessary to calculate the size again.
   */
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

