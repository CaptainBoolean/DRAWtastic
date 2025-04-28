package org.example.paint.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import org.example.paint.graphics.picture.PictureEditor;
import org.example.paint.tools.SelectAndMove;
import org.example.paint.tools.Shapes.Rectangle;
import org.example.paint.tools.Tool;
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
  private Tool currentTool;
  private FileService fileService;

  public void initialize() {
    GraphicsContext g = canvas.getGraphicsContext2D();
    GraphicsContext og = overlayCanvas.getGraphicsContext2D();
    //toolManager = new ToolManager(canvas, overlayCanvas);
    //fileService = new FileService(canvas);
    //brushSize.textProperty().bindBidirectional(toolManager.brushSizeProperty(), new NumberStringConverter());
    //opacitySlider.accessibleTextProperty().bindBidirectional(opacityLabel.textProperty());
    colorPicker.setValue(Color.BLACK);

    opacitySlider.setMajorTickUnit(0.1);
    updateSliderColor();
    opacitySlider.valueProperty().addListener((observable, oldValue, newValue) -> updateSliderColor());
    colorPicker.valueProperty().addListener((observable, oldValue, newValue) -> updateSliderColor());

    currentTool = SizeOpacityAdjust(new RoundPen());

    //necessary to remove residual overlays
    canvas.setOnMouseEntered(e ->{
      og.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    });

    canvas.setOnMouseMoved(e -> {
      currentTool.drawPreviewAt(og, e, Double.parseDouble(brushSize.getText()));
    });

    selectAndMoveButton.setOnAction(e -> {
      currentTool = SizeOpacityAdjust(new SelectAndMove());
    });

    pencilButton.setOnAction(e -> {
      currentTool = SizeOpacityAdjust(new RoundPen());
    });

    markerButton.setOnAction(e -> {
      currentTool = SizeOpacityAdjust(new Marker());
    });

    eraserButton.setOnAction(e -> {
      if (currentTool instanceof RoundEraser) {
        currentTool = new SquareEraser();
      } else {
        currentTool = new RoundEraser();
      }
    });

    fountainPenButton.setOnAction(e -> {
      currentTool = SizeOpacityAdjust(new FountainPen());
    });

    blurButton.setOnAction(e -> {
      currentTool = SizeOpacityAdjust(new Blur());
    });

    paintBrushButton.setOnAction(e -> {
      currentTool = SizeOpacityAdjust(new PaintBrush());
    });

    rainbowPenButton.setOnAction(e -> {
      currentTool = SizeOpacityAdjust(new RainbowPen());
    });

    rectangleButton.setOnAction(e -> {
      currentTool = SizeOpacityAdjust(new Rectangle(colorPicker.getValue()));
    });

    loadPicture.setOnAction(e -> PictureEditor.loadImage(canvas));

    canvas.setOnMouseDragged(e -> {
      try {
        double size = Double.parseDouble(brushSize.getText());
        Color color = colorPicker.getValue();
        int opacitySteepness = 6; //TODO implement better progression of curve
        double opacity = (Math.exp(opacitySlider.getValue()*opacitySteepness)-1)/(Math.exp(opacitySteepness)-1);
        currentTool.onDrag(g, e, size, color, opacity);
        currentTool.drawPreviewAt(og, e, Double.parseDouble(brushSize.getText()));
      } catch (NumberFormatException ex) {
        System.out.println("Ungültige Pinselgröße!");
      }
    });

    canvas.setOnMousePressed(e -> {
      currentTool.onPress(canvas.getGraphicsContext2D(), e);
    });

    canvas.setOnMouseReleased(e -> {
      double size = Double.parseDouble(brushSize.getText());
      currentTool.onRelease(g, e, size);
    });

  }

  private Tool SizeOpacityAdjust(Tool tool) {
    double markerSizeRatio = 2;
    double opacityRatio = 0.2;
    if(currentTool instanceof Marker && !(tool instanceof Marker)) {
      brushSize.setText("" + Double.parseDouble(brushSize.getText()) / markerSizeRatio);
      opacitySlider.setValue(opacitySlider.getMax());
    }
    if(!(currentTool instanceof Marker) && tool instanceof Marker) {
      brushSize.setText("" + Double.parseDouble(brushSize.getText()) * markerSizeRatio);
      opacitySlider.setValue(opacitySlider.getMax()*opacityRatio);
    }
    if (tool instanceof Marker || tool instanceof FountainPen || tool instanceof RainbowPen || tool instanceof PaintBrush) {
      opacitySlider.setVisible(true);
      opacityLabel.setVisible(true);
    } else {
      opacitySlider.setVisible(false);
      opacityLabel.setVisible(false);
    }
    return tool;
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


  double getOpacity() {return opacitySlider.getValue();}
  double getOpacityMax() {return opacitySlider.getMax();}
  void setOpacity(double opacity) {opacitySlider.setValue(opacity);}



}


