package edu.wpi.rbe.rbe2001.fieldsimulator.gui;
import edu.wpi.rbe.rbe2001.fieldsimulator.gui.Main;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import edu.wpi.rbe.rbe2001.fieldsimulator.robot.IRBE2001Robot;
import edu.wpi.rbe.rbe2001.fieldsimulator.robot.IRBE2002Robot;
import edu.wpi.rbe.rbe2001.fieldsimulator.robot.RBE2001Robot;
import edu.wpi.rbe.rbe2001.fieldsimulator.robot.RBE3001Robot;
import edu.wpi.rbe.rbe2001.fieldsimulator.robot.ISimplePIDRobot;
import edu.wpi.rbe.rbe2001.fieldsimulator.robot.WarehouseRobotStatus;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.*;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class RobotManagerScreenController implements Initializable {

    @FXML
    private Label robotName;

    @FXML
    private Label robotStatus;

    @FXML
    private Button ExitBTN;

    @FXML
    private TextField rowEntry;

    @FXML
    private TextField colEntry;

    @FXML
    private TextField heightEntry;

    @FXML
    private Button NavigateBTN;

    @FXML
    private Button ParkBTN;

    @FXML
    private Button DeliverBTN;

    @FXML
    private Button ReturnBTN;

    public void initialize(URL location, ResourceBundle resources) {

    }
    public void setRobotNameLabel(String Name){
        robotName.setText(Name);
    }

    public void setRobotStatusLabel(String status){robotStatus.setText(status);}

    @FXML
    public void ExitBTNCallback(){
        Main.setWelcomeScene();
    }
    @FXML
    public void ReconnectBTNCallback(){
        Main.BackendRobotController.connectToDevice();
    }

    @FXML
    public void NavBTNPressed(){
        double row = 0;
        double col = 0;
        boolean formatCorrect = true;
        try{
            row = Double.parseDouble(rowEntry.getText());
            col = Double.parseDouble(colEntry.getText());
        }
        catch(Exception ex){
            formatCorrect = false;
        }
        if(formatCorrect){
            Main.BackendRobotController.sendNavGoal(row, col);
        }
    }
    @FXML
    public void ParkBTNPressed(){
        double row = 0;
        double col = 0;
        boolean formatCorrect = true;
        try{
            row = Double.parseDouble(rowEntry.getText());
            col = Double.parseDouble(colEntry.getText());
        }
        catch(Exception ex){
            formatCorrect = false;
        }
        if(formatCorrect){
            Main.BackendRobotController.sendPark(row, col);
        }
    }
    @FXML
    public void DeliverBTNPressed(){
        double row = 0;
        double col = 0;
        double height = 0;
        boolean formatCorrect = true;
        try{
            row = Double.parseDouble(rowEntry.getText());
            col = Double.parseDouble(colEntry.getText());
            height = Double.parseDouble(heightEntry.getText());
        }
        catch(Exception ex){
            formatCorrect = false;
        }
        if(formatCorrect){
            Main.BackendRobotController.setDeliverIsTest(true);
            Main.BackendRobotController.sendDeliverBin(row, col, height);
        }
    }
    @FXML
    public void ReturnBTNPressed(){
        double row = 0;
        double col = 0;
        double height = 0;
        boolean formatCorrect = true;
        try{
            row = Double.parseDouble(rowEntry.getText());
            col = Double.parseDouble(colEntry.getText());
            height = Double.parseDouble(heightEntry.getText());
        }
        catch(Exception ex){
            formatCorrect = false;
        }
        if(formatCorrect){
            Main.BackendRobotController.setReturnIsTest(true);
            Main.BackendRobotController.sendReturnBin(row, col, height);
        }
    }
}
