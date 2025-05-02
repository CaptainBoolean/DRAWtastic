package org.example.paint.tools.textField;

public class MyFont {
    private static final String ARIAL = "Arial";

    private String fontName;

    public MyFont() {
        fontName = ARIAL; //default font
    }
    public String getName() {
        return fontName;
    }
    public void setName(String fontName) {
        this.fontName = fontName;
    }
}
