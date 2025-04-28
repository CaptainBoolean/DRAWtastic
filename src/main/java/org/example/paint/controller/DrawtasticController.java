package org.example.paint.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.util.converter.NumberStringConverter;
import org.example.paint.graphics.picture.PictureEditor;
import org.example.paint.tools.SelectAndMove;
import org.example.paint.tools.Shapes.Rectangle;
import org.example.paint.tools.pens.*;


public class DrawtasticController {

  @FXML
  private Canvas canvas;

  @FXML
  private Canvas overlayCanvas;

  @FXML
  ColorPicker colorPicker;

  @FXML
  private Button selectAndMoveButton;

  @FXML
  private Button pencilButton;

  @FXML
  Slider opacitySlider;

  @FXML
  private Label opacityLabel;

  @FXML
  private Button markerButton;

  @FXML
  private TextField brushSize;

  @FXML
  private Button blurButton;

  @FXML
  private Button paintBrushButton;

  @FXML
  private Button eraserButton;

  @FXML
  private Button fountainPenButton;

  @FXML
  private Button rainbowPenButton;

  @FXML
  private Button rectangleButton;

  @FXML
  private Button loadPicture;
  private ToolManager toolManager;
  private FileService fileService;

  public void initialize() {
    GraphicsContext g = canvas.getGraphicsContext2D();
    GraphicsContext og = overlayCanvas.getGraphicsContext2D();
    toolManager = new ToolManager(canvas, overlayCanvas);
    fileService = new FileService(canvas);

    brushSize.textProperty().bindBidirectional(toolManager.brushSizeProperty(), new NumberStringConverter());
    opacitySlider.valueProperty().bindBidirectional(toolManager.brushOpacityProperty());
    opacitySlider.visibleProperty().bind(toolManager.opacitySliderProperty());
    opacityLabel.visibleProperty().bind(toolManager.opacityLabelProperty());
    colorPicker.valueProperty().bindBidirectional(toolManager.colorProperty());



    colorPicker.setValue(Color.BLACK);

    opacitySlider.setMajorTickUnit(0.1);
    updateSliderColor();
    opacitySlider.valueProperty().addListener((observable, oldValue, newValue) -> updateSliderColor());
    colorPicker.valueProperty().addListener((observable, oldValue, newValue) -> updateSliderColor());

    toolManager.changeTool(new RoundPen());

    //necessary to remove residual overlays
    canvas.setOnMouseEntered(e ->{
      og.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    });

    canvas.setOnMouseMoved(e -> {
      toolManager.onMove(e);
    });

    selectAndMoveButton.setOnAction(e -> {
      toolManager.changeTool(new SelectAndMove());
    });

    pencilButton.setOnAction(e -> {
      toolManager.changeTool(new RoundPen());
    });

    markerButton.setOnAction(e -> {
      toolManager.changeTool(new Marker());
    });

    eraserButton.setOnAction(e -> {
      toolManager.changeTool(new RoundEraser());
    });

    fountainPenButton.setOnAction(e -> {
      toolManager.changeTool(new FountainPen());
    });

    blurButton.setOnAction(e -> {
      toolManager.changeTool(new Blur());
    });

    paintBrushButton.setOnAction(e -> {
      toolManager.changeTool(new PaintBrush());
    });

    rainbowPenButton.setOnAction(e -> {
      toolManager.changeTool(new RainbowPen());
    });

    rectangleButton.setOnAction(e -> {
      toolManager.changeTool(new Rectangle());
    });

    loadPicture.setOnAction(e -> PictureEditor.loadImage(canvas));

    canvas.setOnMouseDragged(e -> {
      toolManager.onDrag(e);
    });

    canvas.setOnMousePressed(e -> {
      toolManager.onPress(e);
    });

    canvas.setOnMouseReleased(e -> {
      toolManager.onRelease(e);
    });

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
            Math.min(colorWithOpacity.getOpacity()*1.2, 1) + ");"); //TODO match with implemented curve
  }


  public void onSave() {
    FileService.save(canvas);
  }

  //TODO implement possibility to zoom and pan

  public void onExit() {
    Platform.exit();
  }

}


