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

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class RobotActionScreenController implements Initializable{
    @FXML
    Label actionLabel;

    @FXML
    Button done;

    public void initialize(URL location, ResourceBundle resources) {
        actionLabel.setText("Please Wait As The Part Bin Is Retrieved");
        done.setVisible(false);
    }

    public void setRetrieve(){
        actionLabel.setText("Please Wait As The Part Bin Is Retrieved");
        done.setVisible(false);
    }

    public void setWaitForDone(int numParts){
        String num = Integer.toString(numParts);
        actionLabel.setText("Please Take "+num+" Parts From The Bin And Press Done When Finished");
        done.setVisible(true);
    }
    public void setPutBack(){
        //Call some method to command robot here
        actionLabel.setText("Please Wait As The Part Bin Is Placed Back On The Shelf");
        done.setVisible(false);
    }

    public void donePressed(){
        Main.BackendRobotController.setReturnIsTest(false);
        Main.BackendRobotController.sendReturnBin(Main.currentPart.getRow(), Main.currentPart.getCol(), Main.currentPart.getHeight());
    }

}
