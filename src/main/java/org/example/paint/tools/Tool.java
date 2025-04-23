package org.example.paint.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public interface Tool {
    void onDrag(GraphicsContext g, MouseEvent e, double size, Color color, double opacity);
    default void onPress(GraphicsContext g, MouseEvent e) {
        //default keyword damit diese beiden Methoden nicht zwingend in jedem Tool verwendet werden müssen (brauchen nur shapes)
    }
    void onRelease(GraphicsContext g, MouseEvent e, double size);
}
