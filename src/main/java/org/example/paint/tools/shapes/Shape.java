package org.example.paint.tools.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.example.paint.tools.Opaqueable;
import org.example.paint.tools.Tool;

public class Shape implements Tool, Opaqueable {


  @Override
  public void onDrag(GraphicsContext g, GraphicsContext dg, MouseEvent e, double size, Color color) {

  }

  @Override
  public void onRelease(GraphicsContext g, GraphicsContext dg, MouseEvent e, double size, Color color) {

  }

  @Override
  public void drawPreviewAt(GraphicsContext og, MouseEvent e, double size) {

    }
}
