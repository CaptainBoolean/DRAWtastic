package org.example.paint.tools.pens;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import org.example.paint.controller.FileService;

public class Blur extends Pen {

  private WritableImage snapshot = null;
  private long lastTimestamp;

  //TODO transparent is scho a transparent :) <3
  //TODO fix laggyness and maybe update more frequently and not every stroke? (performance problem? no multithread in java fx)
  @Override
  protected void drawAt(GraphicsContext g, GraphicsContext dg, double x, double y, double size, Color color) {
    long currentTime = System.nanoTime();
    long timeElapsed = currentTime - lastTimestamp;
    if ((lastX == -1 && lastY == -1) || timeElapsed > 5000000) {
      snapshot = FileService.getTranspSnapshot(g.getCanvas());
      lastTimestamp = currentTime;
    }
    PixelReader reader = snapshot.getPixelReader();

    if (reader == null) return;

    int radius = (int) (size / 2);
    int startX = (int) x - radius;
    int startY = (int) y - radius;

    double red = 0, green = 0, blue = 0;
    int count = 0;

    for (int dx = 0; dx < size; dx++) {
      for (int dy = 0; dy < size; dy++) {
        int px = startX + dx;
        int py = startY + dy;

        if (px >= 0 && py >= 0 && px < snapshot.getWidth() && py < snapshot.getHeight()) {
          Color sampled = reader.getColor(px, py);
          if (sampled.getOpacity() > 0) {
            red += sampled.getRed();
            green += sampled.getGreen();
            blue += sampled.getBlue();
            count++;

          }

        }
      }
    }

    if (count > 0) {
      Color avgColor = new Color(red / count, green / count, blue / count,1);
      g.setFill(avgColor);
      g.fillOval(x - size / 2, y - size / 2, size, size);
    }
  }
  
  @Override
  protected void drawPreviewAt(GraphicsContext og, double x, double y, double size) {
    og.setStroke(Color.GRAY);
    og.setLineWidth(1);
    og.strokeOval(x - size / 2, y - size / 2, size, size);
  }
}
