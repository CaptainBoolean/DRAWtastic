package org.example.paint.tools.pens;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;


/**
This is the abstract form of a Pen providing all necessary common fields, managing the position of drawing, preview and recalculation time to preserve performance.
 */
public abstract class Pen extends DrawingTool {

  long lastTimestamp = System.nanoTime();
  int recalculateTime = 5000000;
  long timeElapsed = 0;

  private Timeline timeline;
  protected MouseEvent lastMouseEvent;
  private double lastSize;
  private GraphicsContext dg;
  private GraphicsContext og;
  private double lastDrawingOpac = -1;
  private boolean previewDrawn;
  private final long holdThreshold = 500000000;
  private long lastNoMovementTime = System.nanoTime();
  private final double movementMargin = 5;
  private double noMovementX = -1;
  private double noMovementY = -1;

  @Override
  public void onRelease(GraphicsContext g, GraphicsContext dg, MouseEvent e, double size) {
    if (checkNoMovement(e)) {
      dg.clearRect(0, 0, dg.getCanvas().getWidth(), dg.getCanvas().getHeight());
      drawLine(g, dg, e, startX, startY, size);
    }
    dg.getCanvas().setOpacity(lastDrawingOpac);
    og.getCanvas().setOpacity(1);
    og.clearRect(0, 0, dg.getCanvas().getWidth(), dg.getCanvas().getHeight());
    lastDrawingOpac = -1;
    previewDrawn = false;
    timeline = null;
    super.onRelease(g, dg, e, size);
  }



  @Override
  public void onDrag(GraphicsContext g, GraphicsContext dg, MouseEvent e, double size) {
    super.onDrag(g, dg, e, size);
    lastMouseEvent = e;
    lastSize = size;
    resetHoldTimer();
  }

  @Override
  public void onPress(GraphicsContext g, GraphicsContext dg, MouseEvent e, double size) {
    super.onPress(g, dg, e, size);
    this.dg = dg;
    lastDrawingOpac = dg.getCanvas().getOpacity();
  }

  @Override
  public void drawPreviewAt(GraphicsContext og, MouseEvent e, double size) {
    this.og = og;
    if (checkNoMovement(e) && startX != -1 && startY != -1) {
      og.clearRect(0, 0, og.getCanvas().getWidth(), og.getCanvas().getHeight());
      drawLine(og, og, e, startX, startY, size);
      dg.getCanvas().setOpacity(lastDrawingOpac * 0.3);
      og.getCanvas().setOpacity(lastDrawingOpac);
    } else {
      if(previewDrawn) {
        previewDrawn = false;
        og.clearRect(0, 0, og.getCanvas().getWidth(), og.getCanvas().getHeight());
        dg.getCanvas().setOpacity(lastDrawingOpac);
        og.getCanvas().setOpacity(1);
      }
      super.drawPreviewAt(og, e, size);
    }
  };



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

  protected boolean checkNoMovement(MouseEvent e) {
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

  private void resetHoldTimer() {
    if (timeline != null) {
      timeline.stop();
    }

    timeline = new Timeline(new KeyFrame(Duration.millis(500), e -> {
      if (lastMouseEvent != null && checkNoMovement(lastMouseEvent) && !previewDrawn) {
        previewDrawn = true;
        drawPreviewAt(
                og,
                lastMouseEvent,
                lastSize
        );
      }
    }));

    timeline.setCycleCount(1);
    timeline.play();

  }


}

