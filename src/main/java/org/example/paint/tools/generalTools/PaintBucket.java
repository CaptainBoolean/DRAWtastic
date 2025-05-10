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
  public static void floodFill(GraphicsContext g, int x, int y, Color targetColor, ObjectProperty<Color> fillColor) {
      //checking starting point being out of bounds
      if (x < 0 || x >= g.getCanvas().getWidth() || y < 0 || y >= g.getCanvas().getHeight()) {
          return;
      }

      //capturing current state of canvas
      WritableImage image = g.getCanvas().snapshot(null, null);

      //selected neighbor must have the target color
      if (!image.getPixelReader().getColor(x, y).equals(targetColor)) {
            return;
      }

      g.setFill(fillColor.getValue());
      g.fillRect(x, y, 1, 1);

      //color neighborhood pixels: clockwise
      floodFill(g, x, y-1, targetColor, fillColor); //south
      floodFill(g, x-1, y-1, targetColor, fillColor); //southwest
      floodFill(g, x-1, y, targetColor, fillColor); //west
      floodFill(g, x-1, y+1, targetColor, fillColor); //northwest
      floodFill(g, x, y+1, targetColor, fillColor); //north
      floodFill(g, x+1, y+1, targetColor, fillColor); //northeast
      floodFill(g, x+1, y, targetColor, fillColor); //east
      floodFill(g, x+1, y-1, targetColor, fillColor); //southeast
    }
}
