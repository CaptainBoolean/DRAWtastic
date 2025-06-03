package org.example.paint.tools.generalTools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import org.example.paint.core.FileService;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class SelectAreas {
  static int[][] directions = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
  /**
   * Returns an ArrayList of all connected pixels to the initially selected Pixel
   * @param g
   * @param x
   * @param y
   * @return ArrayList<int[]>
   */
  private static ArrayList<int[]> floodFill(GraphicsContext g, int x, int y, PixelMatch condition) {
    int width = (int) g.getCanvas().getWidth();
    int height = (int) g.getCanvas().getHeight();
    WritableImage image = FileService.getTranspSnapshot(g);
    PixelReader pixelReader = image.getPixelReader();
    // PixelReader to pixel information
    boolean[][] read = new boolean[width][height];
    // array to check if pixel has been read
    ArrayList<int[]> listOP = new ArrayList<>();
    Color readC = pixelReader.getColor(x, y);
    // getting the color
    if(!condition.matches(readC)){
      return listOP;
      //returns an empty list if the wrong pixel was selected
    }
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
          if(condition.matches(color)) {
            //checking if it's the same color
            queue.offer(new int[]{newH, newW});
            read[newH][newW] = true;
          }
        }
      }
    }
    return listOP;
  }
  /**
   * Returns an ArrayList of all connected pixels that are transparent but only if the clicked pixel is aswell
   * @param g
   * @param x
   * @param y
   * @return ArrayList<int[]>
   */
  public static ArrayList<int[]> floodFillArea(GraphicsContext g, int x, int y) {
    Color selectedColor =  FileService.getColorAtPosition(g, x,y);
    return floodFill(g, x, y, color -> color.equals(selectedColor));
  }

  public static ArrayList<int[]> floodFillSelected(GraphicsContext g, int x, int y) {
    return floodFill(g, x, y, color -> !color.equals(Color.TRANSPARENT));
  }

  public interface PixelMatch{
    boolean matches(Color color);
  }
}
