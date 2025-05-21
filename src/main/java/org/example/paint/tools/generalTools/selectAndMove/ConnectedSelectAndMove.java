package org.example.paint.tools.generalTools.selectAndMove;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.example.paint.controller.FileService;
import org.example.paint.tools.generalTools.FloodFill;

import java.util.ArrayList;


//TODO check if it works after flood fill implement
public class ConnectedSelectAndMove extends SelectAndMove {

  @Override
  public void onPress(GraphicsContext g, GraphicsContext dg, MouseEvent e, double size, Color color) {
    if (mode == Mode.IDLE) {
      ArrayList<int[]> pixels = FloodFill.fill(g, (int)e.getX(), (int)e.getY());
      if (pixels.isEmpty()) {
        return;
      }


      int minX = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE;
      int minY = Integer.MAX_VALUE, maxY = Integer.MIN_VALUE;
      for (int[] p : pixels) {
        int x = p[0], y = p[1];
        if (x < minX) minX = x;
        if (x > maxX) maxX = x;
        if (y < minY) minY = y;
        if (y > maxY) maxY = y;
      }
      int width = maxX - minX + 1;
      int height = maxY - minY + 1;

      WritableImage image = new WritableImage(width, height);
      PixelWriter writableImage = image.getPixelWriter();
      WritableImage full = FileService.getTranspSnapshot(g.getCanvas());

      for (int[] pixel : pixels) {
        int x = pixel[0], y = pixel[1];
        Color c = full.getPixelReader().getColor(x, y);
        writableImage.setColor(x-minX, y-minY, c);
        g.clearRect(x, y, 1.01, 1.01); // ensure full clear
      }
      movedImage = image;
      mode = Mode.MOVING;
    } else if (mode == Mode.MOVING && movedImage != null) {
      super.printHere(g, e);
    }
  }


  @Override
  public void onRelease(GraphicsContext g, GraphicsContext dg, MouseEvent e, double size, Color color) {
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


}
