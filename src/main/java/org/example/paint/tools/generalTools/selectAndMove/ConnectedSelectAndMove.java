package org.example.paint.tools.generalTools.selectAndMove;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.example.paint.controller.Background;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ConnectedSelectAndMove extends SelectAndMove {


  //TODO not working at all - fix
  @Override
  public void onPress(GraphicsContext g, MouseEvent e) {
    if (mode == Mode.IDLE) {
      Color backgroundColor = Background.getBackgroundColor();
      Image image = g.getCanvas().snapshot(null, null);
      int width = (int) image.getWidth(), height = (int) image.getHeight();
      PixelReader reader = image.getPixelReader();
      int selectX = (int) e.getX(), selectY = (int) e.getY();
      if (reader.getColor(selectX, selectY) == backgroundColor) {
        return;
      }

      boolean[][] visited = new boolean[width][height];
      Queue<int[]> queue = new ArrayDeque<>();
      List<int[]> region = new LinkedList<>();

      queue.add(new int[]{selectX, selectY});
      visited[selectX][selectY] = true;

      int minX =  selectX, maxX = selectX;
      int minY = selectY, maxY = selectY;

      int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
      while (!queue.isEmpty()) {
        int[] point = queue.poll();
        int x = point[0], y = point[1];
        region.add(point);

        minX = Math.min(minX, x);
        maxX = Math.max(maxX, x);
        minY = Math.min(minY, y);
        maxY = Math.max(maxY, y);


        for (int[] direction : directions) {
          int nx = x + direction[0], ny = y + direction[1];
          if (nx >= 0 && nx < width && ny >= 0 && ny < height && !visited[nx][ny] && !reader.getColor(nx,ny).equals(backgroundColor)) {
            visited[nx][ny] = true;
            queue.add(new int[]{nx, ny});
          }
        }
      }

      int regionWidth = maxX - minX + 1, regionHeight = maxY - minY + 1;
      WritableImage regionImage = new WritableImage(regionWidth, regionHeight);
      PixelWriter regionPixelWriter = regionImage.getPixelWriter();

      for(int[] point : region) {
        int rx = point[0] - minX;
        int ry = point[1] - minY;
        regionPixelWriter.setColor(rx, ry, reader.getColor(rx,ry));
      }
      movedImage = regionImage;
      //todo ??
      mode = Mode.MOVING;

    } else if (mode == Mode.MOVING && movedImage != null) {
      super.printHere(g, e);
    }
  }

  @Override
  public void drawPreviewAt(GraphicsContext og, MouseEvent e, double size) {
    if (mode == Mode.MOVING && movedImage != null) {
      super.drawPreviewAt(og, e, size);
    }
  }

}
