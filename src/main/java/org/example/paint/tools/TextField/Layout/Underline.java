package org.example.paint.tools.TextField.Layout;

public class Underline implements Layout {

    @Override
    public void toggleOn() {
        state.toggle = true;
    }

    @Override
    public void toggleOff() {
        state.toggle = false;
    }
}
