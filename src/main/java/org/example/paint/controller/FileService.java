package org.example.paint.controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class FileService {

  //TODO remove background if transparent maybe need method in background

  static void save(Canvas canvas, Color backgroundColor) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Save drawing as 'png'");
    fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"), new FileChooser.ExtensionFilter("JPEG", "*.jpg","*.jpeg"));

    File file = fileChooser.showSaveDialog(canvas.getScene().getWindow());
    if (file == null) {
      return;
    }
    try {
      WritableImage image = getTranspSnapshot(canvas);
      PixelReader reader = image.getPixelReader();
      int minX = (int) canvas.getWidth();
      int minY = (int) canvas.getHeight();
      int maxX = 0;
      int maxY = 0;
      for(int y = 0; y < canvas.getHeight(); y++) {
        for(int x = 0; x < canvas.getWidth(); x++) {
          int argb = reader.getArgb(x, y);
          if((argb >> 24) != 0x00){
            minX = Math.min(minX, x);
            minY = Math.min(minY, y);
            maxX = Math.max(maxX, x);
            maxY = Math.max(maxY, y);
          }
        }
      }
      if(maxX < minX || maxY < minY) {
        return;
      }
      int width = (int) image.getWidth();
      int height = (int) image.getHeight();
      int buffer = 10; //buffer amount
      int cutX = Math.max(0, minX - buffer);
      int cutY = Math.max(0, minY - buffer);
      int cutWidth = Math.min(width - cutX, maxX - minX + buffer);
      int cutHeight = Math.min(height - cutY, maxY - minY + buffer);
      //TODO cut snapshot to max y and x of non background pixel and add 10px as buffer
      Image cutImage = new WritableImage(reader, cutX, cutY, cutWidth, cutHeight);
      //check if jpg or png because of background handling
      String type = file.getName().toLowerCase().endsWith(".jpg") || file.getName().toLowerCase().endsWith(".jpeg") ? "jpg" : "png";
      if(type.equals("png")) {
        ImageIO.write(SwingFXUtils.fromFXImage(cutImage, null), "png", file);
      } else {
        BufferedImage bufferedImage = new BufferedImage(cutWidth, cutHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.setColor(java.awt.Color.WHITE);
        graphics.fillRect(0, 0, cutWidth, cutHeight);
        graphics.drawImage(SwingFXUtils.fromFXImage(cutImage, null), 0, 0, null);
        graphics.dispose();
        ImageIO.write(bufferedImage, "jpg", file);
      }

    } catch (Exception e) {
      System.out.println("Failed to save Image: " + e);
    }
  }

  /**
   * Return a full Image of the provided canvas, displaying transparent pixels as transparent.
   * @param canvas
   * @return WritableImage
   */
  public static WritableImage getTranspSnapshot(Canvas canvas) {
    WritableImage tempImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
    SnapshotParameters params = new SnapshotParameters();
    params.setFill(Color.TRANSPARENT);
    canvas.snapshot(params, tempImage);
    return  tempImage;
  }
}
