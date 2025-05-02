package org.example.paint.tools.textField;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.example.paint.tools.Tool;

import java.util.Optional;


public class Textfield implements Tool {
    private String text = "click to write";
    private TextColor textColor = new TextColor(Color.BLACK);
    private TextSize textSize = new TextSize(12);
    private org.example.paint.tools.textField.MyFont font = new org.example.paint.tools.textField.MyFont(); // Assuming MyFont is defined elsewhere


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

        Optional<String> result = dialog.showAndWait();
        return result.orElse(text); // Return the input or default text
        /*TODO: use the custom dialog window
        Input Text -+
        ----------
        Enter the text to display:
        ----------
        Text: (text can be entered here)
        Size: (text size can be changed here)
        Font: (let choose between different fonts)
        Color: (let choose between different colors)
         */
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

        // Get the position to draw the text
        double x = e.getX();
        double y = e.getY();

        // Draw the text
        g.fillText(text, x, y);

        if (TextFormating.isUnderline()) {
            // Calculating the width of the text to draw the underline
            Text textNode = new Text(text);
            textNode.setFont(g.getFont());
            double textWidth = textNode.getLayoutBounds().getWidth();

            // Draw the underline
            double underlineY = y + 2; // Adjust this value to position the underline correctly
            g.setStroke(textColor.getColor()); // Set the stroke color to match the text color
            g.setLineWidth(1); // Set the line width for the underline
            g.strokeLine(x, underlineY, x + textWidth, underlineY); // Draw the underline
        }
    }
}
