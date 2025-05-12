package org.example.paint.tools.generalTools.selectAndMove;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;


//TODO does not work at all
public class ConnectedSelectAndMove extends SelectAndMove {

  @Override
  public void onPress(GraphicsContext g, MouseEvent e) {
    if (mode == Mode.IDLE) {
      Image snapshot = g.getCanvas().snapshot(null, null);
      PixelReader reader = snapshot.getPixelReader();
      int width = (int) snapshot.getWidth();
      int height = (int) snapshot.getHeight();
      int startX = (int) e.getX();
      int startY = (int) e.getY();
      Color bg = null; //TODO fix
      int bgARGB = reader.getArgb(startX, startY);
      if (bgARGB == reader.getArgb(startX, startY) && bg.equals(reader.getColor(startX, startY))) {
        return;
      }
      boolean[][] visited = new boolean[width][height];
      int[] bounds = new int[]{startX, startX, startY, startY};
      floodFillScanline(reader, visited, startX, startY, bgARGB, width, height, bounds);
      int minX = bounds[0], maxX = bounds[1], minY = bounds[2], maxY = bounds[3];
      int w = maxX - minX + 1;
      int h = maxY - minY + 1;
      int[] buffer = new int[w * h];
      reader.getPixels(minX, minY, w, h, PixelFormat.getIntArgbInstance(), buffer, 0, w);
      WritableImage regionImage = new WritableImage(w, h);
      PixelWriter writer = regionImage.getPixelWriter();
      writer.setPixels(0, 0, w, h, PixelFormat.getIntArgbInstance(), buffer, 0, w);
      int clearARGB = bgARGB;
      int[] clearSpan = new int[w];
      for (int i = 0; i < w; i++) clearSpan[i] = clearARGB;
      PixelWriter canvasWriter = g.getPixelWriter();
      for (int y = minY; y <= maxY; y++) {
        canvasWriter.setPixels(minX, y, w, 1, PixelFormat.getIntArgbInstance(), clearSpan, 0, w);
      }
      movedImage = regionImage;
      mode = Mode.MOVING;
    } else if (mode == Mode.MOVING && movedImage != null) {
      super.printHere(g, e);
    }
  }

  @Override
  public void onDrag(GraphicsContext g, MouseEvent e, double size, Color color, double opacity) {
    if (mode == Mode.MOVING && movedImage != null) {
      super.printHere(g, e);
    }
  }

  @Override
  public void onRelease(GraphicsContext g, MouseEvent e, double size) {
    if (mode == Mode.MOVING && movedImage != null) {
      super.printHere(g, e);
      movedImage = null;
      mode = Mode.IDLE;
    }
  }

  @Override
  public void drawPreviewAt(GraphicsContext og, MouseEvent e, double size) {
    if (mode == Mode.MOVING && movedImage != null) {
      super.drawPreviewAt(og, e, size);
    }
  }

  private void floodFillScanline(PixelReader reader, boolean[][] visited,
                                 int startX, int startY, int bgARGB,
                                 int width, int height, int[] b) {
    Deque<Point> stack = new ArrayDeque<>();
    stack.push(new Point(startX, startY));
    while (!stack.isEmpty()) {
      Point p = stack.pop();
      int x = p.x, y = p.y;
      int left = x;
      while (left >= 0 && !visited[left][y] && reader.getArgb(left, y) != bgARGB) {
        visited[left][y] = true;
        left--;
      }
      int right = x + 1;
      while (right < width && !visited[right][y] && reader.getArgb(right, y) != bgARGB) {
        visited[right][y] = true;
        right++;
      }
      int spanMinX = left + 1, spanMaxX = right - 1;
      if (spanMinX < b[0]) b[0] = spanMinX;
      if (spanMaxX > b[1]) b[1] = spanMaxX;
      if (y < b[2]) b[2] = y;
      if (y > b[3]) b[3] = y;
      for (int nx = spanMinX; nx <= spanMaxX; nx++) {
        if (y > 0 && !visited[nx][y - 1] && reader.getArgb(nx, y - 1) != bgARGB) {
          stack.push(new Point(nx, y - 1));
        }
        if (y < height - 1 && !visited[nx][y + 1] && reader.getArgb(nx, y + 1) != bgARGB) {
          stack.push(new Point(nx, y + 1));
        }
      }
    }
  }
}
