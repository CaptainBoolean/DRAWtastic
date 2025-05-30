package org.example.paint.controller;

import javafx.beans.property.*;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import org.example.paint.tools.Opaqueable;
import org.example.paint.tools.Tool;
import org.example.paint.tools.generalTools.Pipette;
import org.example.paint.tools.pens.*;
import org.example.paint.tools.shapes.*;

public class ToolManager {
  private Tool currentTool;
  private Tool lastPen;
  private Tool lastShape;
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
  private static final ObjectProperty<Node> penButtonGraphic = new SimpleObjectProperty<>();
  private static final ObjectProperty<Node> eraserButtonGraphic = new SimpleObjectProperty<>();
  private static final ObjectProperty<Node> shapeButtonGraphic = new SimpleObjectProperty<>();


  ToolManager(Canvas canvas, Canvas overlayCanvas, Canvas drawCanvas) {
    this.canvas = canvas;
    this.g = canvas.getGraphicsContext2D();
    this.og = overlayCanvas.getGraphicsContext2D();
    this.dg = drawCanvas.getGraphicsContext2D();
  }

  void changeTool(Tool newTool) {

    checkIfMarker(newTool);
    checkOpacitySliderDisplay(newTool);
    newTool = checkEraserSwitch(newTool);
    newTool = checkPipette(newTool);
    checkIfIconChange(newTool);

    currentTool = newTool;
  }

  void lastPen() {
    changeTool(lastPen);
  }

  void lastShape() {
    changeTool(lastShape);
  }

  private void checkIfIconChange(Tool newTool) {
    ImageView imageView = null;

    if (newTool instanceof Pen) {
      switch (newTool) {
        case RoundPen roundPen ->
                imageView = new ImageView(new Image(getClass().getResource("/org/example/paint/buttonIcons/pen.png").toExternalForm()));
        case Marker marker ->
                imageView = new ImageView(new Image(getClass().getResource("/org/example/paint/buttonIcons/marker.png").toExternalForm()));
        case PaintBrush paintBrush ->
                imageView = new ImageView(new Image(getClass().getResource("/org/example/paint/buttonIcons/brush_fat.png").toExternalForm()));
        case FountainPen fountainPen ->
                imageView = new ImageView(new Image(getClass().getResource("/org/example/paint/buttonIcons/fountain_pen.png").toExternalForm()));
        case RainbowPen rainbowPen ->
                imageView = new ImageView(new Image(getClass().getResource("/org/example/paint/buttonIcons/brush_rainbow.png").toExternalForm()));
        default -> {
        }
      }
      formatImageView(imageView);
      penButtonGraphic.set(imageView);
      lastPen = newTool;
    }


    if (newTool instanceof RoundEraser) {
      imageView = new ImageView(new Image(getClass().getResource("/org/example/paint/buttonIcons/round_eraser.png").toExternalForm()));
      formatImageView(imageView);
      eraserButtonGraphic.set(imageView);
    } else  if (newTool instanceof SquareEraser) {
      imageView = new ImageView(new Image(getClass().getResource("/org/example/paint/buttonIcons/square_eraser.png").toExternalForm()));
      formatImageView(imageView);
      eraserButtonGraphic.set(imageView);
    } else {
      imageView = new ImageView(new Image(getClass().getResource("/org/example/paint/buttonIcons/round_eraser.png").toExternalForm()));
      formatImageView(imageView);
      eraserButtonGraphic.set(imageView);
    }


    if (lastShape == null) {
      lastShape = new Rectangle();
      imageView = new ImageView(new Image(getClass().getResource("/org/example/paint/buttonIcons/rectangle.png").toExternalForm()));
      formatImageView(imageView);
      shapeButtonGraphic.set(imageView);
    }
    if (newTool instanceof Shape) {
      switch (newTool) {
        case Circle circle ->
                imageView = new ImageView(new Image(getClass().getResource("/org/example/paint/buttonIcons/circle.png").toExternalForm()));
        case Ellipse ellipse ->
                imageView = new ImageView(new Image(getClass().getResource("/org/example/paint/buttonIcons/ellipse.png").toExternalForm()));
        case Rectangle rectangle ->
                imageView = new ImageView(new Image(getClass().getResource("/org/example/paint/buttonIcons/rectangle.png").toExternalForm()));
        case Star star ->
                imageView = new ImageView(new Image(getClass().getResource("/org/example/paint/buttonIcons/star.png").toExternalForm()));
        case Triangle triangle ->
                imageView = new ImageView(new Image(getClass().getResource("/org/example/paint/buttonIcons/triangle.png").toExternalForm()));
        default -> {
        }
      }
      formatImageView(imageView);
      shapeButtonGraphic.set(imageView);
      lastShape = newTool;
    }
  }

  private void formatImageView(ImageView imageView) {
    if (imageView != null) {
      imageView.setFitWidth(24);
      imageView.setFitHeight(24);
      imageView.setPreserveRatio(true);
    }
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
      currentTool.onDrag(g, dg, e, brushSize.getValue());
      currentTool.drawPreviewAt(og, e, brushSize.getValue());
    } catch (NumberFormatException ex) {
      System.out.println("Ungültige Pinselgröße!");
    }
  }

  void onPress(MouseEvent e) {
    dg.setFill(color.getValue());
    currentTool.onPress(g, dg, e, brushSize.getValue());
  }

  void onRelease(MouseEvent e) {
    currentTool.onRelease(g, dg, e, brushSize.getValue());
    copyToMainCanvas();
  }

  void onMove(MouseEvent e) {
    currentTool.drawPreviewAt(og, e, brushSize.getValue());
  }



  void onEnter(MouseEvent e) {og.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());}

  private void copyToMainCanvas() {
    g.drawImage(FileService.getTranspSnapshot(dg), 0, 0);
    dg.clearRect(0, 0, dg.getCanvas().getWidth(), dg.getCanvas().getHeight());
  }


  static DoubleProperty brushSizeProperty() {return brushSize;}
  static DoubleProperty brushOpacityProperty() {return opacity;}
  static BooleanProperty opacitySliderProperty() {return opacitySlider;}
  static ObjectProperty<Color> colorProperty() {return color;}
  static ObjectProperty<Color> backgroundColorProperty() {return backgroundColor;}
  static ObjectProperty<Node> penButtonGraphicProperty() {return penButtonGraphic;}
  static ObjectProperty<Node> eraserButtonGraphicProperty() {return eraserButtonGraphic;}
  static ObjectProperty<Node> shapeButtonGraphicProperty() {return shapeButtonGraphic;}

}
