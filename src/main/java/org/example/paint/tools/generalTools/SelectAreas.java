package org.example.paint.tools.generalTools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import org.example.paint.controller.FileService;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class SelectAreas {

  /**
   * Returns an ArrayList of all connected pixels to the initially selected Pixel
   * @param g
   * @param x
   * @param y
   * @return
   */
  public static ArrayList<int[]> floodFill(GraphicsContext g, int x, int y) {
    //TODO implement - return a list of all relevant pixels to work with
    int[][] directions = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    // how to move
    int width = (int) g.getCanvas().getWidth();
    int height = (int) g.getCanvas().getHeight();
    WritableImage image = FileService.getTranspSnapshot(g.getCanvas());
    PixelReader pixelReader = image.getPixelReader();
    // PixelReader to pixel information
    boolean[][] read = new boolean[width][height];
    // array to check if pixel has been read
    ArrayList<int[]> listOP = new ArrayList<>();
    Color readC = pixelReader.getColor(x, y);
    // getting the color
    Queue<int[]> queue = new LinkedList<>();
    // creating a waiting list
    queue.offer(new int[]{x, y});
    // offer should be better than add to avoid conflict
    read[x][y] = true;
    while (!queue.isEmpty()) {
      // iterating over the queue
      int[] cur = queue.poll();
      int h = cur[0];
      int w = cur[1];
      listOP.add(new int[]{h, w});
      for(int[] direction : directions) {
        //iterating over all possible directions
        int newH = h + direction[0];
        int newW = w + direction[1];
        if(newH >= 0 && newW >= 0 && newH < width && newW < height && !read[newH][newW]) {
          // checking if already read
          Color color = pixelReader.getColor(newH, newW);
          if(!color.equals(Color.TRANSPARENT)) {
            //checking if it's the same color
            queue.offer(new int[]{newH, newW});
            read[newH][newW] = true;
          }
        }
      }
    }
    return listOP;
  }

  public static ArrayList<int[]> floodFillBackground(GraphicsContext g, int x, int y, int width, int height) {
    //TODO implement for paint bucket
    return null;
  }
}
