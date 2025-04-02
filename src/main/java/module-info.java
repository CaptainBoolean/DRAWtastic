module org.example.multimediagroupproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.dlsc.formsfx;

    opens org.example.multimediagroupproject to javafx.fxml;
    exports org.example.multimediagroupproject;

}