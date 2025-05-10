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

  private ObjectProperty<Color> fillColor = null;

  public PaintBucket() {}

  public PaintBucket(ObjectProperty<Color> fillColor) {
      this.fillColor = fillColor;
  }

  @Override
  public void onRelease(GraphicsContext g, MouseEvent e, double size) {
      //getting pixel color at mouse position using pipette
      Pipette pipette = new Pipette(new SimpleObjectProperty<>());
      pipette.onRelease(g, e, size);
      Color targetColor = pipette.getColor().getValue();

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
}
