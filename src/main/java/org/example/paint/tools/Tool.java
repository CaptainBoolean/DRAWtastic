package org.example.paint.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

/**
 * This interface contains all 4 methods that a tool can be called for in {@link org.example.paint.core.ToolManager}
 * They are all empty by default as not every tool needs to implement all 4 of them.
 */
public interface Tool {

  /**
   * Performs the tools specific action when the mouse is pressed.
   * @param g main canvas
   * @param dg drawing canvas (use if possible to ensure that the correct color and opacity is applied to the output)
   * @param e the MouseEvent that triggered this action (useful to get the coordinates)
   * @param size the currently selected tool size
   */
  default void onPress(GraphicsContext g, GraphicsContext dg, MouseEvent e, double size){}

  /**
   * Performs the tools specific action when the mouse is dragged over the canvas while pressed.
   * @param g main canvas
   * @param dg drawing canvas (use if possible to ensure that the correct color and opacity is applied to the output)
   * @param e the MouseEvent that triggered this action (useful to get the coordinates)
   * @param size the currently selected tool size
   */
  default void onDrag(GraphicsContext g, GraphicsContext dg, MouseEvent e, double size){}

  /**
   * Performes the tools specific action when the mouse is released.
   * @param g main canvas
   * @param dg drawing canvas (use if possible to ensure that the correct color and opacity is applied to the output)
   * @param e the MouseEvent that triggered this action (useful to get the coordinates)
   * @param size the currently selected tool size
   */
  default void onRelease(GraphicsContext g, GraphicsContext dg, MouseEvent e, double size){}

  /**
   * Performs the specific way a tool implements a preview.
   * @param og overlay canvas
   * @param e the MouseEvent that triggered this action (useful to get the coordinates)
   * @param size the currently selected tool size
   */
  default void drawPreviewAt(GraphicsContext og, MouseEvent e, double size){}


}
