package edu.wpi.rbe.rbe2001.fieldsimulator.robot;

import edu.wpi.SimplePacketComs.device.UdpDevice;

public class WarehouseRobot extends UdpDevice  implements IWarehouseRobot{

	public WarehouseRobot(String add) throws Exception {
		super(add);
		connect();
	}

	@Override
	public String toString() {
		return getName();
	}


}
