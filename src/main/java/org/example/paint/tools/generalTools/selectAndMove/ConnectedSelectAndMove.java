package org.example.paint.tools.generalTools.selectAndMove;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.example.paint.core.FileService;
import org.example.paint.tools.generalTools.SelectAreas;

import java.util.ArrayList;


public class ConnectedSelectAndMove extends SelectAndMove {

  private boolean switchingToMove;

  @Override
  public void onPress(GraphicsContext g, GraphicsContext dg, MouseEvent e, double size) {
    if (mode == Mode.IDLE) {
      ArrayList<int[]> pixels = SelectAreas.floodFillSelected(g, (int)e.getX(), (int)e.getY());
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
      int width = (int) Math.max(maxX - e.getX(), e.getX() - minX)*2 + 1;
      int height = (int) Math.max(maxY - e.getY(), e.getY() - minY)*2 + 1;

      int offsetX = (int)(width / 2 - (e.getX() - minX));
      int offsetY = (int)(height / 2 - (e.getY() - minY));

      WritableImage image = new WritableImage(width, height);
      PixelWriter writableImage = image.getPixelWriter();
      WritableImage full = FileService.getTranspSnapshot(g);

      for (int[] pixel : pixels) {
        int x = pixel[0], y = pixel[1];
        Color c = full.getPixelReader().getColor(x, y);
        writableImage.setColor(x-minX+offsetX, y-minY+offsetY, c);
        g.clearRect(x, y, 1.1, 1.1); // ensure full clear
      }
      movedImage = image;
      switchingToMove = true;
      mode = Mode.MOVING;
    } else if (mode == Mode.MOVING && movedImage != null) {
      super.printHere(g, e);
    }
  }


  @Override
  public void onRelease(GraphicsContext g, GraphicsContext dg, MouseEvent e, double size) {
    if (mode == Mode.MOVING && movedImage != null) {
      super.printHere(g, e);
      movedImage = null;
      mode = Mode.IDLE;
    }
  }

  @Override
  public void drawPreviewAt(GraphicsContext og, MouseEvent e, double size) {
    if (switchingToMove) {
      og.clearRect(0, 0, og.getCanvas().getWidth(), og.getCanvas().getHeight());
      switchingToMove = false;
    }
    if (mode == Mode.MOVING && movedImage != null) {
      super.drawPreviewAt(og, e, size);
    }
  }


}
