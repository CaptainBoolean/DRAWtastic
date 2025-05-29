package org.example.paint.tools.generalTools;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.example.paint.tools.Tool;

import java.util.ArrayList;

public class PaintBucket implements Tool {

  //TODO fix not filling perfectly

  @Override
  public void onRelease(GraphicsContext g, GraphicsContext dg, MouseEvent e, double size, Color color) {
    Canvas canvas = g.getCanvas();

    int x = (int) e.getX();
    int y = (int) e.getY();
    int width = (int) canvas.getWidth();
    int height = (int) canvas.getHeight();

    if (x < 0 || y < 0 || x >= width || y >= height) return;

    ArrayList<int[]> toRecolor = SelectAreas.floodFillBackground(g, x, y);  // verwendet bestehende FloodFill

    dg.setFill(color);
    for (int[] pos : toRecolor) {
      int px = pos[0];
      int py = pos[1];
      dg.fillRect(px, py, 1, 1);
    }


  }

}
