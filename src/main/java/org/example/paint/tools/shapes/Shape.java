package org.example.paint.tools.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.example.paint.tools.Opaqueable;
import org.example.paint.tools.Tool;

public abstract class Shape implements Tool, Opaqueable {
  private double startX, startY;
  private boolean drawing = false;
  private Color currColor = Color.BLACK;

  @Override
  public void onPress(GraphicsContext graphicsContext, GraphicsContext previewGraphicsContext, MouseEvent mouseEvent, double size, Color color) {
    startX = mouseEvent.getX();
    startY = mouseEvent.getY();
    currColor = color;
    drawing = true;
  }

  @Override
  public void onDrag(GraphicsContext graphicsContext, GraphicsContext dg, MouseEvent mouseEvent, double size, Color color) {
    if (!drawing || dg == null) return;

    dg.clearRect(0, 0, dg.getCanvas().getWidth(), dg.getCanvas().getHeight());

    dg.setStroke(color);
    dg.setLineWidth(size);
    dg.setGlobalAlpha(color.getOpacity());

    drawShape(dg, startX, startY, mouseEvent.getX(), mouseEvent.getY(), color);
  }

  @Override
  public void onRelease(GraphicsContext graphicsContext, GraphicsContext dg, MouseEvent mouseEvent, double size, Color color) {
    if (!drawing) return;

    dg.setFill(currColor);
    dg.setGlobalAlpha(1);
    drawShape(dg, startX, startY, mouseEvent.getX(), mouseEvent.getY(), currColor);

    drawing = false;

  }


  public abstract void drawShape(GraphicsContext graphicsContext, double startX, double startY, double endX, double endY, Color color);

}
