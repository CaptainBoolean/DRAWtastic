package org.example.paint.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class SelectAndMove implements Tool {

  //TODO proofread and implement real background (also bugtest)

  private enum Mode { IDLE, SELECTING, MOVING }
  private Mode mode = Mode.IDLE;
  private boolean switchingToMoving = false;

  private double startX = -1, startY = -1;
  private double lastX = 0, lastY = 0, lastW = 0, lastH = 0;
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
      double w = Math.abs(startX - ex), h = Math.abs(startY - ey);
      if (w > 0 && h > 0) {
        double x = Math.min(startX, ex), y = Math.min(startY, ey);
        WritableImage full = g.getCanvas().snapshot(null, null);
        movedImage = new WritableImage(full.getPixelReader(),
                (int)x, (int)y,
                (int)w, (int)h);
        PixelWriter pw = movedImage.getPixelWriter();
        for (int ix = 0; ix < w; ix++)
          for (int iy = 0; iy < h; iy++) {
            Color c = movedImage.getPixelReader().getColor(ix, iy);
            pw.setColor(ix, iy, c.equals(Color.WHITE)
                    ? Color.TRANSPARENT
                    : c);
          }
        g.clearRect(x, y, w, h);
        mode = Mode.MOVING;
        switchingToMoving = true;
      } else {
        mode = Mode.IDLE;
      }
    }
    else if (mode == Mode.MOVING && movedImage != null) {
      double px = e.getX() - movedImage.getWidth()  / 2;
      double py = e.getY() - movedImage.getHeight() / 2;
      g.drawImage(movedImage, px, py);
      movedImage = null;
      mode = Mode.IDLE;
    }
  }

  @Override
  public void drawPreviewAt(GraphicsContext og, MouseEvent e, double size) {
    if (mode == Mode.SELECTING) {
      double cx = e.getX(), cy = e.getY();
      double nx = Math.min(startX, cx), ny = Math.min(startY, cy);
      double nw = Math.abs(cx - startX), nh = Math.abs(cy - startY);

      double m = 1; // stroke margin
      og.clearRect(lastX - m, lastY - m, lastW + 2*m, lastH + 2*m);
      og.setStroke(Color.GRAY);
      og.strokeRect(nx, ny, nw, nh);

      lastX = nx; lastY = ny;
      lastW = nw; lastH = nh;
    }
    else if (mode == Mode.MOVING && movedImage != null) {
      if (switchingToMoving) {
        double m = 1;
        og.clearRect(lastX - m, lastY - m, lastW + 2*m, lastH + 2*m);
        switchingToMoving = false;
      }
      double cx = e.getX(), cy = e.getY();
      double iw = movedImage.getWidth(), ih = movedImage.getHeight();
      double nx = cx - iw/2, ny = cy - ih/2;

      og.clearRect(lastX - iw/2, lastY - ih/2, iw, ih);
      og.drawImage(movedImage, nx, ny);

      lastX = cx; lastY = cy;
      lastW = iw; lastH = ih;
    }
  }
}
