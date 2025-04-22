package org.example.paint.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.example.paint.tools.Pencil;
import org.example.paint.tools.Eraser;
import org.example.paint.tools.Tool;

public class DrawtasticController {

    @FXML
    private Canvas canvas;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private TextField brushSize;

    @FXML
    private Button eraserButton;

    private Tool currentTool;

    public void initialize() {
        GraphicsContext g = canvas.getGraphicsContext2D();

        colorPicker.setValue(Color.BLACK);

        currentTool = new Pencil(colorPicker.getValue());

        colorPicker.setOnAction(e -> {
            currentTool = new Pencil(colorPicker.getValue());
        });

        eraserButton.setOnAction(e -> {
            currentTool = new Eraser();
        });

        canvas.setOnMouseDragged(e -> {
            try {
                double size = Double.parseDouble(brushSize.getText());
                currentTool.onDrag(g, e, size);
            } catch (NumberFormatException ex) {
                System.out.println("Ungültige Pinselgröße!");
            }
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
