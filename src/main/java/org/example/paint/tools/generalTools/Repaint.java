package org.example.paint.tools.generalTools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import org.example.paint.tools.Opaqueable;
import org.example.paint.tools.Tool;

import java.util.ArrayList;

public class Repaint implements Tool, Opaqueable {

  @Override
  public void onRelease(GraphicsContext g, GraphicsContext dg, MouseEvent e, double size) {

    ArrayList<int[]> toRecolor = SelectAreas.floodFillSelected(g, (int) e.getX(), (int) e.getY());  // verwendet bestehende FloodFill

    for (int[] pos : toRecolor) {
      int px = pos[0];
      int py = pos[1];
      dg.fillRect(px, py, 1.1, 1.1); //margin to color in all relevant pixels
    }
  }
}
