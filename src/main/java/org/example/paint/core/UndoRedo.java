package org.example.paint.core;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;

public class UndoRedo {
  private final Canvas currCanvas;
  private static final int MAX_COUNT = 50; //change depending on canvas size and system performance

  private final WritableImage[] history = new WritableImage[MAX_COUNT];
  private int historyIndex = 0;
  private int topIndex = 0;

  /**
   * Constructs the UndoRedo with the Canvas it should be applied to and initializes the first image
   * @param canvas canvas for the UndoRedo to work on
   */
  public UndoRedo(Canvas canvas) {
    this.currCanvas = canvas;
    history[0] = FileService.getTransparentSnapshot(canvas.getGraphicsContext2D());
  }

  /**
   * Saves the current state of the canvas to the array of saved states
   * Saving ofter undoing operations sets this operation as the last one and discards all possible redo
   */
  public void saveState() {
    WritableImage snap = FileService.getTransparentSnapshot(currCanvas.getGraphicsContext2D());

    if (historyIndex < topIndex) {
      topIndex = historyIndex;
    }

    if (topIndex < MAX_COUNT - 1) {
      history[++topIndex] = snap;
      historyIndex = topIndex;
    } else {
      System.arraycopy(history, 1, history, 0, MAX_COUNT - 1);
      history[MAX_COUNT - 1] = snap;
      historyIndex = topIndex = MAX_COUNT - 1;
    }
  }

  /**
   * Reverts the canvas to the previous state
   */
  public void undo() {
    if (historyIndex > 0) {
      historyIndex--;
      restore(history[historyIndex]);
    }
  }

  /**
   * Reverts the canvas to the next state if there is any
   */
  public void redo() {
    if (historyIndex < topIndex) {
      historyIndex++;
      restore(history[historyIndex]);
    }
  }

  /**
   * Restores the canvas to the image provided
   * @param image Image to restore the canvas to
   */
  private void restore(WritableImage image) {
    GraphicsContext gc = currCanvas.getGraphicsContext2D();
    gc.clearRect(0, 0, currCanvas.getWidth(), currCanvas.getHeight());
    gc.drawImage(image, 0, 0);
  }
}
