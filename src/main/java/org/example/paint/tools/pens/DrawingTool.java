package org.example.paint.tools.pens;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.example.paint.tools.Tool;

public class DrawingTool implements Tool {
  @Override
  public void onDrag(GraphicsContext g, GraphicsContext dg, MouseEvent e, double size, Color color) {
    Tool.super.onDrag(g, dg, e, size, color);
  }

  @Override
  public void onPress(GraphicsContext g, GraphicsContext dg, MouseEvent e, double size, Color color) {
    Tool.super.onPress(g, dg, e, size, color);
  }

  @Override
  public void onRelease(GraphicsContext g, GraphicsContext dg, MouseEvent e, double size, Color color) {
    Tool.super.onRelease(g, dg, e, size, color);
  }

  @Override
  public void drawPreviewAt(GraphicsContext og, MouseEvent e, double size) {
    Tool.super.drawPreviewAt(og, e, size);
  }
}
