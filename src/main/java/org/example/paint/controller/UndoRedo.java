package org.example.paint.controller;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;

//TODO test if it works with all tools!!!
//TODO background gets white every time --> fix
public class UndoRedo {
  private final Canvas currCanvas;
  private static final int MAX_COUNT = 40;

  private final WritableImage[] history = new WritableImage[MAX_COUNT];
  private int historyIndex = 0;
  private int topIndex = 0;

  public UndoRedo(Canvas canvas) {
    this.currCanvas = canvas;
    history[0] = canvas.snapshot(new SnapshotParameters(), null);
  }

  public void saveState() {
    WritableImage snap = currCanvas.snapshot(new SnapshotParameters(), null);

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

  public void undo() {
    if (historyIndex > 0) {
      historyIndex--;
      restore(history[historyIndex]);
    }
  }

  public void redo() {
    if (historyIndex < topIndex) {
      historyIndex++;
      restore(history[historyIndex]);
    }
  }

  private void restore(WritableImage img) {
    GraphicsContext gc = currCanvas.getGraphicsContext2D();
    gc.clearRect(0, 0, currCanvas.getWidth(), currCanvas.getHeight());
    gc.drawImage(img, 0, 0);
  }
}
