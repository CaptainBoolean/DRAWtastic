package org.example.paint.controller;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;


public class Background {
  private static Color backgroundColor = Color.TRANSPARENT;
  private static Canvas canvas;

  Background(Canvas canvas) {
    this.canvas = canvas;
  }



  static void changeBackground(Color newColor) {
    //TODO implement

  }

  static void transparentBackground() {
    //TODO implement (make pixels that are background color Color.TRANSPARENT and set the background to transparent
  }

  public static Color getBackgroundColor() {
    return backgroundColor;
  }

}
