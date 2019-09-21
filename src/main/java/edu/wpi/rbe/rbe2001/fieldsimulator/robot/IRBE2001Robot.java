package edu.wpi.rbe.rbe2001.fieldsimulator.robot;

public interface IRBE2001Robot {
	public void add2001();
	public void estop();
	public double getDriveStatus() ;
	public void pickOrder(double material, double angle, double dropLocation);
	public WarehouseRobotStatus getStatus();
	public void clearFaults();
	public void approve();

}
