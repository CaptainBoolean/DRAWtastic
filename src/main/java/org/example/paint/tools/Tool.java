package org.example.paint.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

public interface Tool {
    void onDrag(GraphicsContext g, MouseEvent e, double size);
}
