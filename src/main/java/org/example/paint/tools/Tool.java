package org.example.paint.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

public interface Tool {


  default void onDrag(GraphicsContext g, GraphicsContext dg, MouseEvent e, double size){};
  default void onPress(GraphicsContext g, GraphicsContext dg, MouseEvent e, double size){};
  default void onRelease(GraphicsContext g, GraphicsContext dg, MouseEvent e, double size){};
  default void drawPreviewAt(GraphicsContext og, MouseEvent e, double size){};


}
