package edu.wpi.rbe.rbe2001.fieldsimulator.robot;

import java.util.HashMap;
import java.util.Map;

public enum WarehouseRobotStatus {
	StartingUp( (byte)0),
	StartRunning ( (byte) 1),
	Idle ( (byte) 2),
	Halting ( (byte) 3),
	Halt ( (byte) 4),
	WAIT_FOR_MOTORS_TO_FINISH ( (byte)5),
	WAIT_FOR_TIME ( (byte)6),
	Testing ( (byte) 7),
	Navigating ( (byte) 8),
	ParkingRobot ( (byte) 9),
	HomingLift( (byte)10),
	MovingLift( (byte)11),
	Delivering((byte) 12),
	Returning((byte) 13),
	Delivery_Done( (byte)14),
	Returning_Done( (byte)15),
	Bin_Not_On_Cleat( (byte)16),
	Bin_Not_On_Shelf( (byte)17),
	Delivery_Failure( (byte) 18),
	Timed_Out( (byte) 19),
	Initial_State( (byte)20);// For use with Gui

	private static final Map<Byte, WarehouseRobotStatus> lookup = new HashMap<>();

    static {
        for (WarehouseRobotStatus d : WarehouseRobotStatus.values()) {
            lookup.put(d.getValue(), d);
        }
    }

	private byte value;
	
	public static WarehouseRobotStatus fromValue(byte b) {
		return lookup.get(b);
	}

	WarehouseRobotStatus(byte value){
		this.value = value;
		
	}

	public byte getValue() {
		return value;
	}

	public void setValue(byte value) {
		this.value = value;
	}
	public boolean isFault() {
		return value>6;
	}
	
}
