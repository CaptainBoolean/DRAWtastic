package org.example.paint.core;

import javafx.beans.property.*;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.example.paint.tools.Opaqueable;
import org.example.paint.tools.Tool;
import org.example.paint.tools.generalTools.Pipette;
import org.example.paint.tools.pens.*;
import org.example.paint.tools.picture.PictureInsert;
import org.example.paint.tools.shapes.*;

/**
 * This is a manager for changing tools which performs all specific tasks that need to be performed when a specific tool is selected.
 * It also calls to the tools to perform the 4 core methods of every tool.
 */
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

  /**
   * Constructs a ToolManager allowing it to save all the canvas it needs to access.
   * @param canvas The main canvas
   * @param overlayCanvas The canvas for previews
   * @param drawCanvas The canvas to draw on - it gets merged onto the main canvas after each action
   */
  ToolManager(Canvas canvas, Canvas overlayCanvas, Canvas drawCanvas) {
    this.canvas = canvas;
    this.g = canvas.getGraphicsContext2D();
    this.og = overlayCanvas.getGraphicsContext2D();
    this.dg = drawCanvas.getGraphicsContext2D();
  }

  /**
   * Allows to change the current tool to a new one.
   * @param newTool The new tool to take as the current one
   */
  void changeTool(Tool newTool) {
    checkIfMarker(newTool);
    checkOpacitySliderDisplay(newTool);
    checkPictureInsert(newTool);
    newTool = checkEraserSwitch(newTool);
    newTool = checkPipette(newTool);
    checkIfIconChange(newTool);

    currentTool = newTool;
  }

  /**
   * Allows to change the current tool to the last selected Pen
   */
  void lastPen() {
    changeTool(lastPen);
  }

  /**
   * Allows to change the current tool to the last selected Shape
   */
  void lastShape() {
    changeTool(lastShape);
  }

  /**
   * Checks if any UI Icons need to be changed when changing the tool and does so if needed
   * @param newTool Tool to apply the check to
   */
  private void checkIfIconChange(Tool newTool) throws NullPointerException {
    ImageView imageView = null;

    try {
      if (newTool instanceof Pen) {
        switch (newTool) {
          case Marker marker ->
                  imageView = new ImageView(new Image(getClass().getResource("/org/example/paint/buttonIcons/marker.png").toExternalForm()));
          case RoundPen roundPen ->
                  imageView = new ImageView(new Image(getClass().getResource("/org/example/paint/buttonIcons/pen.png").toExternalForm()));
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
    } catch (NullPointerException e) {
      throw new NullPointerException("An icon could not be found - check all paths");
    }
  }

  /**
   * Formats a given Imageview to the Height and Width of all Icons
   * @param imageView The ImageView to be formatted
   */
  private void formatImageView(ImageView imageView) {
    if (imageView != null) {
      imageView.setFitWidth(24);
      imageView.setFitHeight(24);
      imageView.setPreserveRatio(true);
    }
  }

  /**
   * Checks if the newTool is a marker and adjusts the size and opacity accordingly
   * @param newTool Tool to check if it is a marker
   */
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

  /**
   * Checks if the newTool implements Opaqueable and sets the display value of the slider.
   * @param newTool Tool to check if it is Opaqueable
   */
  private void checkOpacitySliderDisplay(Tool newTool) {
    opacitySlider.setValue(newTool instanceof Opaqueable);
  }

  /**
   * Checks if the newTool is an Eraser and switches the types of Erasers
   * @param newTool Tool to check if it is an Eraser and which type it is
   * @return The newTool if it is not an Eraser,
   * a RoundEraser if the previous Tool was not an eraser of any Type
   * or a SquareEraser if the previous Tool was a round Eraser
   */
  private Tool checkEraserSwitch(Tool newTool) {
    if (newTool instanceof RoundEraser && !(currentTool instanceof RoundEraser)) {
      return new RoundEraser();
    } else if (newTool instanceof RoundEraser) {
      return new SquareEraser();
    }
    return newTool;
  }

  /**
   * Checks if the newTool is a Pipette and constructs the Pipette with the proper parameters
   * @param newTool The Tool to check if it is a Pipette
   * @return The newTool if it is not a Pipette
   * or a new Pipette with the correct Parameters while discarding the previously constructed Pipette
   */
  private Tool checkPipette(Tool newTool) {
    if (newTool instanceof Pipette) {
      return new Pipette(color, backgroundColor);
    }
    return newTool;
  }

  /**
   * Checks if the newTool is a PictureInsert and sets the Opacity to 1 to allow for Pictures to be inserted with full opaqueness if not changed.
   * @param newTool The Tool to check if it is a PictureInsert
   */
  private void checkPictureInsert(Tool newTool) {
    if (newTool instanceof PictureInsert) {
      opacity.setValue(1);
    }
  }

  /**
   * Calls the onDrag method for the specific Tool and the drawPreviewAt
   * @param e The MouseEvent that triggered this action
   */
  void onDrag(MouseEvent e) {
      currentTool.onDrag(g, dg, e, brushSize.getValue());
      currentTool.drawPreviewAt(og, e, brushSize.getValue());
  }

  /**
   * Sets the fill color for the drawingCanvas to the selected color and alls the onPress method for the specific Tool.
   * @param e The MouseEvent that triggered this action
   */
  void onPress(MouseEvent e) {
    dg.setFill(color.getValue());
    currentTool.onPress(g, dg, e, brushSize.getValue());
  }

  /**
   * Calls the onRelease method for the specific Tool and calls {@link #copyToMainCanvas()}
   * @param e The MouseEvent that triggered this action
   */
  void onRelease(MouseEvent e) {
    currentTool.onRelease(g, dg, e, brushSize.getValue());
    copyToMainCanvas();
  }

  /**
   * Calls the drawPreviewAt method for the specific Tool
   * @param e The MouseEvent that triggered this action
   */
  void onMove(MouseEvent e) {
    currentTool.drawPreviewAt(og, e, brushSize.getValue());
  }

  /**
   * Clears the entire previewCanvas when the mouse enters the canvas to remove residual preview drawings
   * @param e The MouseEvent that triggered this action
   */
  void onEnter(MouseEvent e) {og.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());}

  /**
   * Takes the drawing from the drawingCanvas and copies it to the main canvas with the specified opacity
   */
  private void copyToMainCanvas() {
    g.drawImage(FileService.getTranspSnapshot(dg), 0, 0);
    dg.clearRect(0, 0, dg.getCanvas().getWidth(), dg.getCanvas().getHeight());
  }


  //the following getters are necessary to initialize the binds with the controller
  static DoubleProperty brushSizeProperty() {return brushSize;}
  static DoubleProperty brushOpacityProperty() {return opacity;}
  static BooleanProperty opacitySliderProperty() {return opacitySlider;}
  static ObjectProperty<Color> colorProperty() {return color;}
  static ObjectProperty<Color> backgroundColorProperty() {return backgroundColor;}
  static ObjectProperty<Node> penButtonGraphicProperty() {return penButtonGraphic;}
  static ObjectProperty<Node> eraserButtonGraphicProperty() {return eraserButtonGraphic;}
  static ObjectProperty<Node> shapeButtonGraphicProperty() {return shapeButtonGraphic;}

}
