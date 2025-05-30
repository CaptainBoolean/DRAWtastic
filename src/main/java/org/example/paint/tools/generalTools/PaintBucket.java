package org.example.paint.tools.generalTools;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import org.example.paint.tools.Opaqueable;
import org.example.paint.tools.Tool;

import java.util.ArrayList;

public class PaintBucket implements Tool, Opaqueable {

  @Override
  public void onRelease(GraphicsContext g, GraphicsContext dg, MouseEvent e, double size) {
    Canvas canvas = g.getCanvas();

    int x = (int) e.getX();
    int y = (int) e.getY();
    int width = (int) canvas.getWidth();
    int height = (int) canvas.getHeight();

    if (x < 0 || y < 0 || x >= width || y >= height) return;

    ArrayList<int[]> toRecolor = SelectAreas.floodFillArea(g, x, y);  // verwendet bestehende FloodFill

    for (int[] pos : toRecolor) {
      int px = pos[0];
      int py = pos[1];
      double margin = 3;
      dg.fillRect(px-margin/2, py-margin/2, margin, margin);
    }


  }

}
