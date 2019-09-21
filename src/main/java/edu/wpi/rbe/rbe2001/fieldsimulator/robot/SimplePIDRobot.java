package edu.wpi.rbe.rbe2001.fieldsimulator.robot;

public interface SimplePIDRobot {
	public void add2001();
	public void addIMU();

	public void addIR();


	public double getNumPid();
	public double getPidSetpoint(int index);
	public double getPidPosition(int index);
	public double getHardwareOutput(int index) ;

	public double getVelocity(int index) ;
	public double getVelSetpoint(int index) ;

	public void updatConfig();

	public void setPidGains(int index, double kp, double ki, double kd) ;

	public double getKp(int index);
	public double getKi(int index);

	public double getKd(int index);
	public double getVKp(int index);

	public double getVKd(int index);
	public void setVelocityGains(int index, double kp, double kd);

	public void setPidSetpoints(int msTransition, int mode, double[] data) ;
	public void setPidSetpoint(int msTransition, int mode, int index, double data);

	public void setVelocity(int index, double data);

	public void setVelocity(double[] data);

	public void estop();
	public double getDriveStatus() ;
	public void pickOrder(double material, double angle, double dropLocation);
	public WarehouseRobotStatus getStatus();

	public void clearFaults();

	public void approve();
	public int getMyNumPid();
	public void setMyNumPid(int myNumPid);

	public void stop(int currentIndex);
}
