package org.example.paint.controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.io.File;

public class FileService {

  static void save(Canvas canvas) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Save drawing as 'png'");
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG", "*.png"));

    File file = fileChooser.showSaveDialog(canvas.getScene().getWindow());
    try {
      WritableImage image = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
      Image snapshot = canvas.snapshot(null, image);
      int cutX = 0, cutY = 0, cutWidth = 0, cutHeight = 0;
      cutX = cutY = cutWidth = cutHeight = 500;
      //TODO cut snapshot to max y and x of non background pixel and add 10px as buffer
      Image cutImage = new WritableImage(snapshot.getPixelReader(), cutX,cutY,cutWidth,cutHeight);
      ImageIO.write(SwingFXUtils.fromFXImage(cutImage, null), "png", file);


    } catch (Exception e) {
      System.out.println("Failed to save Image: " + e);
    }
  }
}
