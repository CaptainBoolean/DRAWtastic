package org.example.paint.tools.generalTools;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.example.paint.controller.FileService;
import org.example.paint.tools.Tool;

public class Pipette implements Tool {
  private ObjectProperty<Color> color = new SimpleObjectProperty<>();
  private ObjectProperty<Color> backgroundColor = new SimpleObjectProperty<>();

  public Pipette() {}


  public Pipette(ObjectProperty<Color> color, ObjectProperty<Color> backgroundColor) {
    this.color = color;
    this.backgroundColor = backgroundColor;
  }

  @Override
  public void onRelease(GraphicsContext g, GraphicsContext dg, MouseEvent e, double size, Color color) {
    PixelReader pixelReader = FileService.getTranspSnapshot(g.getCanvas()).getPixelReader();
    Color pickedColor = pixelReader.getColor((int)e.getX(), (int)e.getY());

    if (pickedColor.equals(Color.TRANSPARENT)) {
      this.color.set(backgroundColor.getValue());
    } else {
      this.color.set(pickedColor);
    }
  }

}
