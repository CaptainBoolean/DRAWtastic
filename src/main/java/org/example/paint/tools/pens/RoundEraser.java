package org.example.paint.tools.pens;

import javafx.scene.canvas.GraphicsContext;

public class RoundEraser extends Pen {

  public RoundEraser() {
    super(null);
  }

  @Override
  protected void drawAt(GraphicsContext g, double x, double y, double size) {
    //TODO find method to clear oval
    g.clearRect(x, y, size, size);
  }
}
