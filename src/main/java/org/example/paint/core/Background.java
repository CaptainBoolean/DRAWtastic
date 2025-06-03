package org.example.paint.core;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;


public class Background {
  private Canvas backgroundCanvas;
  private static ImagePattern checkerboard;
  private static final int TILE_SIZE = 15;

  Background(Canvas backgroundCanvas) {
    this.backgroundCanvas = backgroundCanvas;
    createCheckerboard();
    changeBackground(Color.WHITE);
  }

  /**
   * @param newColor
   * es gibt jetzt einen backgroundCanvas(hauptsächlich für Hintergrundfarbe) und einen canvas(auf dem wird gezeichnet etc)
   */
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


}
