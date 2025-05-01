package org.example.paint.tools.textField.layout;

public class Italic implements Layout {
    @Override
    public void toggleOn() {
        //turn on by java fx
        state.toggle = true;
    }

    @Override
    public void toggleOff() {
        //turn off by java fx
        state.toggle = false;
    }

    @Override
    public boolean isToggledOn() {
        return state.toggle;
    }

    public void setToggled(boolean toggle) {
        state.toggle = toggle;
    }
}
