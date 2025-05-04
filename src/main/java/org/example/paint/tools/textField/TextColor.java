package org.example.paint.tools.textField;

import javafx.scene.paint.Color;

//TODO why seperate class instead of just a variable?
public class TextColor {
    private Color color;

    public TextColor(Color color) {
        this.color = color;
    }
    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }
}
