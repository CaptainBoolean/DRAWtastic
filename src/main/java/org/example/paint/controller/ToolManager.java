package org.example.paint.controller;

import javafx.beans.property.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.example.paint.tools.Tool;
import org.example.paint.tools.generalTools.PaintBucket;
import org.example.paint.tools.generalTools.Pipette;
import org.example.paint.tools.pens.*;

public class ToolManager {
  private Tool currentTool;
  private final Canvas canvas;
  private final GraphicsContext g;
  private final Canvas overlayCanvas;
  private final GraphicsContext og;
  private static final DoubleProperty brushSize = new SimpleDoubleProperty(8);
  private static final DoubleProperty opacity = new SimpleDoubleProperty(1);
  private static final BooleanProperty opacitySlider = new SimpleBooleanProperty();
  private static final BooleanProperty opacityLabel = new SimpleBooleanProperty();
  private static final double markerSizeRatio = 2;
  private static final double markerOpacity = 0.3;
  private static final ObjectProperty<Color> color = new SimpleObjectProperty<>(Color.BLACK);



  public ToolManager(Canvas canvas, Canvas overlayCanvas) {
    this.canvas = canvas;
    this.g = canvas.getGraphicsContext2D();
    this.overlayCanvas = overlayCanvas;
    this.og = overlayCanvas.getGraphicsContext2D();
  }

  public void changeTool(Tool newTool) {
    checkIfMarker(newTool);
    checkOpacitySliderDisplay(newTool);
    newTool = checkEraserSwitch(newTool);
    newTool = checkPipette(newTool);
    newTool = checkPaintBucket(newTool);

    currentTool = newTool;
  }

  private void checkIfMarker(Tool newTool) {
    if(currentTool instanceof Marker && !(newTool instanceof Marker)) {
      brushSize.setValue(brushSize.getValue() / markerSizeRatio);
      opacity.setValue(1);
    }
    if(!(currentTool instanceof Marker) && newTool instanceof Marker) {
      brushSize.setValue(brushSize.getValue() * markerSizeRatio);
      opacity.setValue(markerOpacity);
    }
  }

  private void checkOpacitySliderDisplay(Tool newTool) {
    if (newTool instanceof Marker || newTool instanceof FountainPen || newTool instanceof RainbowPen || newTool instanceof PaintBrush) {
      opacitySlider.setValue(true);
      opacityLabel.setValue(true);
    } else {
      opacitySlider.setValue(false);
      opacityLabel.setValue(false);
    }
  }

  private Tool checkEraserSwitch(Tool newTool) {
    if (newTool instanceof RoundEraser && !(currentTool instanceof RoundEraser)) {
      return new RoundEraser();
    } else if (newTool instanceof RoundEraser) {
      return new SquareEraser();
    }
    return newTool;
  }

  private Tool checkPipette(Tool newTool) {
    if (newTool instanceof Pipette) {
      return new Pipette(color);
    }
    return newTool;
  }

  private Tool checkPaintBucket(Tool newTool) {
    if (newTool instanceof PaintBucket) {
      return new PaintBucket(color);
    }
    return newTool;
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

  void onEnter(MouseEvent e) {og.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());}


  static DoubleProperty brushSizeProperty() {return brushSize;}
  static DoubleProperty brushOpacityProperty() {return opacity;}
  static BooleanProperty opacitySliderProperty() {return opacitySlider;}
  static BooleanProperty opacityLabelProperty() {return opacityLabel;}
  static ObjectProperty<Color> colorProperty() {return color;}

  public static Color getDrawColor() {return colorProperty().getValue();}

}
