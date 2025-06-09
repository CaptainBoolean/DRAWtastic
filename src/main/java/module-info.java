module org.example.multimediagroupproject {
    requires javafx.controls;
    requires javafx.fxml;

  requires java.desktop;
  requires javafx.swing;
  requires javafx.graphics;

  opens org.example.paint to javafx.fxml;
    exports org.example.paint;
    exports org.example.paint.core;
    opens org.example.paint.core to javafx.fxml;
}