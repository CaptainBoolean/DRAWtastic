package org.example.paint.tools.Shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.example.paint.tools.Tool;

public class Shape implements Tool {
  @Override
  public void onDrag(GraphicsContext g, MouseEvent e, double size, Color color, double opacity) {

  }

  @Override
  public void onRelease(GraphicsContext g, MouseEvent e, double size) {

  }

  @Override
  public void drawPreviewAt(GraphicsContext og, MouseEvent e, double size) {

  }
}
