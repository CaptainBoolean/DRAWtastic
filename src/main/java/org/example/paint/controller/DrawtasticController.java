package org.example.paint.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import org.example.paint.tools.pens.Marker;
import org.example.paint.tools.pens.SquareEraser;
import org.example.paint.tools.Shapes.Rectangle;
import org.example.paint.tools.Tool;
import org.example.paint.tools.pens.RoundPen;

public class DrawtasticController {

    @FXML
    private Canvas canvas;

    @FXML
    private Canvas overlayCanvas;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private Button pencilButton;

    @FXML
    private Slider opacitySlider;

    @FXML
    private Button markerButton;

    @FXML
    private TextField brushSize;

    @FXML
    private Button eraserButton;

    @FXML
    private Button rectangleButton;

    private Tool currentTool;

    public void initialize() {
        GraphicsContext g = canvas.getGraphicsContext2D();
        GraphicsContext og = overlayCanvas.getGraphicsContext2D();

        colorPicker.setValue(Color.BLACK);

        currentTool = new RoundPen();

        canvas.setOnMouseMoved(e -> {
            currentTool.drawPreviewAt(og, e, Double.parseDouble(brushSize.getText()));
        });

        canvas.setOnMouseDragged(e -> {
            currentTool.drawPreviewAt(og, e, Double.parseDouble(brushSize.getText()));
        });

        pencilButton.setOnAction(e -> {
            currentTool = SizeOpacityAdjust(new RoundPen());
        });

        markerButton.setOnAction(e -> {
            currentTool = SizeOpacityAdjust(new Marker());
        });

        eraserButton.setOnAction(e -> {
            currentTool = SizeOpacityAdjust(new SquareEraser());
        });

        rectangleButton.setOnAction(e -> {
            currentTool = SizeOpacityAdjust(new Rectangle(colorPicker.getValue()));
        });

        canvas.setOnMouseDragged(e -> {
            try {
                double size = Double.parseDouble(brushSize.getText());
                Color color = colorPicker.getValue();
                double opacity = opacitySlider.getValue();
                currentTool.onDrag(g, e, size, color, opacity);
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
        return tool;
    }

    public void onSave() {
        try {
            Image snapshot = canvas.snapshot(null, null);
            // Speicherort goes here
        } catch (Exception e) {
            System.out.println("Failed to save Image: " + e);
        }
    }

    public void onExit() {
        Platform.exit();
    }
}
