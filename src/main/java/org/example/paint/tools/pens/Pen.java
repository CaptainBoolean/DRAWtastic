package org.example.paint.tools.pens;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.example.paint.tools.Tool;

/**
This is the abstract form of a Pen providing all necessary common fields, managing the position of drawing, preview and recalculation time to preserve performance.
 */
public abstract class Pen implements Tool {

  private double startX = -1;
  private double startY = -1;
  protected double lastX = -1;
  protected double lastY = -1;
  private double lastPreviewX = -1;
  private double lastPreviewY = -1;
  protected double minSizeFactor = 0.5;
  protected double maxSizeFactor = 4;
  long lastTimestamp = System.nanoTime();
  int recalculateTime = 5000000;
  long timeElapsed = 0;

  private final long holdThreshold = 200000000;
  private long lastNoMovementTime = System.nanoTime();
  private final double movementMargin = 5;
  private double noMovementX = -1;
  private double noMovementY = -1;


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

    //TODO maybe make it so the line on the temp canvas gets deleted when pen is held at the end and straight line is drawn

    if (lastX != -1 && lastY != -1) {
      drawLine(g, dg, e, lastX, lastY, size, color);
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
    if (checkNoMovement(e)) {
      dg.clearRect(0, 0, dg.getCanvas().getWidth(), dg.getCanvas().getHeight());
      drawLine(g, dg, e, startX, startY, size, color);
    }
    lastX = -1;
    lastY = -1;
    startX = -1;
    startY = -1;


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
    if (checkNoMovement(e)) {
      og.clearRect(0, 0, size, size);
      drawLine(og, og, e, startX, startY, size, Color.LIGHTGRAY);
    } else {
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
    }
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

  @Override
  public void onPress(GraphicsContext g, GraphicsContext dg, MouseEvent e, double size, Color color){
    startX = e.getX();
    startY = e.getY();
  }

  private void drawLine(GraphicsContext g,GraphicsContext dg, MouseEvent e, double calcX, double calcY, double size, Color color) {

    double dx = e.getX() - calcX;
    double dy = e.getY() - calcY;


    double distance = Math.hypot(dx, dy);
    int steps = (int) distance;

    for (int i = 0; i <= steps; i++) {
      double t = (double) i / steps;
      double x = calcX + t * dx;
      double y = calcY + t * dy;
      drawAt(g, dg, x, y, size, color);
    }
  }

  private boolean checkNoMovement(MouseEvent e) {
    double x = e.getX();
    double y = e.getY();

    double distance = Math.hypot(x - noMovementX, y - noMovementY);
    long now = System.nanoTime();

    if (distance > movementMargin) {
      lastNoMovementTime = now;
      noMovementX = x;
      noMovementY = y;
      return false;
    }

    return now - lastNoMovementTime > holdThreshold;
  }
}

