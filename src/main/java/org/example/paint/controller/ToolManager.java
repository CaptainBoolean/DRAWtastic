//package org.example.paint.controller;
//
//import javafx.beans.property.DoubleProperty;
//import javafx.beans.property.SimpleDoubleProperty;
//import javafx.scene.canvas.Canvas;
//import org.example.paint.tools.Tool;
//import org.example.paint.tools.pens.FountainPen;
//import org.example.paint.tools.pens.Marker;
//import org.example.paint.tools.pens.PaintBrush;
//import org.example.paint.tools.pens.RainbowPen;
//
//public class ToolManager {
//  private Tool currentTool;
//  private final Canvas canvas;
//  private final Canvas overlayCanvas;
//  private final DoubleProperty brushSize = new SimpleDoubleProperty(5);
//  private final DoubleProperty opacity = new SimpleDoubleProperty(1);
//
//  public ToolManager(Canvas canvas, Canvas overlayCanvas) {
//    this.canvas = canvas;
//    this.overlayCanvas = overlayCanvas;
//  }
//
//  public void changeTool(Tool newTool) {
//    double markerSizeRatio = 2;
//    double opacityRatio = 0.2;
//
//    if(currentTool instanceof Marker && !(newTool instanceof Marker)) {
//      brushSize.setValue("" + Double.parseDouble(brushSize.toString()) / markerSizeRatio);
//      opacitySlider.setValue(opacitySlider.getMax());
//
//    }
//    if(!(currentTool instanceof Marker) && newTool instanceof Marker) {
//      brushSize.setText("" + Double.parseDouble(brushSize.toString()) * markerSizeRatio);
//      opacitySlider.setValue(opacitySlider.getMax()*opacityRatio);
//    }
//    if (newTool instanceof Marker || newTool instanceof FountainPen || newTool instanceof RainbowPen || newTool instanceof PaintBrush) {
//      opacitySlider.setVisible(true);
//      opacityLabel.setVisible(true);
//    } else {
//      opacitySlider.setVisible(false);
//      opacityLabel.setVisible(false);
//    }
//    return newTool;
//  }
//
//
//  DoubleProperty brushSizeProperty() {
//    return brushSize;
//  }
//}
