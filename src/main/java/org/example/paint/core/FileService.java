package org.example.paint.core;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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

  static void save(Canvas canvas, Color backgroundColor) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Save drawing as 'png'");
    fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"), new FileChooser.ExtensionFilter("JPEG", "*.jpg","*.jpeg"));

    File file = fileChooser.showSaveDialog(canvas.getScene().getWindow());
    if (file == null) {
      return;
    }
    try {
      WritableImage image = getTransparentSnapshot(canvas.getGraphicsContext2D());
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
      int buffer = 50; //buffer amount
      int cutX = Math.max(0, minX - buffer/2);
      int cutY = Math.max(0, minY - buffer/2);
      int cutWidth = Math.min(width - cutX, maxX - minX + buffer);
      int cutHeight = Math.min(height - cutY, maxY - minY + buffer);
      Image cutImage = new WritableImage(reader, cutX, cutY, cutWidth, cutHeight);
      //check if jpg or png because of background handling
      String type = file.getName().toLowerCase().endsWith(".jpg") || file.getName().toLowerCase().endsWith(".jpeg") ? "jpg" : "png";
      if(type.equals("png") && backgroundColor.equals(Color.TRANSPARENT)) {
        ImageIO.write(SwingFXUtils.fromFXImage(cutImage, null), "png", file);
      } else {
        BufferedImage bufferedImage = new BufferedImage(cutWidth, cutHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bufferedImage.createGraphics();
        if(backgroundColor.equals(Color.TRANSPARENT) && type.equals("jpg")) {
          graphics.setColor(java.awt.Color.white);
        } else {
          graphics.setColor(colorTypeTransformer(backgroundColor));
        }
        graphics.fillRect(0, 0, cutWidth, cutHeight);
        graphics.drawImage(SwingFXUtils.fromFXImage(cutImage, null), 0, 0, null);
        graphics.dispose();
        if(type.equals("jpg")) {
          ImageIO.write(bufferedImage, "jpg", file);
        } else {
          ImageIO.write(bufferedImage, "png", file);
        }
      }

    } catch (Exception e) {
      System.out.println("Failed to save Image: " + e);
    }
  }

  private static java.awt.Color colorTypeTransformer(Color color) {
      int r = (int) Math.round(color.getRed() * 255);
      int g = (int) Math.round(color.getGreen() * 255);
      int b = (int) Math.round(color.getBlue() * 255);
      int a = (int) Math.round(color.getOpacity() * 255);
      return new java.awt.Color(r, g, b, a);
  }

  /**
   * Return a full Image of the provided canvas, displaying transparent pixels as transparent.
   * @return WritableImage
   */
  public static WritableImage getTransparentSnapshot(GraphicsContext g) {
    Canvas canvas = g.getCanvas();
    WritableImage tempImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
    SnapshotParameters params = new SnapshotParameters();
    params.setFill(Color.TRANSPARENT);
    canvas.snapshot(params, tempImage);
    return  tempImage;
  }

  public static Color getColorAtPosition(GraphicsContext g, int x, int y) {
    WritableImage image = getTransparentSnapshot(g);
    PixelReader reader = image.getPixelReader();
    return  reader.getColor(x, y);
  }
}
