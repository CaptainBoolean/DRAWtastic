package org.example.paint.tools.textField;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import org.example.paint.controller.ToolManager;

public class CustomDialog extends Dialog<String> {
    private TextField textField;
    private TextField sizeField;
    private ComboBox<String> fontComboBox;
    private ColorPicker colorPicker;
    private ToggleButton toggleBold;
    private ToggleButton toggleItalic;
    private ToggleButton toggleUnderline;

    public CustomDialog(String defaultText) {
        setTitle("Input Text");
        setHeaderText("Enter the text to display:");

        //creating controls
        textField = new TextField(defaultText);
        sizeField = new TextField("12"); // Default size
        colorPicker = new ColorPicker(ToolManager.getDrawColor());
        fontComboBox = new ComboBox<>();
        toggleBold = new ToggleButton("Bold");
        toggleItalic = new ToggleButton("Italic");
        toggleUnderline = new ToggleButton("Underline");


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
        grid.add(new Label("Bold:"), 0, 4);
        grid.add(toggleBold, 1, 4);
        grid.add(new Label("Italic:"), 0, 5);
        grid.add(toggleItalic, 1, 5);
        grid.add(new Label("Underline:"), 0, 6);
        grid.add(toggleUnderline, 1, 6);

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

    public boolean getBold(){return toggleBold.isSelected();}
    public boolean getItalic(){return toggleItalic.isSelected();}
    public boolean getUnderline(){return toggleUnderline.isSelected();}
}
