package org.example.paint.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class SelectAndMove implements Tool{

  double startX = -1, startY = -1;
  double endX = -1, endY = -1;
  private double lastPreviewX = -1;
  private double lastPreviewY = -1;
  WritableImage movedImage;
  double height;
  double width;

  @Override
  public void onDrag(GraphicsContext g, MouseEvent e, double size, Color color, double opacity) {
    if(startX == -1 && startY == -1){
      startX = e.getX();
      startY = e.getY();
    }
  }

  @Override
  public void onRelease(GraphicsContext g, MouseEvent e, double size) {
    if(movedImage == null){
      endX = e.getX();
      endY = e.getY();
      width = (int)Math.abs(startX - endX);
      height = (int)Math.abs(startY - endY);
      WritableImage fullCanvasImage = g.getCanvas().snapshot(null, null);
      movedImage = new WritableImage(fullCanvasImage.getPixelReader(), (int) startX, (int) startY, (int) width, (int) height);
      PixelWriter pixelWriter = movedImage.getPixelWriter();
      for (int x = 0; x < width; x++) {
        for (int y = 0; y < height; y++) {
          Color color = movedImage.getPixelReader().getColor(x, y);
          if (!color.equals(Color.TRANSPARENT)) { //TODO somehow implement the background
            pixelWriter.setColor(x, y, Color.TRANSPARENT);
          } else {
            pixelWriter.setColor(x, y, color);
          }
        }
      }

      //TODO fix not working to left up
      g.clearRect(startX, startY, endX - startX, endY - startY);
      startX = startY = endX = endY = -1;
    } else {
      double insertX = e.getX() - width / 2;
      double insertY = e.getY() - height / 2;
      g.drawImage(movedImage, insertX, insertY);
      movedImage = null;
    }
  }

  @Override
  public void drawPreviewAt(GraphicsContext og, MouseEvent e, double size) {
    if(movedImage != null){
      og.clearRect(lastPreviewX - width / 2, lastPreviewY - height / 2, width, height);
      og.drawImage(movedImage, e.getX() - movedImage.getWidth() / 2, e.getY() - movedImage.getHeight() / 2);
      lastPreviewX = e.getX();
      lastPreviewY = e.getY();
    }

  }
}
