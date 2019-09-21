package edu.wpi.rbe.rbe2001.fieldsimulator.robot;

public interface SimplePIDRobot {
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

	public double getDriveStatus();

	public int getMyNumPid();
	public void setMyNumPid(int myNumPid);
	public void addEvent(Integer id, Runnable event);
	public String getName();
	public void disconnect();
	public void readFloats(int id, double[] values);
	public void stop(int currentIndex);
}
