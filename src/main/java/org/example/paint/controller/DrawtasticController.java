package org.example.paint.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.util.converter.NumberStringConverter;
import org.example.paint.tools.generalTools.DeleteColor;
import org.example.paint.tools.generalTools.PaintBucket;
import org.example.paint.tools.generalTools.Pipette;
import org.example.paint.tools.generalTools.Repaint;
import org.example.paint.tools.generalTools.selectAndMove.BoxSelectAndMove;
import org.example.paint.tools.generalTools.selectAndMove.ConnectedSelectAndMove;
import org.example.paint.tools.pens.*;
import org.example.paint.tools.picture.BlackAndWhiteFilter;
import org.example.paint.tools.picture.BlurFilter;
import org.example.paint.tools.picture.PictureInsert;
import org.example.paint.tools.shapes.*;


public class DrawtasticController {


  @FXML private Canvas canvas, overlayCanvas;
  @FXML private Group canvasGroup;
  @FXML private Button zoomInButton;
  @FXML private Button zoomOutButton;
  @FXML private ColorPicker colorPicker;
  @FXML private ColorPicker backgroundColorPicker;
  @FXML private TextField brushSize;
  @FXML private Label opacityLabel;
  @FXML private Slider opacitySlider;
  @FXML private Button undoButton, redoButton;
  @FXML private Button selectAndMoveButton, connectedSelectAndMoveButton, pipetteButton, transparentBackgroundButton, deleteColorButton, repaintButton, paintBucketButton;
  @FXML private SplitMenuButton pensButton;
  @FXML private Button eraserButton;
  @FXML private MenuItem penButton, markerButton, blurButton, paintBrushButton, fountainPenButton, rainbowPenButton;
  @FXML private SplitMenuButton insertPicture;
  @FXML private MenuItem  blurFilterButton, blackAndWhiteFilterButton;
  @FXML private Button textFieldButton;
  @FXML private SplitMenuButton lineButton;
  @FXML private MenuItem rectangleButton, circleButton, ellipseButton, starButton, arrowButton;
  @FXML private Button encryptButton, decryptButton;

  private ToolManager toolManager;
  private Background background;
  private UndoRedo undoRedo;
  private Encrypter encrypter;
  private final Scale canvasScale = new Scale(1.0, 1.0, 0, 0);

  public void initialize() {
    toolManager = new ToolManager(canvas, overlayCanvas);
    background = new Background(canvas);
    undoRedo = new UndoRedo(canvas);
    encrypter = new Encrypter(canvas);
    initBinds();
    colorPicker.setValue(Color.BLACK);
    toolManager.changeTool(new RoundPen());
    initListeners();

    // Canvas Color & Scale init
    Background.changeBackground(Color.WHITE);
    canvasGroup.getTransforms().add(canvasScale);

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
    brushSize.textProperty().bindBidirectional(ToolManager.brushSizeProperty(), new NumberStringConverter());
    opacitySlider.valueProperty().bindBidirectional(ToolManager.brushOpacityProperty());
    opacitySlider.visibleProperty().bindBidirectional(ToolManager.opacitySliderProperty());
    opacityLabel.visibleProperty().bindBidirectional(ToolManager.opacityLabelProperty());
    colorPicker.valueProperty().bindBidirectional(ToolManager.colorProperty());
    backgroundColorPicker.valueProperty().bindBidirectional(Background.backgroundColorProperty());
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



  //TODO sort buttons in groups and comment on them
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
    insertPicture.setOnAction(e -> toolManager.changeTool(new PictureInsert()));
    pipetteButton.setOnAction(e -> {toolManager.changeTool(new Pipette());});
    transparentBackgroundButton.setOnAction(e -> {Background.transparentBackground();});
    deleteColorButton.setOnAction(e -> {toolManager.changeTool(new DeleteColor());});
    pensButton.setOnAction(e -> {toolManager.changeTool(new RoundPen());});
    textFieldButton.setOnAction(e -> {toolManager.changeTool(new Textfield());});
    undoButton.setOnAction(e -> undoRedo.undo());
    redoButton.setOnAction(e -> undoRedo.redo());
    repaintButton.setOnAction(e -> {toolManager.changeTool(new Repaint());});
    paintBucketButton.setOnAction(e -> {toolManager.changeTool(new PaintBucket());});
    blurFilterButton.setOnAction(e-> {toolManager.changeTool(new BlurFilter());});
    blackAndWhiteFilterButton.setOnAction(e -> {toolManager.changeTool(new BlackAndWhiteFilter());});
    lineButton.setOnAction(e->{toolManager.changeTool(new Line());});
    rectangleButton.setOnAction(e->{toolManager.changeTool(new Rectangle());});
    circleButton.setOnAction(e->{toolManager.changeTool(new Circle());});
    ellipseButton.setOnAction(e->toolManager.changeTool(new Ellipse()));
    starButton.setOnAction((e->{toolManager.changeTool(new Star());}));
    arrowButton.setOnAction(e->{toolManager.changeTool(new Arrow());});
    encryptButton.setOnAction(e->encrypter.encrypte());
    decryptButton.setOnAction(e->encrypter.decrypte());
    zoomInButton.setOnAction(e -> toolManager.zoom(1.1,canvas, canvasScale));
    zoomOutButton.setOnAction(e -> toolManager.zoom(0.9,canvas, canvasScale));
  }

}


