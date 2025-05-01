package org.example.paint.tools.generalTools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.example.paint.controller.Background;
import org.example.paint.tools.Tool;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ConnectedSelectAndMove implements Tool {

  private enum State {IDLE, MOVING}
  private State state = State.IDLE;
  private boolean switchingToMoving = false;

  private int startX = -1, startY = -1;
  private int lastX = 0, lastY = 0, lastWidth = 0, lastHeight = 0;
  private WritableImage movedImage;

  @Override
  public void onPress(GraphicsContext g, MouseEvent e) {
    Image image = g.getCanvas().snapshot(null, null);
    PixelReader reader = image.getPixelReader();
    int selectX = (int) e.getX(), selectY = (int) e.getY();
    if (reader.getColor(selectX, selectY) == Background.getBackgroundColor()) {
      return;
    }

    boolean[][] checked = new boolean[(int)image.getWidth()][(int)image.getHeight()];
    Queue<int[]> queue = new ArrayDeque<>();
    List<int[]> region = new LinkedList<>();

    queue.add(new int[]{selectX, selectY});
    checked[selectX][selectY] = true;

  }
  @Override
  public void onRelease(GraphicsContext g, MouseEvent e, double size) {

  }

  @Override
  public void drawPreviewAt(GraphicsContext og, MouseEvent e, double size) {

  }

  public void onDrag(GraphicsContext g, MouseEvent e, double size, Color color, double opacity) {}
}
