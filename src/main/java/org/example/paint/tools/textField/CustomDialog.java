package org.example.paint.tools.textField;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class CustomDialog extends Dialog<String> {
    private TextField textField;
    private TextField sizeField;
    private ComboBox<String> fontComboBox;
    private ColorPicker colorPicker;

    public CustomDialog(String defaultText) {
        setTitle("Input Text");
        setHeaderText("Enter the text to display:");

        //creating controls
        textField = new TextField(defaultText);
        sizeField = new TextField("12"); // Default size
        colorPicker = new ColorPicker(Color.BLACK); //TODO maybe set to currently selected brush color
        fontComboBox = new ComboBox<>();

        //only 1 font option
        fontComboBox.getItems().add("System default");
        //is the default value
        fontComboBox.setValue("System default");

        //grid layout
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

        //setting result converter
        setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return textField.getText();
            }
            return null;
        });
    }

    public Color getSelectedColor() {return colorPicker.getValue();}

    public double getSelectedSize() {
        try {
            return Double.parseDouble(sizeField.getText());
        } catch (NumberFormatException e) {
            return 12; //default size 12 is chosen if parsing fails
        }
    }
}
