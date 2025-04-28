package org.example.paint.controller;

import javafx.beans.property.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.example.paint.tools.Tool;
import org.example.paint.tools.pens.*;

public class ToolManager {
  private Tool currentTool;
  private final Canvas canvas;
  private final GraphicsContext g;
  private final Canvas overlayCanvas;
  private final GraphicsContext og;
  private final DoubleProperty brushSize = new SimpleDoubleProperty(5);
  private final DoubleProperty opacity = new SimpleDoubleProperty(1);
  private final BooleanProperty opacitySlider = new SimpleBooleanProperty();
  private final BooleanProperty opacityLabel = new SimpleBooleanProperty();
  private double markerSizeRatio = 2;
  private double markerOpacity = 0.1;
  private final ObjectProperty<Color> color = new SimpleObjectProperty<>(Color.BLACK);



  public ToolManager(Canvas canvas, Canvas overlayCanvas) {
    this.canvas = canvas;
    this.g = canvas.getGraphicsContext2D();
    this.overlayCanvas = overlayCanvas;
    this.og = overlayCanvas.getGraphicsContext2D();
  }

  public void changeTool(Tool newTool) {
    double opacityRatio = 0.2;

    if(currentTool instanceof Marker && !(newTool instanceof Marker)) {
      brushSize.setValue(brushSize.getValue() / markerSizeRatio);
      opacity.setValue(1);

    }
    if(!(currentTool instanceof Marker) && newTool instanceof Marker) {
      brushSize.setValue(brushSize.getValue() * markerSizeRatio);
      opacity.setValue(opacityRatio);
    }
    if (newTool instanceof Marker || newTool instanceof FountainPen || newTool instanceof RainbowPen || newTool instanceof PaintBrush) {
      opacitySlider.setValue(true);
      opacityLabel.setValue(true);
    } else {
      opacitySlider.setValue(false);
      opacityLabel.setValue(false);
    }

    if (newTool instanceof RoundEraser && !(currentTool instanceof RoundEraser)) {
      newTool = new SquareEraser();
    } else if (newTool instanceof RoundEraser) {
      newTool = new RoundEraser();
    }

    currentTool = newTool;
  }

  void onDrag(MouseEvent e) {
    try {
      double size = brushSize.getValue();
      int opacitySteepness = 6; //TODO implement better progression of curve
      double corrOpacity = (Math.exp(opacity.getValue()*opacitySteepness)-1)/(Math.exp(opacitySteepness)-1);
      currentTool.onDrag(g, e, size, color.getValue(), corrOpacity);
      currentTool.drawPreviewAt(og, e, brushSize.getValue());
    } catch (NumberFormatException ex) {
      System.out.println("Ungültige Pinselgröße!");
    }
  }

  void onPress(MouseEvent e) {
    currentTool.onPress(g, e);
  }

  void onRelease(MouseEvent e) {
    currentTool.onRelease(g, e, brushSize.getValue());
  }

  void onMove(MouseEvent e) {
    currentTool.drawPreviewAt(og, e, brushSize.getValue());
  }



  DoubleProperty brushSizeProperty() {
    return brushSize;
  }
  DoubleProperty brushOpacityProperty() {
    return opacity;
  }

  BooleanProperty opacitySliderProperty() {
    return opacitySlider;
  }

  BooleanProperty opacityLabelProperty() {
    return opacityLabel;
  }

  ObjectProperty<Color> colorProperty() {
    return color;
  }
}
