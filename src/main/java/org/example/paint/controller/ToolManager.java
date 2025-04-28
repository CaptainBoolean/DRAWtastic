//package org.example.paint.controller;
//
//import javafx.scene.canvas.Canvas;
//import javafx.scene.input.MouseEvent;
//import org.example.paint.tools.Tool;
//
//import java.util.ArrayList;
//
//public class ToolManager {
//  private Tool currentTool;
//  private final Canvas canvas;
//  private final Canvas overlayCanvas;
//
//  public ToolManager(Canvas canvas, Canvas overlayCanvas) {
//    this.canvas = canvas;
//    this.overlayCanvas = overlayCanvas;
//  }
//
//  public void changeTool(Tool newTool) {
//    this.currentTool = newTool;
//  }
//
//  public void updateToolSizeAndOpacity(double size, double opacity) {
//    currentTool.updateSize(size);
//    currentTool.updateOpacity(opacity);
//  }
//
//  public void drawPreview(MouseEvent e) {
//    currentTool.drawPreviewAt(overlayCanvas.getGraphicsContext2D(), e);
//  }
//
//  public void handleMouseDragged(MouseEvent e) {
//    currentTool.onDrag(canvas.getGraphicsContext2D(), e);
//    drawPreview(e);
//  }
//
//  public void handleMousePressed(MouseEvent e) {
//    currentTool.onPress(canvas.getGraphicsContext2D(), e);
//  }
//
//  public void handleMouseReleased(MouseEvent e, double size) {
//    currentTool.onRelease(canvas.getGraphicsContext2D(), e, size);
//  }
//
//
//}
