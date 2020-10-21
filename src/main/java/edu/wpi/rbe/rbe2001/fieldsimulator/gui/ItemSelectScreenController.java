package edu.wpi.rbe.rbe2001.fieldsimulator.gui;
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

public class ItemSelectScreenController implements Initializable {
    @FXML
    private Button Finished;

    @FXML
    private ListView<ListViewPart> partsList;

    public ItemSelectScreenController(){

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        partsList.setItems(Main.partList);
        partsList.setCellFactory(partListView->new ListViewPartCell());
    }
    public void finishedCallback(){ Main.setWelcomeScene();}

    public void itemSelectedCallback(){
        ListViewPart sP = partsList.getSelectionModel().getSelectedItem();
        Main.currentPart = sP;
        Main.setItemCheckOutScene(sP.getName(), sP.getNumAvailable(), sP.getreturnRequired());
    }
}
