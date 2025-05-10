package org.example.paint.tools.generalTools;

import javafx.beans.property.ObjectProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.example.paint.tools.Tool;

public class Pipette implements Tool {
  private ObjectProperty<Color> color = null;

  public Pipette() {}

  public Pipette(ObjectProperty<Color> color) {
    this.color = color;
  }

  @Override
  public void onRelease(GraphicsContext g, MouseEvent e, double size) {
    double x = e.getX();
    double y = e.getY();

    //creating WriteableImage with same size as canvas
    WritableImage image = new WritableImage((int) g.getCanvas().getWidth(), (int) g.getCanvas().getHeight());
    //capturing current state of canvas
    g.getCanvas().snapshot(null, image);

    //getting pixel color at mouse position
    Color pickedColor = image.getPixelReader().getColor((int) x, (int) y);

    //setting pixel color to the color property
    if (color != null) {
      color.setValue(pickedColor);
    }
  }

  public ObjectProperty<Color> getColor() {
    return color;
  }

}
