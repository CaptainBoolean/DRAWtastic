package org.example.paint.tools.generalTools;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.example.paint.tools.Tool;

public class PaintBucket implements Tool {

  private static ObjectProperty<Color> fillColor = new SimpleObjectProperty<>();

  public PaintBucket() {}

  public PaintBucket(ObjectProperty<Color> fillColor) {
      PaintBucket.fillColor = fillColor;
  }

  @Override
  public void onRelease(GraphicsContext g, MouseEvent e, double size) {
      Color targetColor = new WritableImage((int) g.getCanvas().getWidth(), (int) g.getCanvas().getHeight()).getPixelReader().getColor((int) e.getX(), (int) e.getY());

      if (!targetColor.equals(fillColor.getValue())) {
          fillAll(g, targetColor, fillColor);
      }
  }

  private static void fillAll(GraphicsContext g, Color targetColor, ObjectProperty<Color> fillColor) {
      int width = (int) g.getCanvas().getWidth();
      int height = (int) g.getCanvas().getHeight();

      WritableImage image = new WritableImage(width, height);
      g.getCanvas().snapshot(null, image);

      PixelReader pixelReader = image.getPixelReader();
      PixelWriter pixelWriter = g.getPixelWriter();

      Color fill = fillColor.getValue();

      for (int i = 0; i < height; i++) { //iterating through each pixel
          for (int j = 0; j < width; j++) {
              Color currentColor = pixelReader.getColor(j, i);
              if (currentColor.equals(targetColor)) { //replace the pixel with the target color with fill color
                  pixelWriter.setColor(j, i, fill);
              }
          }
      }
  }

  //TODO implement preview
}
