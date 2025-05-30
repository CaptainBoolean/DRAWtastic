package org.example.paint.tools.generalTools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.example.paint.tools.Opaqueable;
import org.example.paint.tools.Tool;

import java.util.Optional;

public class Textfield implements Tool, Opaqueable {
    private String text = "write here";
    private Color textColor = Color.BLACK;
    private int textSize = 12;
    private boolean bold = false;
    private boolean italic = false;
    private boolean underline = false;

    public void setString(String text) {
        this.text = text;
    }

    //shows the dialog
    private String promptForText() {
        //create the custom dialog
        CustomDialog dialog = new CustomDialog(text, textColor, textSize, bold, italic, underline);

        //show and wait for user input
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) { //ok: setting selected string, color and size
            textColor = dialog.getSelectedColor();
            textSize = (int) dialog.getSelectedSize();
            bold = dialog.getBold();
            italic = dialog.getItalic();
            underline = dialog.getUnderline();
            return result.get();
        }

        return ""; //canceled: inserts empty string; no text visible
    }


    @Override
    public void onRelease(GraphicsContext g, GraphicsContext dg, MouseEvent e, double size) {
        //capturing user input
        String userInput = promptForText();
        setString(userInput); //set text to the input

        //draw text there where mouse click happened
        dg.setFill(textColor);
        dg.setFont(Font.font(Font.getDefault().getFamily(),
                bold ? FontWeight.BOLD : FontWeight.NORMAL,
                italic ? FontPosture.ITALIC : FontPosture.REGULAR,
                textSize));

        //get position to place the text
        double x = e.getX();
        double y = e.getY();

        //draw the text there
        dg.fillText(text, x, y);

        if (underline) { //in case underline was clicked
            //calculating width of the text
            Text textNode = new Text(text);
            textNode.setFont(dg.getFont());
            double textWidth = textNode.getLayoutBounds().getWidth();

            //drawing the underline
            double underlineY = y + 2; //value position adjusted
            dg.setStroke(textColor); //stroke color is set
            dg.setLineWidth(1); //line width is chosen
            dg.strokeLine(x, underlineY, x + textWidth, underlineY); //draw the underline
        }
    }

    private static class CustomDialog extends Dialog<String> {
        private TextField textField;
        private TextField sizeField;
        private ComboBox<String> fontComboBox;
        private ColorPicker colorPicker;
        private ToggleButton toggleBold;
        private ToggleButton toggleItalic;
        private ToggleButton toggleUnderline;

        public CustomDialog(String text, Color textColor, int textSize, boolean bold, boolean italic, boolean underline) {
            setTitle("Input Text");
            setHeaderText("Enter the text to display:");

            //creating controls
            textField = new TextField(text);
            sizeField = new TextField(Integer.toString(textSize));
            colorPicker = new ColorPicker(textColor);
            fontComboBox = new ComboBox<>();
            toggleBold = new ToggleButton("Bold");
            toggleBold.setSelected(bold);
            toggleItalic = new ToggleButton("Italic");
            toggleItalic.setSelected(italic);
            toggleUnderline = new ToggleButton("Underline");
            toggleUnderline.setSelected(underline);

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
}
