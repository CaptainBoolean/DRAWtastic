package org.example.paint.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public interface Tool {


  default void onDrag(GraphicsContext g, GraphicsContext dg, MouseEvent e, double size, Color color, double opacity){};
  default void onPress(GraphicsContext g, GraphicsContext dg, MouseEvent e, double size, Color color, double opacity){};
  default void onRelease(GraphicsContext g, GraphicsContext dg, MouseEvent e, double size, Color color, double opacity){};
  default void drawPreviewAt(GraphicsContext og, MouseEvent e, double size){};


}
