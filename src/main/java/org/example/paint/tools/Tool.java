package org.example.paint.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public interface Tool {


  void onDrag(GraphicsContext g, MouseEvent e, double size, Color color, double opacity);
  default void onPress(GraphicsContext g, MouseEvent e){};
  void onRelease(GraphicsContext g, MouseEvent e, double size);

  void drawPreviewAt(GraphicsContext og, MouseEvent e, double size);


}
