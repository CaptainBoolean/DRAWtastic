package org.example.paint.controller;

import javafx.animation.AnimationTimer;
import javafx.beans.property.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import org.example.paint.tools.Opaqueable;
import org.example.paint.tools.Tool;
import org.example.paint.tools.generalTools.Pipette;
import org.example.paint.tools.pens.Marker;
import org.example.paint.tools.pens.RoundEraser;
import org.example.paint.tools.pens.SquareEraser;

public class ToolManager {
  private Tool currentTool;
  private final Canvas canvas;
  private final GraphicsContext g;
  private final GraphicsContext og;
  private final GraphicsContext dg;
  private static final DoubleProperty brushSize = new SimpleDoubleProperty(8);
  private static final DoubleProperty opacity = new SimpleDoubleProperty(1);
  private static final BooleanProperty opacitySlider = new SimpleBooleanProperty();
  private static final double markerSizeRatio = 2;
  private static final double markerOpacity = 0.3;
  private static final ObjectProperty<Color> color = new SimpleObjectProperty<>(Color.BLACK);
  private static final ObjectProperty<Color> backgroundColor = new SimpleObjectProperty<>(Color.WHITE);
  private MouseEvent lastMouseEvent;



  public ToolManager(Canvas canvas, Canvas overlayCanvas, Canvas drawCanvas) {
    this.canvas = canvas;
    this.g = canvas.getGraphicsContext2D();
    this.og = overlayCanvas.getGraphicsContext2D();
    this.dg = drawCanvas.getGraphicsContext2D();
    previewTimer.start();
  }

  public void changeTool(Tool newTool) {
    checkIfMarker(newTool);
    checkOpacitySliderDisplay(newTool);
    newTool = checkEraserSwitch(newTool);
    newTool = checkPipette(newTool);

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
    opacitySlider.setValue(newTool instanceof Opaqueable);
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
      return new Pipette(color, backgroundColor);
    }
    return newTool;
  }

  public void zoom(double zoomFactor, Canvas canvas, Scale canvasScale) {
    double canvasCenterX = canvas.getWidth() / 2;
    double canvasCenterY = canvas.getHeight() / 2;

    double newScale = canvasScale.getX() * zoomFactor;
    newScale = Math.max(0.2, Math.min(newScale, 2.0));

    canvasScale.setX(newScale);
    canvasScale.setY(newScale);

    canvasScale.setPivotX(canvasCenterX);
    canvasScale.setPivotY(canvasCenterY);
  }


  void onDrag(MouseEvent e) {
    try {
      currentTool.onDrag(g, dg, e, brushSize.getValue(), color.getValue());
      currentTool.drawPreviewAt(og, e, brushSize.getValue());
    } catch (NumberFormatException ex) {
      System.out.println("Ungültige Pinselgröße!");
    }
  }

  void onPress(MouseEvent e) {
    currentTool.onPress(g, dg, e, brushSize.getValue(), color.getValue());
  }

  void onRelease(MouseEvent e) {
    currentTool.onRelease(g, dg, e, brushSize.getValue(),color.getValue());
    copyToMainCanvas();
  }

  void onMove(MouseEvent e) {
    currentTool.drawPreviewAt(og, e, brushSize.getValue());
  }

  AnimationTimer previewTimer = new AnimationTimer() {
    private long lastUpdate = 0;
    private double prevX = -1;
    private double prevY = -1;

    @Override
    public void handle(long now) {
      if (now - lastUpdate < 33_000_000) return; // ~30 FPS cap
      lastUpdate = now;

      if (lastMouseEvent != null) {
        double currX = lastMouseEvent.getX();
        double currY = lastMouseEvent.getY();

        if (currX != prevX || currY != prevY) {
          onMove(lastMouseEvent);
          prevX = currX;
          prevY = currY;
        }
      }
    }
  };

  void onEnter(MouseEvent e) {og.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());}

  private void copyToMainCanvas() {
    g.drawImage(FileService.getTranspSnapshot(dg.getCanvas()), 0, 0);
    dg.clearRect(0, 0, dg.getCanvas().getWidth(), dg.getCanvas().getHeight());
  }


  static DoubleProperty brushSizeProperty() {return brushSize;}
  static DoubleProperty brushOpacityProperty() {return opacity;}
  static BooleanProperty opacitySliderProperty() {return opacitySlider;}
  static ObjectProperty<Color> colorProperty() {return color;}
  static ObjectProperty<Color> backgroundColorProperty() {return backgroundColor;}

  public static Color getDrawColor() {return colorProperty().getValue();}

}
