package org.example.paint.tools.textField;

//TODO why seperate class instead of just a variable?
public class TextSize {
    private int size; //default

    public TextSize(int size) {
        this.size = size;
    }
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }
}
