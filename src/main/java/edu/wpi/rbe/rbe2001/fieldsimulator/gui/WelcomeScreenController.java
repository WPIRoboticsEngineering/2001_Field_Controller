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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.CheckBox;


public class WelcomeScreenController implements Initializable {

    @FXML
    private Button Enter;

    @FXML
    private Label error;

    @FXML
    private TextField IDEntry;

    @FXML
    private Button MaintenanceBTN;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        error.setVisible(false);
    }

    @FXML
    public void enterCallback(){
        long ID = 0;
        boolean idValid = true;
        try{
            ID = Long.parseLong(IDEntry.getText());
            if(IDEntry.getText().length()!=9){
                idValid = false;
            }
        }
        catch (NumberFormatException e){
            idValid = false;
        }
        if(idValid) {
            Main.currentIDNum = ID;
            error.setVisible(false);
            IDEntry.setText("");
            Main.setItemSelectScene();
        }
        else{
            error.setVisible(true);
        }
    }
    @FXML
    public void MaintainenceBTNCallback(){
        Main.setRobotManagerScene();
    }
}
