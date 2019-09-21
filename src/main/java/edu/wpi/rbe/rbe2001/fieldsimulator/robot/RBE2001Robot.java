package edu.wpi.rbe.rbe2001.fieldsimulator.robot;

import edu.wpi.SimplePacketComs.device.UdpDevice;

public class RBE2001Robot extends UdpDevice  implements SimplePIDRobot,IRBE2001Robot,IRBE2002Robot{



	public RBE2001Robot(String add, int numPID) throws Exception {
		super(add);
		setupPidCommands(numPID);
		connect();
	}

	@Override
	public String toString() {
		return getName();
	}


}
