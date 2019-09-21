package edu.wpi.rbe.rbe2001.fieldsimulator.robot;

import edu.wpi.SimplePacketComs.phy.HIDSimplePacketComs;

public class RBE3001Robot extends HIDSimplePacketComs implements SimplePIDRobot {


	public RBE3001Robot(int vidIn, int pidIn,int numPID) throws Exception {
		super(vidIn,  pidIn);
		setupPidCommands(numPID);
		connect();
		if(isVirtual())
			throw new RuntimeException("Device is virtual!");
	}

	@Override
	public String toString() {
		return getName();
	}




}
