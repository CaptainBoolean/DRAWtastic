package org.example.paint.tools.textField;

public class MyFont {
    static final String ARIAL = "Arial";
    static final String TIMES_NEW_ROMAN = "Times New Roman";
    static final String VERDANA = "Verdana";
    static final String GEORGIA = "Georgia";
    static final String COURIER_NEW = "Courier New";
    static final String COMIC_SANS_MS = "Comic Sans MS";
    static final String IMPACT = "Impact";

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
