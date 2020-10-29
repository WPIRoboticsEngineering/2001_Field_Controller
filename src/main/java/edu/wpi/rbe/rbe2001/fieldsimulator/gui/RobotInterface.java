package edu.wpi.rbe.rbe2001.fieldsimulator.gui;

import edu.wpi.rbe.rbe2001.fieldsimulator.robot.*;

public class RobotInterface {
    private IWarehouseRobot robot;
    private WarehouseRobotStatus oldStatus = WarehouseRobotStatus.Initial_State;
    private final int numPIDControllersOnDevice = 3;
    private boolean DeliverIsTest = false;
    private boolean ReturnIsTest = false;
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
                        case Ready_for_new_task:
                            Main.SetMaintenanceScreenRobotStatus("Awaiting Task");
                            break;
                        case Picking_up:
                            Main.SetMaintenanceScreenRobotStatus("Picking Up Bin");
                            break;
                        case Delivery_Done:
                            Main.SetMaintenanceScreenRobotStatus("Delivery Done");
                            if(!DeliverIsTest){
                                Main.setRobotActionScene(1);
                            }
                            break;
                        case Returning_Done:
                            Main.SetMaintenanceScreenRobotStatus("Delivery Done");
                            if(!ReturnIsTest){
                                Main.setItemSelectScene();
                            }
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

    public void setDeliverIsTest(boolean wellIsIt){
        DeliverIsTest = wellIsIt;
    }
    public void setReturnIsTest(boolean wellIsIt){
        ReturnIsTest = wellIsIt;
    }
}
