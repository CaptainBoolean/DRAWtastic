package org.example.paint.controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;


public class Background {
  private static final ObjectProperty<Color> backgroundColor = new SimpleObjectProperty<>(Color.TRANSPARENT);
  private static Canvas backgroundCanvas;
  private static ImagePattern checkerboard;
  private static final int TILE_SIZE = 15;

  Background(Canvas backgroundCanvas) {
    Background.backgroundCanvas = backgroundCanvas;
    createCheckerboard();
    changeBackground(Color.WHITE);
  }

  /**
   * @param newColor
   * es gibt jetzt einen backgroundCanvas(hauptsächlich für Hintergrundfarbe) und einen canvas(auf dem wird gezeichnet etc)
   */
  static void changeBackground(Color newColor) {
    backgroundColor.set(newColor);
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

  static void transparentBackground() {
    changeBackground(Color.TRANSPARENT);
  }

  public static void createCheckerboard() {
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

  public static Color getBackgroundColor() {
    return backgroundColor.get();
  }

  static ObjectProperty<Color> backgroundColorProperty() {
    return backgroundColor;
  }

  public static void fillRectWithBackground(double x, double y, double width, double height) {
    GraphicsContext gc = backgroundCanvas.getGraphicsContext2D();
    gc.setFill(backgroundColor.get());
    gc.fillRect(x - width / 2, y - height / 2, width, height);
  }

  public static void fillEllipseWithBackground(double x, double y, double width, double height) {
    GraphicsContext gc = backgroundCanvas.getGraphicsContext2D();
    gc.setFill(backgroundColor.get());
    gc.fillOval(x - width / 2, y - height / 2, width, height);
  }

}
