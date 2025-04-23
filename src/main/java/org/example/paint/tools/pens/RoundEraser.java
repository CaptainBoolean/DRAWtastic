package org.example.paint.tools.pens;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class RoundEraser extends Pen {

  @Override
  protected void drawAt(GraphicsContext g, double x, double y, double size, Color color, double opacity) {
    //TODO find method to clear oval
    g.clearRect(x, y, size, size);
  }

  @Override
  public void drawPreviewAt(GraphicsContext og, MouseEvent e, double size) {

  }
}
