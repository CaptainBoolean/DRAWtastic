package org.example.paint.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.example.paint.controller.DrawtasticController;

public class SelectAndMove implements Tool {

  //TODO fix occurring blurriness

  private enum Mode { IDLE, SELECTING, MOVING }
  private Mode mode = Mode.IDLE;
  private boolean switchingToMoving = false;

  private double startX = -1, startY = -1;
  private double lastX = 0, lastY = 0, lastWidth = 0, lastHeight = 0;
  private WritableImage movedImage;

  @Override
  public void onDrag(GraphicsContext g, MouseEvent e, double size, Color color, double opacity) {
    if (mode == Mode.IDLE) {
      startX = e.getX();
      startY = e.getY();
      mode = Mode.SELECTING;
    }
  }

  @Override
  public void onRelease(GraphicsContext g, MouseEvent e, double size) {
    if (mode == Mode.SELECTING) {
      double ex = e.getX(), ey = e.getY();
      double cutWidth = Math.abs(startX - ex), cutHeight = Math.abs(startY - ey);
      if (cutWidth > 0 && cutHeight > 0) {
        double cutX = Math.min(startX, ex), cutY = Math.min(startY, ey);
        WritableImage full = g.getCanvas().snapshot(null, null);
        movedImage = new WritableImage(full.getPixelReader(),
                (int)cutX, (int)cutY,
                (int)cutWidth, (int)cutHeight);
        PixelWriter pw = movedImage.getPixelWriter();
        for (int i = 0; i < cutWidth; i++)
          for (int j = 0; j < cutHeight; j++) {
            Color color = movedImage.getPixelReader().getColor(i, j);
            Color background = DrawtasticController.getBackgroundColor();
            pw.setColor(i, j, color.equals(background) ? Color.TRANSPARENT : color);
          }
        g.clearRect(cutX, cutY, cutWidth, cutHeight);
        mode = Mode.MOVING;
        switchingToMoving = true;
      } else {
        mode = Mode.IDLE;
      }
    }
    else if (mode == Mode.MOVING && movedImage != null) {
      double printX = e.getX() - movedImage.getWidth()  / 2;
      double printY = e.getY() - movedImage.getHeight() / 2;
      g.drawImage(movedImage, printX, printY);
      movedImage = null;
      mode = Mode.IDLE;
    }
  }

  @Override
  public void drawPreviewAt(GraphicsContext og, MouseEvent e, double size) {
    if (mode == Mode.SELECTING) {
      double currX = e.getX(), currY = e.getY();
      double newX = Math.min(startX, currX), newY = Math.min(startY, currY);
      double newWidth = Math.abs(currX - startX), newHeight = Math.abs(currY - startY);

      double m = 1; // stroke margin
      og.clearRect(lastX - m, lastY - m, lastWidth + 2*m, lastHeight + 2*m);
      og.setStroke(Color.GRAY);
      og.strokeRect(newX, newY, newWidth, newHeight);

      lastX = newX; lastY = newY;
      lastWidth = newWidth; lastHeight = newHeight;
    }
    else if (mode == Mode.MOVING && movedImage != null) {
      if (switchingToMoving) {
        double m = 1;
        og.clearRect(lastX - m, lastY - m, lastWidth + 2*m, lastHeight + 2*m);
        switchingToMoving = false;
      }
      double currX = e.getX(), currY = e.getY();
      double imageWidth = movedImage.getWidth(), imageHeight = movedImage.getHeight();
      double newX = currX - imageWidth/2, newY = currY - imageHeight/2;

      og.clearRect(lastX - imageWidth/2, lastY - imageHeight/2, imageWidth, imageHeight);
      og.drawImage(movedImage, newX, newY);

      lastX = currX; lastY = currY;
      lastWidth = imageWidth; lastHeight = imageHeight;
    }
  }
}
