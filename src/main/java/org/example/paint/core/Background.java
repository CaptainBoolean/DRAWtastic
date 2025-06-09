package org.example.paint.core;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;


public class Background {
  private final Canvas backgroundCanvas;
  private static ImagePattern checkerboard;
  private static final ObjectProperty<Color> backgroundColor = new SimpleObjectProperty<>();
  private static final int TILE_SIZE = 15;

  Background(Canvas backgroundCanvas) {
    this.backgroundCanvas = backgroundCanvas;
    createCheckerboard();
    changeBackground(Color.WHITE);
  }


  void changeBackground(Color newColor) {
    GraphicsContext gc = backgroundCanvas.getGraphicsContext2D();
    gc.clearRect(0,0,backgroundCanvas.getWidth(), backgroundCanvas.getHeight());

    if (newColor.equals(Color.TRANSPARENT)) {
      gc.setFill(checkerboard);
      gc.fillRect(0, 0, backgroundCanvas.getWidth(), backgroundCanvas.getHeight());

    } else {
      gc.setFill(newColor);
      gc.fillRect(0, 0, backgroundCanvas.getWidth(), backgroundCanvas.getHeight());
    }
    backgroundColor.setValue(newColor);
  }

  void transparentBackground() {
    changeBackground(Color.TRANSPARENT);
  }

  private static void createCheckerboard() {
    Canvas patternCanvas = new Canvas(TILE_SIZE * 2, TILE_SIZE * 2);
    GraphicsContext gc = patternCanvas.getGraphicsContext2D();

    gc.setFill(Color.LIGHTGRAY);
    gc.fillRect(0, 0, TILE_SIZE * 2, TILE_SIZE * 2);

    gc.setFill(Color.GRAY);
    gc.fillRect(0, 0, TILE_SIZE, TILE_SIZE);
    gc.fillRect(TILE_SIZE, TILE_SIZE, TILE_SIZE, TILE_SIZE);

    Image patternImage = patternCanvas.snapshot(null, null);
    checkerboard = new ImagePattern(patternImage, 0, 0, TILE_SIZE * 2, TILE_SIZE * 2, false);
  }

  static ObjectProperty<Color> backgroundColorProperty() {return backgroundColor;}

}
