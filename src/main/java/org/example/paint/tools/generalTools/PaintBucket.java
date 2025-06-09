package org.example.paint.tools.generalTools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import org.example.paint.tools.Opaqueable;
import org.example.paint.tools.Tool;

import java.util.ArrayList;

public class PaintBucket implements Tool, Opaqueable {

  @Override
  public void onPress(GraphicsContext g, GraphicsContext dg, MouseEvent e, double size) {
    ArrayList<int[]> toRecolor = SelectAreas.floodFillArea(g, (int) e.getX(), (int) e.getY());

    for (int[] pos : toRecolor) {
      int px = pos[0];
      int py = pos[1];
      double margin = 3; //margin necessary to allow for a full fill - goes out of desired Bounds on repeated press
      dg.fillRect(px-margin/2, py-margin/2, margin, margin);
    }


  }

}
