package org.example.paint.tools.textField;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import org.example.paint.tools.Tool;


public class Textfield implements Tool {
    private String text = "click to write";
    private TextColor textColor = new TextColor(Color.BLACK);
    private TextSize textSize = new TextSize(12);
    private org.example.paint.tools.TextField.MyFont font = new org.example.paint.tools.TextField.MyFont(); // Assuming MyFont is defined elsewhere


    public void setString(String text) {
        this.text = text;
    }

    public void setTextColor(TextColor textColor) {
        this.textColor = textColor;
    }

    public void setTextSize(TextSize textSize) {
        this.textSize = textSize;
    }


    private String promptForText() {
        TextInputDialog dialog = new TextInputDialog(text);
        dialog.setTitle("Input Text");
        dialog.setHeaderText("Enter the text to display:");
        dialog.setContentText("Text:");

        dialog.showAndWait();
        return dialog.getResult(); // Return the input or default text
    }

    @Override
    public void onRelease(GraphicsContext g, MouseEvent e, double size) {
        // Capture user input
        String userInput = promptForText();
        setString(userInput); // Set the text to the input

        // Draw the finalized text at the mouse release position
        g.setFill(textColor.getColor());
        g.setFont(Font.font(font.getName(),
                TextFormating.isBold() ? FontWeight.BOLD : FontWeight.NORMAL,
                TextFormating.isItalic() ? FontPosture.ITALIC : FontPosture.REGULAR,
                textSize.getSize()));
        g.fillText(text, e.getX(), e.getY());
    }



}
