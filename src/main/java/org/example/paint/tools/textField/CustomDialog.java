package org.example.paint.tools.textField;

import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.Optional;

public class CustomDialog extends Dialog<String> {
    private TextField textField;
    private TextField sizeField;
    private ComboBox<String> fontComboBox;
    private ColorPicker colorPicker;

    public CustomDialog(String defaultText) {
        setTitle("Input Text");
        setHeaderText("Enter the text to display:");

        // Create controls
        textField = new TextField(defaultText);
        sizeField = new TextField("12"); // Default size
        colorPicker = new ColorPicker(Color.BLACK);
        fontComboBox = new ComboBox<>();

        // Populate font options
        fontComboBox.getItems().addAll(
                "Arial", "Verdana", "Times New Roman", "Courier New",
                "Georgia", "Comic Sans MS", "Impact", "Tahoma",
                "Trebuchet MS", "Lucida Console"
        );

        // Create a grid pane to layout the controls
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Input Text:"), 0, 0);
        grid.add(textField, 1, 0);
        grid.add(new Label("Size:"), 0, 1);
        grid.add(sizeField, 1, 1);
        grid.add(new Label("Font:"), 0, 2);
        grid.add(fontComboBox, 1, 2);
        grid.add(new Label("Color:"), 0, 3);
        grid.add(colorPicker, 1, 3);

        DialogPane dialogPane = new DialogPane();
        dialogPane.setContent(grid);
        getDialogPane().setContent(dialogPane);
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Set the result converter
        setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return textField.getText(); // Return the input text
            }
            return null;
        });
    }

    public Color getSelectedColor() {
        return colorPicker.getValue();
    }

    public String getSelectedFont() {
        return fontComboBox.getValue();
    }

    public double getSelectedSize() {
        try {
            return Double.parseDouble(sizeField.getText());
        } catch (NumberFormatException e) {
            return 12; // Default size if parsing fails
        }
    }
}
