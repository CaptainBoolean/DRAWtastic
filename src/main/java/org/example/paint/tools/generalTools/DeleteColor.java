package org.example.paint.tools.generalTools;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.example.paint.tools.Tool;

public class DeleteColor implements Tool {
  private final Canvas canvas;

  public DeleteColor(Canvas canvas) {
    this.canvas = canvas;
  }

  @Override
  public void onRelease(GraphicsContext g, MouseEvent e, double size) {
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
        Color color = reader.getColor(i, j);
        if (color.equals(deleteColor)) {
          pw.setColor(i, j, Color.TRANSPARENT);
        } else {
          pw.setColor(i, j, color);
        }
      }
    }

    // Canvas löschen und Bild neu zeichnen
    g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    g.drawImage(output, 0, 0);
  }
}
