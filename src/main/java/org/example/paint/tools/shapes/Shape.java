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
  public void onDrag(GraphicsContext graphicsContext, GraphicsContext previewGraphicsContext, MouseEvent mouseEvent, double size, Color color) {
    if (!drawing || previewGraphicsContext == null) return;

    previewGraphicsContext.clearRect(0, 0, previewGraphicsContext.getCanvas().getWidth(), previewGraphicsContext.getCanvas().getHeight());

    previewGraphicsContext.setStroke(color);
    previewGraphicsContext.setLineWidth(size);
    previewGraphicsContext.setGlobalAlpha(color.getOpacity());

    drawShape(previewGraphicsContext, startX, startY, mouseEvent.getX(), mouseEvent.getY(), color);
  }

  @Override
  public void onRelease(GraphicsContext graphicsContext, GraphicsContext previewGraphicsContext, MouseEvent mouseEvent, double size, Color color) {
    if (!drawing) return;

    graphicsContext.setStroke(currColor);
    graphicsContext.setLineWidth(size);
    graphicsContext.setGlobalAlpha(currColor.getOpacity());

    drawShape(graphicsContext, startX, startY, mouseEvent.getX(), mouseEvent.getY(), currColor);

    drawing = false;

    if (previewGraphicsContext != null) {
      previewGraphicsContext.clearRect(0, 0, previewGraphicsContext.getCanvas().getWidth(), previewGraphicsContext.getCanvas().getHeight());
    }
  }

  @Override
  public void drawPreviewAt(GraphicsContext previewGraphicsContext, MouseEvent mouseEvent, double size) {
    if (drawing) {
      onDrag(previewGraphicsContext, null, mouseEvent, size, currColor);
    }
  }

  public abstract void drawShape(GraphicsContext graphicsContext, double startX, double startY, double endX, double endY, Color color);

}
