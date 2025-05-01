package org.example.paint.tools.textField;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class TextFormating {
   private static BooleanProperty bold = new SimpleBooleanProperty();
   private static BooleanProperty italic = new SimpleBooleanProperty();
   private static BooleanProperty underline = new SimpleBooleanProperty();

  public BooleanProperty getBoldProperty() {return bold;}
  public BooleanProperty getItalicProperty() {return italic;}
  public BooleanProperty getUnderlineProperty() {return underline;}

  static boolean isBold() {return bold.get();}
  static boolean isItalic() {return italic.get();}
  static boolean isUnderline() {return underline.get();}
}
