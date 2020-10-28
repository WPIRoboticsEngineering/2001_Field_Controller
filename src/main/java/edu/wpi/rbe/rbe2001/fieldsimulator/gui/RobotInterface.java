package edu.wpi.rbe.rbe2001.fieldsimulator.gui;

import edu.wpi.rbe.rbe2001.fieldsimulator.robot.*;

public class RobotInterface {
    private IWarehouseRobot robot;
    private final int numPIDControllersOnDevice = 3;
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
    public IWarehouseRobot getRobot() {
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
                System.out.println("Recieved Status");
                robot.readBytes(robot.getStatus.idOfCommand, robot.status);
                switch(robot.getStatus()){
                    case Ready_for_new_task:
                        Main.SetMaintenanceScreenRobotStatus("Awaiting Task");
                        break;
                    case Picking_up:
                        Main.SetMaintenanceScreenRobotStatus("Picking Up Bin");
                        break;
                    default:
                        Main.SetMaintenanceScreenRobotStatus("No Status");
                        break;
                }

            });
        }

    }
}
