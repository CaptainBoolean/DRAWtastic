package org.example.paint.tools.generalTools;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.example.paint.tools.Tool;

import java.util.ArrayList;

public class DeleteLine implements Tool {
  //löscht Pixel einer Farbe, aber hässlich TODO
  /*@Override
  public void onRelease(GraphicsContext g, GraphicsContext dg, MouseEvent e, double size, Color color) {
    Canvas canvas = g.getCanvas();
    // Farbe an Mausposition holen
    WritableImage snapshot = canvas.snapshot(new SnapshotParameters(), null);
    PixelReader reader = snapshot.getPixelReader();
    int x = (int) e.getX();
    int y = (int) e.getY();
    if (x < 0 || y < 0 || x >= snapshot.getWidth() || y >= snapshot.getHeight()) return;

    Color deleteColor = reader.getColor(x, y);
    if (deleteColor == null) return;

    // Neues Bild erzeugen mit entfernten Pixeln
    WritableImage output = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
    PixelWriter pw = output.getPixelWriter();

    for (int i = 0; i < canvas.getWidth(); i++) {
      for (int j = 0; j < canvas.getHeight(); j++) {
        Color co = reader.getColor(i, j);
        if (co.equals(deleteColor)) {
          pw.setColor(i, j, Color.TRANSPARENT);
        } else {
          pw.setColor(i, j, co);
        }
      }
    }

    // Canvas löschen und Bild neu zeichnen
    g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    g.drawImage(output, 0, 0);
  }*/

  //löscht alle zusammenhängenden Pixel

  @Override
  public void onRelease(GraphicsContext g, GraphicsContext dg, MouseEvent e, double size, Color color) {
    Canvas canvas = g.getCanvas();

    int x = (int) e.getX();
    int y = (int) e.getY();
    int width = (int) canvas.getWidth();
    int height = (int) canvas.getHeight();

    if (x < 0 || y < 0 || x >= width || y >= height) return;

    ArrayList<int[]> toDelete = SelectAreas.floodFillSelected(g, x, y);  // verwendet bestehende FloodFill

    for (int[] pos : toDelete) {
      int px = pos[0];
      int py = pos[1];
      g.clearRect(px, py, 1, 1);  // löscht Pixel durch Setzen auf transparent
    }
  }
}