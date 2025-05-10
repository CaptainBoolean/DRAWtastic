package org.example.paint.tools.generalTools;

import javafx.beans.property.ObjectProperty;
import javafx.scene.canvas.GraphicsContext;
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

  //TODO: musing Pipette instead these first lines of code down here
  @Override
  public void onRelease(GraphicsContext g, MouseEvent e, double size) {
      double x = e.getX();
      double y = e.getY();

      //creating WriteableImage with same size as canvas
      WritableImage image = new WritableImage((int) g.getCanvas().getWidth(), (int) g.getCanvas().getHeight());
      //capturing current state of canvas
      g.getCanvas().snapshot(null, image);

      //getting pixel color at mouse position
      Color targetColor = image.getPixelReader().getColor((int) x, (int) y);

      if (!targetColor.equals(fillColor.getValue())) {
          floodFill(g, (int) x, (int) y, targetColor, fillColor);
      }
  }

  //algorithm to flood fill
  private static void floodFill(GraphicsContext g, int x, int y, Color targetColor, ObjectProperty<Color> fillColor) {
      //waiting for implementation
  }
}
