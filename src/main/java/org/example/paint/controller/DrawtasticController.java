package org.example.paint.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import org.example.paint.tools.pens.SquareEraser;
import org.example.paint.tools.pens.Shapes.Rectangle;
import org.example.paint.tools.Tool;
import org.example.paint.tools.pens.RoundPen;

public class DrawtasticController {

    @FXML
    private Canvas canvas;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private Button pencilButton;

    @FXML
    private Button markerButton;

    @FXML
    private Slider opacitySlider;

    @FXML
    private TextField brushSize;

    @FXML
    private Button eraserButton;

    @FXML
    private Button rectangleButton;

    private Tool currentTool;

    public void initialize() {
        GraphicsContext g = canvas.getGraphicsContext2D();

        colorPicker.setValue(Color.BLACK);

        currentTool = new RoundPen(colorPicker.getValue());



        pencilButton.setOnAction(e -> {
            currentTool = new RoundPen(colorPicker.getValue());
        });

        eraserButton.setOnAction(e -> {
            currentTool = new SquareEraser();
        });

        rectangleButton.setOnAction(e -> {
            currentTool = new Rectangle(colorPicker.getValue());
        });

        canvas.setOnMouseDragged(e -> {
            try {
                double size = Double.parseDouble(brushSize.getText());
                currentTool.onDrag(g, e, size);
            } catch (NumberFormatException ex) {
                System.out.println("Ungültige Pinselgröße!");
            }
        });

        canvas.setOnMouseReleased(e -> {
            // TODO fix unnecessary variables
            currentTool.onRelease(g, e, Double.parseDouble(brushSize.getText()));
        });

        canvas.setOnMousePressed(e -> {
            currentTool.onPress(canvas.getGraphicsContext2D(), e);
        });

        canvas.setOnMouseReleased(e -> {
            double size = Double.parseDouble(brushSize.getText());
            currentTool.onRelease(g, e, size);
        });
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
