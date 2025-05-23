package org.example.paint.tools.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.example.paint.tools.Opaqueable;
import org.example.paint.tools.Tool;

public class Shape implements Tool, Opaqueable {
  private double startX, startY;
  private boolean drawing = false;
  private Color currColor = Color.BLACK;

  @Override
  public void onPress(GraphicsContext drawShape, GraphicsContext dg, MouseEvent mouse, double size, Color color) {
    startX = mouse.getX();
    startY = mouse.getY();
    drawing = true;
  }

  @Override
  public void onDrag(GraphicsContext g, GraphicsContext dg, MouseEvent e, double size, Color color) {

  }

  @Override
  public void onRelease(GraphicsContext g, GraphicsContext dg, MouseEvent e, double size, Color color) {

  }

  @Override
  public void drawPreviewAt(GraphicsContext og, MouseEvent e, double size) {

    }
}
