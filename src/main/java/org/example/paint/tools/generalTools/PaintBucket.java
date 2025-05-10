package org.example.paint.tools.generalTools;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
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

  @Override
  public void onRelease(GraphicsContext g, MouseEvent e, double size) {
      //getting pixel color at mouse position using pipette
      Pipette pipette = new Pipette(new SimpleObjectProperty<>());
      pipette.onRelease(g, e, size);
      Color targetColor = pipette.getColor().getValue();

      if (!targetColor.equals(fillColor.getValue())) {
          floodFill(g, (int) e.getX(), (int) e.getY(), targetColor, fillColor);
      }
  }

  //algorithm to flood fill
  private static void floodFill(GraphicsContext g, int x, int y, Color targetColor, ObjectProperty<Color> fillColor) {
      //waiting for implementation
  }
}
