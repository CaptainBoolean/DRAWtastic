package org.example.paint.tools.generalTools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.example.paint.controller.FileService;
import org.example.paint.tools.Tool;

public class RemoveColorFromCanvas implements Tool {

  WritableImage tempImage;

  @Override
  public void onPress(GraphicsContext g, GraphicsContext dg, MouseEvent e, double size, Color color) {

    tempImage = FileService.getTranspSnapshot(g.getCanvas());
    PixelReader pixelReader = tempImage.getPixelReader();
    PixelWriter pixelWriter = tempImage.getPixelWriter();

    Color pickedColor = tempImage.getPixelReader().getColor((int)e.getX(), (int)e.getY());

    //TODO fix account fo opaque?
    for (int x = 0; x < tempImage.getWidth(); x++) {
      for (int y = 0; y < tempImage.getHeight(); y++) {
        if (checkColorMath(pixelReader.getColor(x, y), pickedColor)) {
          pixelWriter.setColor(x, y, Color.TRANSPARENT);
        }
      }
    }
    g.clearRect(0, 0, tempImage.getWidth(), tempImage.getHeight());
    g.drawImage(tempImage, 0, 0, tempImage.getWidth(), tempImage.getHeight());
  }

  private boolean checkColorMath(Color imageC, Color pickedC) {
    double tolerance = 0.2;
    double dr = imageC.getRed() - pickedC.getRed();
    double dg = imageC.getGreen() - pickedC.getGreen();
    double db = imageC.getBlue() - pickedC.getBlue();
    return Math.sqrt(dr * dr + dg * dg + db * db) < tolerance;
  }




}
