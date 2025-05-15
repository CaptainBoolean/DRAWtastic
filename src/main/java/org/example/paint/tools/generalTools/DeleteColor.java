package org.example.paint.tools.generalTools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.example.paint.tools.Tool;

public class DeleteColor implements Tool {

  @Override
  public void onRelease(GraphicsContext g, GraphicsContext dg, MouseEvent e, double size, Color color) {
//TODO implement - seraches the whole canvas for the color selected on release and removes matching pixels (make them the background color)
    //maybe use pipette to get the color selected to eliminate code duplication
  }


}
