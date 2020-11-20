package edu.wpi.rbe.rbe2001.fieldsimulator.gui;

import edu.wpi.rbe.rbe2001.fieldsimulator.robot.*;
import javafx.application.Platform;

public class RobotInterface {
    private IWarehouseRobot robot;
    private WarehouseRobotStatus oldStatus = WarehouseRobotStatus.Initial_State;
    private final int numPIDControllersOnDevice = 3;
    private boolean DeliverIsTest = false;
    private boolean ReturnIsTest = false;
    public double defaultParkLocation[] = {1,0};
    public RobotInterface(){
        connectToDevice();
    }

    public void connectToDevice() {
        if (getRobot() == null) {
            new Thread(() -> {
                String name = "*";//TODO Change to robot name

                try {
                    setFieldSim(new WarehouseRobot(name));
                    // getFieldSim().setReadTimeout(1000);
                    if (getRobot() != null) {
                        Main.SetMaintenanceScreenRobotName(robot.toString());
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                if(getRobot()==null){
                    Main.SetMaintenanceScreenRobotName("None");
                }

            }).start();
        }
    }
    private IWarehouseRobot getRobot() {
        return robot;
    }

    private void setFieldSim(IWarehouseRobot r) {
        // fieldSim.setReadTimeout(1000);
        try {
            Thread.sleep(1000);//I think this must wait to let the UDP device respond
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        robot = r;
        //Add events here
        if(robot!=null){
            robot.addWarehouseRobot();
            robot.addEvent(robot.getStatus.idOfCommand, () -> {
                robot.readBytes(robot.getStatus.idOfCommand, robot.status);
                if(robot.getStatus()!= oldStatus){
                    switch(robot.getStatus()){
                        case StartingUp:
                            Main.SetMaintenanceScreenRobotStatus("Starting Robot");
                            break;
                        case StartRunning:
                            Main.SetMaintenanceScreenRobotStatus("Start Running");
                            break;
                        case Idle:
                            Main.SetMaintenanceScreenRobotStatus("Idle");
                            break;
                        case Halting:
                            Main.SetMaintenanceScreenRobotStatus("Halting");
                            break;
                        case Halt:
                            Main.SetMaintenanceScreenRobotStatus("Halted");
                            break;
                        case WAIT_FOR_MOTORS_TO_FINISH:
                            Main.SetMaintenanceScreenRobotStatus("Waiting For Motors to Finish");
                            break;
                        case WAIT_FOR_TIME:
                            Main.SetMaintenanceScreenRobotStatus("Waiting for time");
                            break;
                        case Testing:
                            Main.SetMaintenanceScreenRobotStatus("Testing");
                            break;
                        case Navigating:
                            Main.SetMaintenanceScreenRobotStatus("Navigating");
                            break;
                        case ParkingRobot:
                            Main.SetMaintenanceScreenRobotStatus("Parking Robot");
                            break;
                        case HomingLift:
                            Main.SetMaintenanceScreenRobotStatus("Homing Lift");
                            break;
                        case MovingLift:
                            Main.SetMaintenanceScreenRobotStatus("Moving Lift");
                            break;
                        case Delivering:
                            Main.SetMaintenanceScreenRobotStatus("Delivering Bin");
                            break;
                        case Returning:
                            Main.SetMaintenanceScreenRobotStatus("Returning Bin");
                            Platform.runLater(()->Main.setRobotActionScene(2));
                            break;
                        case Delivery_Done:
                            Main.SetMaintenanceScreenRobotStatus("Delivery Done");
                            Platform.runLater(()->Main.setRobotActionScene(1));
                            break;
                        case Returning_Done:
                            Main.SetMaintenanceScreenRobotStatus("Returning Done");
                            if(!ReturnIsTest){
                                Platform.runLater(Main::setItemSelectScene);
                            }
                            break;
                        case Bin_Not_On_Cleat:
                            Main.SetMaintenanceScreenRobotStatus("Bin Not On Cleat");
                            Platform.runLater(Main::setRobotActionSceneWarning);
                            break;
                        case Bin_Not_On_Shelf:
                            Main.SetMaintenanceScreenRobotStatus("Bin Not On Shelf");
                            break;
                        case Delivery_Failure:
                            Main.SetMaintenanceScreenRobotStatus("Delivery Done");
                            break;
                        case Timed_Out:
                            Main.SetMaintenanceScreenRobotStatus("Timed Out");
                            break;
                        default:
                            Main.SetMaintenanceScreenRobotStatus("No Status");
                            break;
                    }
                    oldStatus = robot.getStatus();
                }
            });
        }

    }
    public void sendPark(double row, double col){
        robot.sendPark(row, col);
    }

    public void sendNavGoal(double row, double col){
        robot.sendNavGoal(row, col);
    }
    public void sendDeliverBin(double row, double col, double height){
        robot.sendDeliverBin(row, col, height);
    }
    public void sendReturnBin(double row, double col, double height){
        robot.sendReturnBin(row, col, height);
    }
    public void sendHomeLift(){robot.sendHomeLift();}
    public void sendMoveLift(double mm){robot.sendMoveLift(mm);}

    public void setDeliverIsTest(boolean wellIsIt){
        DeliverIsTest = wellIsIt;
    }
    public void setReturnIsTest(boolean wellIsIt){
        ReturnIsTest = wellIsIt;
    }
}
