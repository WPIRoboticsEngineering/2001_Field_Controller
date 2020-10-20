package edu.wpi.rbe.rbe2001.fieldsimulator.gui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
  private static Stage theStage;
  private static Scene WelcomeScene = null;
  private static Scene ItemSelectScene = null;
  public static ObservableList<ListViewPart> partList = FXCollections.observableArrayList();


  @Override
  public void start(Stage stage) throws Exception {
    theStage = stage;
    Parent WelcomeScreen = FXMLLoader.load(getClass().getResource("/fxml/WelcomeScreen.fxml"));
    Parent ItemSelectScreen = FXMLLoader.load(getClass().getResource("/fxml/ItemSelectScreen.fxml"));
    //Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainScreen.fxml"));
    theStage.setTitle("RBE Warehouse");
    //root.getStylesheets().add("/materialfx-material-design-for-javafx/material-fx-v0_3.css");
    //stage.setScene(new Scene(root, 1011, 665));
    //primaryStage.setResizable(false);
    WelcomeScene = new Scene(WelcomeScreen);
    ItemSelectScene = new Scene(ItemSelectScreen);
    theStage.setScene(WelcomeScene);
    theStage.show();

  }

  public static void main(String[] args) {
    launch(args);
  }

  public static void setWelcomeScene(){theStage.setScene(WelcomeScene);}
  public static void setItemSelectScene(){theStage.setScene(ItemSelectScene);}
}
