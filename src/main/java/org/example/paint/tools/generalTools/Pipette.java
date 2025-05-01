package org.example.paint.tools.generalTools;

import javafx.beans.property.ObjectProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.example.paint.tools.Tool;

public class Pipette implements Tool {
  private ObjectProperty<Color> color = null;

  public Pipette() {}

  public Pipette(ObjectProperty<Color> color) {
    this.color = color;
  }

  //TODO implement - leave constructor and just modify the color.setValue with the color of the onRelease pixel

  @Override
  public void onRelease(GraphicsContext g, MouseEvent e, double size) {

  }

}
