package org.example.paint.tools.generalTools.selectAndMove;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.example.paint.controller.FileService;

public class BoxSelectAndMove extends SelectAndMove {

  private boolean switchingToMoving = false;

  @Override
  public void onDrag(GraphicsContext g, GraphicsContext dg, MouseEvent e, double size) {
    if (mode == Mode.IDLE) {
      startX = (int)e.getX();
      startY = (int)e.getY();
      mode = Mode.SELECTING;
    }
  }

  @Override
  public void onRelease(GraphicsContext g, GraphicsContext dg, MouseEvent e, double size) {
    if (mode == Mode.SELECTING) {
      int ex = (int) Math.max(0, Math.min(e.getX(), g.getCanvas().getWidth() - 1));
      int ey = (int) Math.max(0, Math.min(e.getY(), g.getCanvas().getHeight() - 1));

      double cutWidth = Math.abs(startX - ex), cutHeight = Math.abs(startY - ey);
      if (cutWidth > 0 && cutHeight > 0) {
        double cutX = Math.min(startX, ex), cutY = Math.min(startY, ey);
        WritableImage full = FileService.getTranspSnapshot(g);
        movedImage = new WritableImage(full.getPixelReader(),
                (int)cutX, (int)cutY,
                (int)cutWidth, (int)cutHeight);
        g.clearRect(cutX-1, cutY-1, cutWidth+2, cutHeight+2);
        if(checkIfContent()) {
          mode = Mode.MOVING;
          switchingToMoving = true;
        } else  {
          mode = Mode.FAILEDMOVE;
          switchingToMoving = false;
          movedImage = null;
        }

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
    } else if (mode == Mode.FAILEDMOVE) {
      og.clearRect(0, 0, og.getCanvas().getWidth(), og.getCanvas().getHeight());
      mode = Mode.IDLE;
    }
  }

  private boolean checkIfContent() {
    for(int i = 0; movedImage.getWidth() > i; i++) {
      for(int j = 0; movedImage.getHeight() > j; j++) {
        if (!movedImage.getPixelReader().getColor(i, j).equals(Color.TRANSPARENT)) {
          return true;
        }
      }
    }
    return false;
  }
}
