package org.example.paint.tools.textField;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.example.paint.tools.Tool;

import java.util.Optional;


public class Textfield implements Tool {
    private String text = "write here";
    private TextColor textColor = new TextColor(Color.BLACK);
    private TextSize textSize = new TextSize(12);

    public void setString(String text) {
        this.text = text;
    }

    //shows the dialog
    private String promptForText() {
        //create the custom dialog
        CustomDialog dialog = new CustomDialog(text);

        //show and wait for user input
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) { //ok: setting selected string, color and size
            textColor = new TextColor(dialog.getSelectedColor());
            textSize.setSize((int) dialog.getSelectedSize());
            return result.get();
        }

        return ""; //canceled: inserts empty string; no text visible
    }


    @Override
    public void onRelease(GraphicsContext g, MouseEvent e, double size) {
        //capturing user input
        String userInput = promptForText();
        setString(userInput); //set text to the input

        //draw text there where mouse click happened
        g.setFill(textColor.getColor());
        g.setFont(Font.font(Font.getDefault().getFamily(),
                TextFormating.isBold() ? FontWeight.BOLD : FontWeight.NORMAL,
                TextFormating.isItalic() ? FontPosture.ITALIC : FontPosture.REGULAR,
                textSize.getSize()));

        //get position to place the text
        double x = e.getX();
        double y = e.getY();

        //draw the text there
        g.fillText(text, x, y);

        if (TextFormating.isUnderline()) { //in case underline was clicked
            //calculating width of the text
            Text textNode = new Text(text);
            textNode.setFont(g.getFont());
            double textWidth = textNode.getLayoutBounds().getWidth();

            //drawing the underline
            double underlineY = y + 2; //value position adjusted
            g.setStroke(textColor.getColor()); //stroke color is set
            g.setLineWidth(1); //line width is chosen
            g.strokeLine(x, underlineY, x + textWidth, underlineY); //draw the underline
        }
    }
}
