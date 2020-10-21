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

public class ItemCheckOutScreenController implements Initializable {

    @FXML
    private Label name;

    @FXML
    private Label numStock;

    @FXML
    private Label PartReturn;

    @FXML
    private Spinner<Integer> quantityDesired;

    @FXML
    private Button confirm;

    @FXML
    private Button cancel;

    @FXML
    private TextField rbeclassEntry;

    public void initialize(URL location, ResourceBundle resources) {
        SpinnerValueFactory<Integer> valueFactory = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);
        quantityDesired.setValueFactory(valueFactory);
    }
    public void setScreenInfo(String nameOfPart, long numberInStock, boolean needToReturn){
        quantityDesired.getValueFactory().setValue(1);
        quantityDesired.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, (int)numberInStock, 1));
        name.setText(nameOfPart);
        numStock.setText(Integer.toString((int)numberInStock));
        if(needToReturn){
            PartReturn.setText("End Of Term");
        }
        else{
            PartReturn.setText("Do Not Return");
        }
        rbeclassEntry.setText("");
    }
    public void confirm(){
        Main.numberRequested = quantityDesired.getValue();
        Main.updateInventory(Main.currentPart, Main.numberRequested, Main.currentIDNum, rbeclassEntry.getText());
        Main.setRobotActionScene(0);
    }

    public void cancelCallback(){
        Main.setItemSelectScene();
    }
}
