package org.example.paint.tools.generalTools.selectAndMove;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import org.example.paint.tools.Tool;

public abstract class SelectAndMove implements Tool {
  protected enum Mode {IDLE, SELECTING, MOVING, FAILEDMOVE}
  protected Mode mode = Mode.IDLE;
  protected int startX = -1, startY = -1;
  protected int lastX = 0, lastY = 0, lastWidth = 0, lastHeight = 0;
  protected WritableImage movedImage;
  protected boolean switchingToMove = false;

  /**
   * Draws the preview of the {@link #movedImage}
   * @param og Canvas to draw on
   * @param e MouseEvent that triggered  this
   */
  @Override
  public void drawPreviewAt(GraphicsContext og, MouseEvent e, double size) {
    int currX = (int)e.getX(), currY = (int)e.getY();
    int imageWidth = (int)movedImage.getWidth(), imageHeight = (int)movedImage.getHeight();
    int newX = currX - imageWidth/2, newY = currY - imageHeight/2;

    og.clearRect(lastX - (double) imageWidth /2, lastY - (double) imageHeight /2, imageWidth, imageHeight);
    og.drawImage(movedImage, newX, newY);

    lastX = currX; lastY = currY;
    lastWidth = imageWidth; lastHeight = imageHeight;
  }

  /**
   * Prints the {@link #movedImage} at the location of the MouseEvent on the provided canvas and resets it
   * @param g Canvas to print on
   * @param e MouseEvent to take the location from
   */
  protected void printHere(GraphicsContext g, MouseEvent e) {
    int printX = (int)e.getX() - (int)(movedImage.getWidth() / 2);
    int printY = (int)e.getY() - (int)(movedImage.getHeight() / 2);
    g.drawImage(movedImage, printX, printY);

    movedImage = null;
    mode = Mode.IDLE;
  }
}


