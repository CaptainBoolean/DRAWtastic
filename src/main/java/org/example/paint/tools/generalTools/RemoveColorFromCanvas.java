package org.example.paint.tools.generalTools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.example.paint.core.FileService;
import org.example.paint.tools.Tool;

public class RemoveColorFromCanvas implements Tool {

  WritableImage tempImage;
  PixelReader pixelReader;
  PixelWriter pixelWriter;
  Color pickedColor;

  @Override
  public void onPress(GraphicsContext g, GraphicsContext dg, MouseEvent e, double size) {

    tempImage = FileService.getTranspSnapshot(g);
    pixelReader = tempImage.getPixelReader();
    pixelWriter = tempImage.getPixelWriter();

    pickedColor = tempImage.getPixelReader().getColor((int)e.getX(), (int)e.getY());

    for (int x = 0; x < tempImage.getWidth(); x++) {
      for (int y = 0; y < tempImage.getHeight(); y++) {
        if (checkColorMatch(pixelReader.getColor(x, y), 0.2)) {
          pixelWriter.setColor(x, y, Color.TRANSPARENT);
          removeAdjacientPixels(x,y);
        }
      }
    }
    g.clearRect(0, 0, tempImage.getWidth(), tempImage.getHeight());
    g.drawImage(tempImage, 0, 0, tempImage.getWidth(), tempImage.getHeight());
  }

  private boolean checkColorMatch(Color imageC, double tolerance) {

    double dr = imageC.getRed() - pickedColor.getRed();
    double dg = imageC.getGreen() - pickedColor.getGreen();
    double db = imageC.getBlue() - pickedColor.getBlue();
    double da = imageC.getOpacity() - pickedColor.getOpacity();

    return Math.sqrt(dr * dr + dg * dg + db * db + da * da) < tolerance;
  }

  private void removeAdjacientPixels(int x, int y) {
    int radius = 2;

    for (int dx = -radius; dx <= radius; dx++) {
      for (int dy = -radius; dy <= radius; dy++) {
        int absX = x + dx;
        int absY = y + dy;
        if (absX >= 0 && absY >= 0 && absX < tempImage.getWidth() && absY < tempImage.getHeight()) {
          Color imageC = tempImage.getPixelReader().getColor(absX, absY);
          if (checkColorMatch(imageC, 0.5)) {
            pixelWriter.setColor(absX, absY, Color.TRANSPARENT);
          }
        }
      }
    }
  }
}
