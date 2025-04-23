package org.example.paint.tools.pens;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.example.paint.tools.Tool;

public class Pen implements Tool {

    private Color color;

    public Pen(Color color) {
        this.color = color;
    }

    @Override
    public void onDrag(GraphicsContext g, MouseEvent e, double size) {
        g.setFill(color);
        g.fillOval(e.getX(), e.getY(), size, size);
    }


}
