package org.example.paint.tools.generalTools.selectAndMove;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.example.paint.core.FileService;

public class BoxSelectAndMove extends SelectAndMove {


  /**
   * Switches modes depending on the current mode and initializes variables
   * @param e MouseEvent that triggered this
   */
  @Override
  public void onPress(GraphicsContext g, GraphicsContext dg, MouseEvent e, double size) {
    if (mode == Mode.IDLE) {
      startX = (int)e.getX();
      startY = (int)e.getY();
      mode = Mode.SELECTING;
    }
  }

  /**
   * Cuts the {@link #movedImage} out of the canvas after selecting,
   * or prints the picture using {@link #printHere(GraphicsContext, MouseEvent)},
   * and changes current modes
   * @param g Main canvas
   * @param dg Drawing canvas
   * @param e MouseEvent that triggered this
   */
  @Override
  public void onRelease(GraphicsContext g, GraphicsContext dg, MouseEvent e, double size) {
    if (mode == Mode.SELECTING) {
      int ex = (int) Math.max(0, Math.min(e.getX(), g.getCanvas().getWidth() - 1));
      int ey = (int) Math.max(0, Math.min(e.getY(), g.getCanvas().getHeight() - 1));

      double cutWidth = Math.abs(startX - ex), cutHeight = Math.abs(startY - ey);
      if (cutWidth > 0 && cutHeight > 0) {
        double cutX = Math.min(startX, ex), cutY = Math.min(startY, ey);
        WritableImage full = FileService.getTransparentSnapshot(g);
        movedImage = new WritableImage(full.getPixelReader(),
                (int)cutX, (int)cutY,
                (int)cutWidth, (int)cutHeight);
        g.clearRect(cutX-1, cutY-1, cutWidth+2, cutHeight+2);
        if(checkIfContent()) {
          mode = Mode.MOVING;
          switchingToMove = true;
        } else  {
          mode = Mode.FAILEDMOVE;
          switchingToMove = false;
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

  /**
   * Draws the different previews while selecting or moving
   * @param og Canvas to draw on
   * @param e MouseEvent that triggered  this
   */
  @Override
  public void drawPreviewAt(GraphicsContext og, MouseEvent e, double size) {
    double margin = 5;

    if (mode == Mode.SELECTING) {
      int currX = (int)e.getX(), currY = (int)e.getY();
      int newX = Math.min(startX, currX), newY = Math.min(startY, currY);
      int newWidth = Math.abs(currX - startX), newHeight = Math.abs(currY - startY);

      og.clearRect(lastX - margin, lastY - margin, lastWidth + 2* margin, lastHeight + 2* margin);
      og.setStroke(Color.GRAY);
      og.strokeRect(newX, newY, newWidth, newHeight);

      lastX = newX; lastY = newY;
      lastWidth = newWidth; lastHeight = newHeight;
    }
    else if (mode == Mode.MOVING && movedImage != null) {
      if (switchingToMove) {
        og.clearRect(lastX - margin, lastY - margin, lastWidth + 2* margin, lastHeight + 2* margin);
        switchingToMove = false;
      }
      super.drawPreviewAt(og, e, size);
    } else if (mode == Mode.FAILEDMOVE) {
      og.clearRect(0, 0, og.getCanvas().getWidth(), og.getCanvas().getHeight());
      mode = Mode.IDLE;
    }
  }

  /**
   * Checks if the {@link SelectAndMove#movedImage} is not totally empty
   * @return true if there is any content in {@link SelectAndMove#movedImage}
   */
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
