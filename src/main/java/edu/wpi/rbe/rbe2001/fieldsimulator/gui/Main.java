package edu.wpi.rbe.rbe2001.fieldsimulator.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Main extends Application {
  private static Stage theStage;
  private static Scene WelcomeScene = null;
  private static Scene ItemSelectScene = null;
  private static Scene ItemCheckOutScene = null;
  private static Scene RobotActionScene = null;
  private static Scene RobotManagerScene = null;
  private static InventoryManager inventoryManager;
  private static FXMLLoader ICOController = null;
  private static FXMLLoader RobotActionController = null;
  private static FXMLLoader RobotManagerController = null;
  private static RobotInterface BackendRobotController;
  //Shared Screen Data
  public static ObservableList<ListViewPart> partList = FXCollections.observableArrayList();
  public static long currentIDNum = 0;
  public static ListViewPart currentPart = null;
  public static int numberRequested = 0;


  @Override
  public void start(Stage stage) throws Exception {
    theStage = stage;
    inventoryManager = new InventoryManager("Inventory.json", "BorrowedInventory.json");
    inventoryManager.loadInventory();
    inventoryManager.loadBorrowedInventory();
    Parent WelcomeScreen = FXMLLoader.load(getClass().getResource("/fxml/WelcomeScreen.fxml"));
    ICOController = new FXMLLoader(getClass().getResource("/fxml/ItemCheckOutScreen.fxml"));
    RobotActionController = new FXMLLoader(getClass().getResource("/fxml/RobotActionScreen.fxml"));
    RobotManagerController = new FXMLLoader(getClass().getResource("/fxml/RobotManagerScreen.fxml"));
    Parent ItemCheckOutScreen = ICOController.load();
    Parent RobotActionScreen = RobotActionController.load();
    Parent RobotManagerScreen = RobotManagerController.load();
    Parent ItemSelectScreen = FXMLLoader.load(Main.class.getResource("/fxml/ItemSelectScreen.fxml"));
    theStage.setTitle("RBE Warehouse");
    theStage.setResizable(false);
    WelcomeScene = new Scene(WelcomeScreen);
    ItemSelectScene = new Scene(ItemSelectScreen);
    ItemCheckOutScene = new Scene(ItemCheckOutScreen);
    RobotActionScene = new Scene(RobotActionScreen);
    RobotManagerScene = new Scene(RobotManagerScreen);
    //Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainScreen.fxml"));
    //root.getStylesheets().add("/materialfx-material-design-for-javafx/material-fx-v0_3.css");
    //theStage.setScene(new Scene(root, 1011, 665));
    theStage.setScene(WelcomeScene);
    BackendRobotController = new RobotInterface();
    theStage.show();

  }

  public static void main(String[] args) {
    launch(args);
  }

  public static void setWelcomeScene(){theStage.setScene(WelcomeScene);}
  public static void setItemSelectScene(){theStage.setScene(ItemSelectScene);}
  public static void setItemCheckOutScene(String nameOfPart, long numberInStock, boolean needToReturn){
    ItemCheckOutScreenController ICOSController = ICOController.getController();
    ICOSController.setScreenInfo(nameOfPart, numberInStock, needToReturn);
    theStage.setScene(ItemCheckOutScene);
  }

  public static void setRobotActionScene(int phase){
    RobotActionScreenController controller = RobotActionController.getController();
    theStage.setScene(RobotActionScene);
    if(phase==0){
      controller.setRetrieve();
    }
    else if(phase == 1){
      controller.setWaitForDone(numberRequested);
    }
    else{
      controller.setPutBack();
    }

  }
  public static void setRobotManagerScene(){theStage.setScene(RobotManagerScene);}
  public static void updateInventory(ListViewPart part, int numBorrowed, long ID, String forClass){
    inventoryManager.addBorrowedParts(part, numBorrowed, ID, forClass);
    inventoryManager.removeItemsFromInventory(part, numBorrowed);
    try{//has to remake scene because the listview gets messed up when editing it
      Parent ItemSelectScreen = FXMLLoader.load(Main.class.getResource("/fxml/ItemSelectScreen.fxml"));
      ItemSelectScene = new Scene(ItemSelectScreen);
    }
    catch(IOException e){
      e.printStackTrace();
    }

  }
  public static void SetMaintenanceScreen(String name){
    RobotManagerScreenController controller = RobotManagerController.getController();
    controller.setRobotNameLabel(name);
    if(name.equals("None")){
      Platform.runLater(Main::setRobotManagerScene);
    }
  }

  public static void tryReconnectingToRobots(){
    BackendRobotController.connectToDevice();
  }

}
