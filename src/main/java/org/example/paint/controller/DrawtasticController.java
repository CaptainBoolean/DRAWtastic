package org.example.paint.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.util.converter.NumberStringConverter;
import org.example.paint.tools.generalTools.DeleteColor;
import org.example.paint.tools.generalTools.Pipette;
import org.example.paint.tools.generalTools.selectAndMove.BoxSelectAndMove;
import org.example.paint.tools.generalTools.selectAndMove.ConnectedSelectAndMove;
import org.example.paint.tools.pens.*;
import org.example.paint.tools.picture.PictureInsert;
import org.example.paint.tools.picture.RemoveRedEye;
import org.example.paint.tools.shapes.Rectangle;
import org.example.paint.tools.textField.Textfield;


public class DrawtasticController {


  @FXML private Canvas canvas, overlayCanvas;
  @FXML private ColorPicker colorPicker;
  @FXML private ColorPicker backgroundColorPicker;
  @FXML private TextField brushSize;
  @FXML private Label opacityLabel;
  @FXML private Slider opacitySlider;
  @FXML private Button undoButton, redoButton;
  @FXML private Button selectAndMoveButton, connectedSelectAndMoveButton, pipetteButton, transparentBackgroundButton, deleteColorButton;
  @FXML private SplitMenuButton pensButton;
  @FXML private Button eraserButton;
  @FXML private MenuItem penButton, markerButton, blurButton, paintBrushButton, fountainPenButton, rainbowPenButton;
  @FXML private Button rectangleButton;
  @FXML private Button insertPicture, removeRedEyeButton;
  @FXML private Button textFieldButton;

  private ToolManager toolManager;
  private Background background;
  private UndoRedo undoRedo;

  public void initialize() {
    toolManager = new ToolManager(canvas, overlayCanvas);
    background = new Background(canvas);
    undoRedo = new UndoRedo(canvas);
    initBinds();
    colorPicker.setValue(Color.BLACK);
    toolManager.changeTool(new RoundPen());
    initListeners();

    canvas.setOnMouseEntered(e ->{toolManager.onEnter(e);});
    canvas.setOnMouseMoved(e -> {toolManager.onMove(e);});
    canvas.setOnMouseDragged(e -> {toolManager.onDrag(e);});
    canvas.setOnMousePressed(e -> {toolManager.onPress(e);});
    canvas.setOnMouseReleased(e -> {toolManager.onRelease(e);undoRedo.saveState();});

    initButtons();
  }

  public void onSave() {
    FileService.save(canvas);}

  public void onExit() {Platform.exit();}

  private void initBinds() {
    brushSize.textProperty().bindBidirectional(toolManager.brushSizeProperty(), new NumberStringConverter());
    opacitySlider.valueProperty().bindBidirectional(toolManager.brushOpacityProperty());
    opacitySlider.visibleProperty().bind(toolManager.opacitySliderProperty());
    opacityLabel.visibleProperty().bind(toolManager.opacityLabelProperty());
    colorPicker.valueProperty().bindBidirectional(toolManager.colorProperty());
    backgroundColorPicker.valueProperty().bindBidirectional(background.backgroundColorProperty());
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
            Math.min(colorWithOpacity.getOpacity()*1.2, 1) + ");"); //TODO match with implemented curve
  }



  private void initButtons() {
    selectAndMoveButton.setOnAction(e -> {toolManager.changeTool(new BoxSelectAndMove());});
    connectedSelectAndMoveButton.setOnAction(e -> {toolManager.changeTool(new ConnectedSelectAndMove());});
    penButton.setOnAction(e -> {toolManager.changeTool(new RoundPen());});
    markerButton.setOnAction(e -> {toolManager.changeTool(new Marker());});
    eraserButton.setOnAction(e -> {toolManager.changeTool(new RoundEraser());});
    fountainPenButton.setOnAction(e -> {toolManager.changeTool(new FountainPen());});
    blurButton.setOnAction(e -> {toolManager.changeTool(new Blur());});
    paintBrushButton.setOnAction(e -> {toolManager.changeTool(new PaintBrush());});
    rainbowPenButton.setOnAction(e -> {toolManager.changeTool(new RainbowPen());});
    rectangleButton.setOnAction(e -> {toolManager.changeTool(new Rectangle());});
    insertPicture.setOnAction(e -> toolManager.changeTool(new PictureInsert()));
    pipetteButton.setOnAction(e -> {toolManager.changeTool(new Pipette());});
    transparentBackgroundButton.setOnAction(e -> {Background.transparentBackground();});
    deleteColorButton.setOnAction(e -> {toolManager.changeTool(new DeleteColor());});
    removeRedEyeButton.setOnAction(e -> toolManager.changeTool(new RemoveRedEye()));
    pensButton.setOnAction(e -> {toolManager.changeTool(new RoundPen());});
    textFieldButton.setOnAction(e -> {toolManager.changeTool(new Textfield());});
    undoButton.setOnAction(e -> undoRedo.undo());
    redoButton.setOnAction(e -> undoRedo.redo());
  }

}


