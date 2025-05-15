package org.example.paint.tools.generalTools.selectAndMove;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class BoxSelectAndMove extends SelectAndMove {

  //TODO fix occurring blurriness (maybe int casts?)
  //TODO fix problem when marking out of canvas
  //TODO implement no copy if totally background

  private boolean switchingToMoving = false;

  @Override
  public void onDrag(GraphicsContext g, GraphicsContext dg, MouseEvent e, double size, Color color) {
    if (mode == Mode.IDLE) {
      startX = (int)e.getX();
      startY = (int)e.getY();
      mode = Mode.SELECTING;
    }
  }

  @Override
  public void onRelease(GraphicsContext g, GraphicsContext dg, MouseEvent e, double size, Color color) {
    if (mode == Mode.SELECTING) {
      int ex = (int)e.getX(), ey = (int)e.getY();
      double cutWidth = Math.abs(startX - ex), cutHeight = Math.abs(startY - ey);
      if (cutWidth > 0 && cutHeight > 0) {
        double cutX = Math.min(startX, ex), cutY = Math.min(startY, ey);
        WritableImage full = g.getCanvas().snapshot(null, null);
        movedImage = new WritableImage(full.getPixelReader(),
                (int)cutX, (int)cutY,
                (int)cutWidth, (int)cutHeight);
        PixelWriter pw = movedImage.getPixelWriter();

        for (int i = 0; i < cutWidth; i++) {
          for (int j = 0; j < cutHeight; j++) {
            Color pixColor = movedImage.getPixelReader().getColor(i, j);
            if (pixColor.equals(Color.WHITE)) {
              pixColor = Color.TRANSPARENT;
            }
            pw.setColor(i, j, pixColor);
          }
        }
        g.clearRect(cutX-1, cutY-1, cutWidth+2, cutHeight+2);
        mode = Mode.MOVING;
        switchingToMoving = true;
      } else {
        mode = Mode.IDLE;
      }
    }
    else if (mode == Mode.MOVING && movedImage != null) {
      super.printHere(g, e);
    }
  }

  @Override
  public void drawPreviewAt(GraphicsContext og, MouseEvent e, double size) {
    if (mode == Mode.SELECTING) {
      int currX = (int)e.getX(), currY = (int)e.getY();
      int newX = Math.min(startX, currX), newY = Math.min(startY, currY);
      int newWidth = Math.abs(currX - startX), newHeight = Math.abs(currY - startY);

      double m = 5;
      og.clearRect(lastX - m, lastY - m, lastWidth + 2*m, lastHeight + 2*m);
      og.setStroke(Color.GRAY);
      og.strokeRect(newX, newY, newWidth, newHeight);

      lastX = newX; lastY = newY;
      lastWidth = newWidth; lastHeight = newHeight;
    }
    else if (mode == Mode.MOVING && movedImage != null) {
      if (switchingToMoving) {
        double m = 5;
        og.clearRect(lastX - m, lastY - m, lastWidth + 2*m, lastHeight + 2*m);
        switchingToMoving = false;
      }
      super.drawPreviewAt(og, e, size);
    }
  }
}
