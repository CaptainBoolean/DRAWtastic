package org.example.paint.tools.TextField.Layout;

public class Bold implements Layout {
    @Override
    public void toggleOn() {
        //turn on by javafx
        state.toggle = true;
    }

    @Override
    public void toggleOff() {
        //turn off by javafx
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
