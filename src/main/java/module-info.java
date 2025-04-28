module org.example.multimediagroupproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
  requires java.desktop;
  requires javafx.swing;

  opens org.example.paint to javafx.fxml;
    exports org.example.paint;
    exports org.example.paint.controller;
    opens org.example.paint.controller to javafx.fxml;
}