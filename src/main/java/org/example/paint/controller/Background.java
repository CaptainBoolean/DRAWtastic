package org.example.paint.controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;


public class Background {
  private static final ObjectProperty<Color> backgroundColor = new SimpleObjectProperty<>(Color.TRANSPARENT);
  private static Canvas canvas;

  Background(Canvas canvas) {
    this.canvas = canvas;
  }



  static void changeBackground(Color newColor) {
    //TODO implement - listener already calling this method hen color picker is changed

  }

  static void transparentBackground() {
    //TODO implement (make pixels that are background color Color.TRANSPARENT and set the background to transparent
  }

  public static Color getBackgroundColor() {
    return backgroundColor.get();
  }

  static ObjectProperty<Color> backgroundColorProperty() {return backgroundColor;}

}
