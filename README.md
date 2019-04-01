# 200x_Field_Controller

This application is used to communicate information form a JavaFX application to the RBE2000 classes robots.

# How To add new commands

To add your own custom command start with the default code and modify it by adding a custom command. Your new command needs a unique packet command ID, any 32 bit unsigned integer is valid. 

## Setup: Set the Command ID

The id is set in two places, once in the firmware:
https://github.com/WPIRoboticsEngineering/RBE2001_template/blob/master/src/commands/PickOrder.h#L12
and once in the Java robot object:
https://github.com/WPIRoboticsEngineering/2001_Field_Controller/blob/2001_Field/src/main/java/edu/wpi/rbe/rbe2001/fieldsimulator/robot/RBE2001Robot.java#L30

## Setup: Attach the new command to the library

In the java robot object, the commands are set up to send once and only once when commanded to:
https://github.com/WPIRoboticsEngineering/2001_Field_Controller/blob/2001_Field/src/main/java/edu/wpi/rbe/rbe2001/fieldsimulator/robot/RBE2001Robot.java#L117

In the firmware, the servers are added to the coms layer at startup:
https://github.com/WPIRoboticsEngineering/RBE2001_template/blob/master/src/RobotControlCenter.cpp#L88

### Single Commands

For commands sent as a single send you set the packet into waitToSend mode, then call oneShotMode to send one and only one packet.

https://github.com/WPIRoboticsEngineering/2001_Field_Controller/blob/2001_Field/src/main/java/edu/wpi/rbe/rbe2001/fieldsimulator/robot/RBE2001Robot.java#L118

then 

https://github.com/WPIRoboticsEngineering/2001_Field_Controller/blob/2001_Field/src/main/java/edu/wpi/rbe/rbe2001/fieldsimulator/robot/RBE2001Robot.java#L294

### Polling mode reading data

The default mode for a packet is to poll over and over.

### Reading data from a polling comand

Reading data from a polling command is handled with a lambda attached to the Command ID

https://github.com/WPIRoboticsEngineering/2001_Field_Controller/blob/2001_Field/src/main/java/edu/wpi/rbe/rbe2001/fieldsimulator/robot/RBE2001Robot.java#L126

In the C code this is read from the event for the command ID

https://github.com/WPIRoboticsEngineering/RBE2001_template/blob/master/src/commands/GetStatus.cpp#L7

## Call the command: Java

Events start with the Javafx button event:
https://github.com/WPIRoboticsEngineering/2001_Field_Controller/blob/2001_Field/src/main/java/edu/wpi/rbe/rbe2001/fieldsimulator/gui/InterfaceController.java#L564

which tells the robot object to write the data(as up to 15 floats):
https://github.com/WPIRoboticsEngineering/2001_Field_Controller/blob/2001_Field/src/main/java/edu/wpi/rbe/rbe2001/fieldsimulator/robot/RBE2001Robot.java#L293


then send a packet:
https://github.com/WPIRoboticsEngineering/2001_Field_Controller/blob/2001_Field/src/main/java/edu/wpi/rbe/rbe2001/fieldsimulator/robot/RBE2001Robot.java#L294

## Recive the command: C

This is received by the firmware in a server here where it passes the array of floats to the StudentsRobot instance:

https://github.com/WPIRoboticsEngineering/RBE2001_template/blob/master/src/commands/PickOrder.cpp
