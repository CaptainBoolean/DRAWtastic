package org.example.paint.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.util.converter.NumberStringConverter;
import org.example.paint.tools.generalTools.*;
import org.example.paint.tools.generalTools.selectAndMove.BoxSelectAndMove;
import org.example.paint.tools.generalTools.selectAndMove.ConnectedSelectAndMove;
import org.example.paint.tools.pens.*;
import org.example.paint.tools.picture.*;
import org.example.paint.tools.shapes.*;


public class DrawtasticController {


  @FXML private Canvas canvas,drawCanvas, overlayCanvas, backgroundCanvas;
  @FXML private Group canvasGroup;
  @FXML private ColorPicker colorPicker;
  @FXML private ColorPicker backgroundColorPicker;
  @FXML private TextField brushSize;
  @FXML private Slider opacitySlider;

  private ToolManager toolManager;
  private Background background;
  private UndoRedo undoRedo;
  private final Scale canvasScale = new Scale(1.0, 1.0, 0, 0);

  public void initialize() {
    toolManager = new ToolManager(canvas, overlayCanvas, drawCanvas);
    background = new Background(backgroundCanvas);
    undoRedo = new UndoRedo(canvas);
    initBinds();
    colorPicker.setValue(Color.BLACK);
    toolManager.changeTool(new RoundPen());
    initListeners();

    canvasGroup.getTransforms().add(canvasScale);

    canvas.setOnMouseEntered(e ->{toolManager.onEnter(e);});
    canvas.setOnMouseMoved(e -> {toolManager.onMove(e);});
    canvas.setOnMouseDragged(e -> {toolManager.onDrag(e);});
    canvas.setOnMousePressed(e -> {toolManager.onPress(e);});
    canvas.setOnMouseReleased(e -> {toolManager.onRelease(e);undoRedo.saveState();});
  }

  private void initBinds() {
    brushSize.textProperty().bindBidirectional(ToolManager.brushSizeProperty(), new NumberStringConverter());
    opacitySlider.valueProperty().bindBidirectional(ToolManager.brushOpacityProperty());
    opacitySlider.visibleProperty().bindBidirectional(ToolManager.opacitySliderProperty());
    colorPicker.valueProperty().bindBidirectional(ToolManager.colorProperty());
    backgroundColorPicker.valueProperty().bindBidirectional(ToolManager.backgroundColorProperty());
  }

  private void initListeners() {
    opacitySlider.valueProperty().addListener((observable, oldValue, newValue) -> updateSliderColor());
    colorPicker.valueProperty().addListener((observable, oldValue, newValue) -> updateSliderColor());
    backgroundColorPicker.valueProperty().addListener((observable, oldValue, newValue) -> Background.changeBackground(newValue));
  }

  private void updateSliderColor() {
    Color selectedColor = colorPicker.getValue();
    double opacity = opacitySlider.getValue();

    Color colorWithOpacity = new Color(selectedColor.getRed(), selectedColor.getGreen(),
            selectedColor.getBlue(), opacity);

    opacitySlider.setStyle("-fx-background-color: rgba(" +
            (int)(colorWithOpacity.getRed() * 255) + "," +
            (int)(colorWithOpacity.getGreen() * 255) + "," +
            (int)(colorWithOpacity.getBlue() * 255) + "," +
            Math.min(colorWithOpacity.getOpacity(), 1) + ");"); //TODO match with implemented curve

    drawCanvas.setOpacity(opacity);
  }


  public void onSave() {FileService.save(canvas, backgroundColorPicker.getValue());}
  public void onExit() {Platform.exit();}

  public void undo() {undoRedo.undo();}
  public void redo() {undoRedo.redo();}

  public void zoomIn() {toolManager.zoom(1.1,canvas, canvasScale);}
  public void zoomOut() {toolManager.zoom(0.9,canvas, canvasScale);}

  public void newBoxSelectAndMove() {toolManager.changeTool(new BoxSelectAndMove());}
  public void newConnectedSelectAndMove() {toolManager.changeTool(new ConnectedSelectAndMove());}

  public void newEraser(){toolManager.changeTool(new RoundEraser());}
  public void newPipette() {toolManager.changeTool(new Pipette());}
  public void newPaintbucket() {toolManager.changeTool(new PaintBucket());}
  public void newRepaint() {toolManager.changeTool(new Repaint());}
  public void newRemoveColor() {toolManager.changeTool(new RemoveColorFromCanvas());}
  public void newDeleteConnectedLine() {toolManager.changeTool(new DeleteLine());}

  public void newPen() {toolManager.changeTool(new RoundPen());}
  public void newMarker() {toolManager.changeTool(new Marker());}
  public void newBlur() {toolManager.changeTool(new Blur());}
  public void newPaintBrush() {toolManager.changeTool(new PaintBrush());}
  public void newFountainPen() {toolManager.changeTool(new FountainPen());}
  public void newRainbowPen() {toolManager.changeTool(new RainbowPen());}

  public void newInsertPicture() {toolManager.changeTool(new PictureInsert());}
  public void newSepiaFilter() {toolManager.changeTool(new SepiaFilter());}
  public void newBlurFilter() {toolManager.changeTool(new BlurFilter());}
  public void newBlackAndWhiteFilter() {toolManager.changeTool(new BlackAndWhiteFilter());}
  public void newInvertFilter() {toolManager.changeTool(new InvertFilter());}
  public void newFlipVertical() {toolManager.changeTool(new FlipPicture("vertical"));}
  public void newFlipHorizontal() {toolManager.changeTool(new FlipPicture("horizontal"));}

  public void newRectangle() {toolManager.changeTool(new Rectangle());}
  public void newEllipse() {toolManager.changeTool(new Ellipse());}
  public void newCircle() {toolManager.changeTool(new Circle());}
  public void newTriangle() {toolManager.changeTool(new Triangle());}
  public void newStar() {toolManager.changeTool(new Star());}

  public void newTextfield() {toolManager.changeTool(new Textfield());}

  public void setTransparentBackground() {Background.transparentBackground();}

}


