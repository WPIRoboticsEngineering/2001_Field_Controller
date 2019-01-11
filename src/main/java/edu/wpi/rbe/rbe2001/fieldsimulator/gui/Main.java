package edu.wpi.rbe.rbe2001.fieldsimulator.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainScreen.fxml"));
    primaryStage.setTitle("RBE 2001 Controller");
    //root.getStylesheets().add("/materialfx-material-design-for-javafx/material-fx-v0_3.css");
    primaryStage.setScene(new Scene(root, 1011, 665));
    //primaryStage.setResizable(false);
    
    primaryStage.show();
    primaryStage.getScene().setRoot(root);
    primaryStage.setOnCloseRequest(event -> {
 
        InterfaceController.disconnect();
      
    });
  }

  public static void main(String[] args) {
    launch(args);
  }
}
