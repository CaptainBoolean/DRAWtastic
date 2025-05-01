package org.example.paint.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.util.converter.NumberStringConverter;
import org.example.paint.graphics.picture.PictureEditor;
import org.example.paint.tools.ConnectedSelectAndMove;
import org.example.paint.tools.SelectAndMove;
import org.example.paint.tools.Shapes.Rectangle;
import org.example.paint.tools.TextField.Textfield;
import org.example.paint.tools.pens.*;


public class DrawtasticController {

  @FXML private Canvas canvas, overlayCanvas;
  @FXML private ColorPicker colorPicker;
  @FXML private TextField brushSize;
  @FXML private Label opacityLabel;
  @FXML private Slider opacitySlider;
  @FXML private Button selectAndMoveButton, connectedSelectAndMoveButton;
  @FXML private Button eraserButton, penButton, markerButton, blurButton, paintBrushButton, fountainPenButton, rainbowPenButton;
  @FXML private Button rectangleButton;
  @FXML private Button loadPicture;
  @FXML private Button textButton;
  @FXML private Button boldButton;
  @FXML private Button italicButton;
  @FXML private Button underlineButton;

  private ToolManager toolManager;

  public void initialize() {
    toolManager = new ToolManager(canvas, overlayCanvas);
    initBinds();
    colorPicker.setValue(Color.BLACK);
    toolManager.changeTool(new RoundPen());
    initOpacitySliderListeners();

    canvas.setOnMouseEntered(e ->{toolManager.onEnter(e);});
    canvas.setOnMouseMoved(e -> {toolManager.onMove(e);});
    canvas.setOnMouseDragged(e -> {toolManager.onDrag(e);});
    canvas.setOnMousePressed(e -> {toolManager.onPress(e);});
    canvas.setOnMouseReleased(e -> {toolManager.onRelease(e);});

    initButtons();
  }

  public void onSave() {FileService.save(canvas);}

  public void onExit() {Platform.exit();}

  private void initBinds() {
    brushSize.textProperty().bindBidirectional(toolManager.brushSizeProperty(), new NumberStringConverter());
    opacitySlider.valueProperty().bindBidirectional(toolManager.brushOpacityProperty());
    opacitySlider.visibleProperty().bind(toolManager.opacitySliderProperty());
    opacityLabel.visibleProperty().bind(toolManager.opacityLabelProperty());
    colorPicker.valueProperty().bindBidirectional(toolManager.colorProperty());
  }

  private void initOpacitySliderListeners() {
    opacitySlider.valueProperty().addListener((observable, oldValue, newValue) -> updateSliderColor());
    colorPicker.valueProperty().addListener((observable, oldValue, newValue) -> updateSliderColor());
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

  public static Color getBackgroundColor() {
    //TODO implement background (leave getter as it is!!!!!!! already implemented)
    return Color.WHITE;
  }

  private void initButtons() {
    selectAndMoveButton.setOnAction(e -> {toolManager.changeTool(new SelectAndMove());});
    connectedSelectAndMoveButton.setOnAction(e -> {toolManager.changeTool(new ConnectedSelectAndMove());});
    penButton.setOnAction(e -> {toolManager.changeTool(new RoundPen());});
    markerButton.setOnAction(e -> {toolManager.changeTool(new Marker());});
    eraserButton.setOnAction(e -> {toolManager.changeTool(new RoundEraser());});
    fountainPenButton.setOnAction(e -> {toolManager.changeTool(new FountainPen());});
    blurButton.setOnAction(e -> {toolManager.changeTool(new Blur());});
    paintBrushButton.setOnAction(e -> {toolManager.changeTool(new PaintBrush());});
    rainbowPenButton.setOnAction(e -> {toolManager.changeTool(new RainbowPen());});
    rectangleButton.setOnAction(e -> {toolManager.changeTool(new Rectangle());});
    loadPicture.setOnAction(e -> PictureEditor.loadImage(canvas));

    //all about textfield here
    Textfield textTool = new Textfield();
    textButton.setOnAction(e -> toolManager.changeTool(textTool));

    boldButton.setOnAction(e -> textTool.setBold(!textTool.bold.isToggledOn())); // Toggle bold
    italicButton.setOnAction(e -> textTool.setItalic(!textTool.italic.isToggledOn())); // Toggle italic
    underlineButton.setOnAction(e -> textTool.setUnderline(!textTool.underline.isToggledOn()));
  }
}


