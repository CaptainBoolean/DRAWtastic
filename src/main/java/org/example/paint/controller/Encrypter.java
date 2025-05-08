package org.example.paint.controller;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.Random;

public class Encrypter {
  Canvas canvas;

  Encrypter(Canvas canvas) {
    this.canvas = canvas;
  }

  //TODO implement with shifting pixels based on the key AND the x and y location to insure unreadability
  void encrypte(){
    double rand = askForKey();
    System.out.println(rand);


  }

  void decrypte(){

  }

  double askForKey(){
    int password = 0;
    CustomDialog dialog = new CustomDialog();
    if(dialog.showAndWait().isPresent()){
      password = dialog.getPassword();
    }
    return new Random(password).nextDouble();
  }

  private class CustomDialog extends Dialog {

    TextField password;

    public CustomDialog() {
      setTitle("Enter Text");

      password = new TextField();
      password.setPromptText("Enter something...");

      // Allow only digits in the TextField
      password.textProperty().addListener((obs, oldValue, newValue) -> {
        if (!newValue.matches("\\d*")) {
          password.setText(oldValue);
        }
      });

      getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

      VBox content = new VBox(10, new Label("Please enter:"), password);
      getDialogPane().setContent(content);

      setResultConverter(button -> {
        if (button == ButtonType.OK) {
          return password.getText();
        }
        return null;
      });
    }

    int getPassword(){
      return (int)Double.parseDouble(password.getText());
    }
  }
}
