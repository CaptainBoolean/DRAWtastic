package org.example.paint.tools.TextField;

import org.example.paint.tools.TextField.Layout.Bold;
import org.example.paint.tools.TextField.Layout.Italic;
import org.example.paint.tools.TextField.Layout.Underline;

public class Textfield {
    private String text = "click to write";
    private TextColor textColor = TextColor.BLACK;
    private TextSize textSize = TextSize.MEDIUM;
    private Font font = new Font(); //is not changed; there is only 1 font
    private Bold bold = new Bold();
    private Italic italic = new Italic();
    private Underline underline = new Underline();

    public void setString(String text) {
        this.text = text;
    }

    public void setTextColor(TextColor textColor) {
        this.textColor = textColor;
    }

    public void setTextSize(TextSize textSize) {
        this.textSize = textSize;
    }

    public void setBold(boolean toggle) {
        if (toggle) {
            bold.toggleOn();
        } else {
            bold.toggleOff();
        }
    }
    public void setItalic(boolean toggle) {
        if (toggle) {
            italic.toggleOn();
        } else {
            italic.toggleOff();
        }
    }
    public void setUnderline(boolean toggle) {
        if (toggle) {
            underline.toggleOn();
        } else {
            underline.toggleOff();
        }
    }
}
