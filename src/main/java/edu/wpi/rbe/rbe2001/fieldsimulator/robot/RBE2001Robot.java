package edu.wpi.rbe.rbe2001.fieldsimulator.robot;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import edu.wpi.SimplePacketComs.BytePacketType;
import edu.wpi.SimplePacketComs.FloatPacketType;
import edu.wpi.SimplePacketComs.PacketType;
import edu.wpi.SimplePacketComs.device.UdpDevice;
import edu.wpi.SimplePacketComs.phy.UDPSimplePacketComs;
import javafx.application.Platform;

public class RBE2001Robot extends UdpDevice {

	private FloatPacketType setSetpoint = new FloatPacketType(1848, 64);
	private FloatPacketType pidStatus = new FloatPacketType(1910, 64);
	private FloatPacketType getConfig = new FloatPacketType(1857, 64);
	private FloatPacketType setConfig = new FloatPacketType(1900, 64);
	private PacketType estop = new BytePacketType(1989, 64);
	private PacketType getStatus = new BytePacketType(2012, 64);
	private PacketType clearFaults = new BytePacketType(1871, 64);
	private PacketType pickOrder = new FloatPacketType(1936, 64);
	private PacketType approve = new BytePacketType(1994, 64);
	private byte[] status = new byte[1];
	private double[] pickOrderData = new double[3];
	private double[] driveStatus = new double[1];
	double[] numPID = new double[1];
	double[] pidConfigData = new double[15];
	private double[] piddata;
	private int myNumPid = -1;

	private RBE2001Robot(InetAddress add) throws Exception {
		super(add);

		for (PacketType pt : Arrays.asList(pidStatus, getConfig, setConfig, setSetpoint, clearFaults, pickOrder,
				getStatus, approve, estop)) {
			addPollingPacket(pt);
		}
		getConfig.oneShotMode();
		setConfig.waitToSendMode();
		setSetpoint.waitToSendMode();
		pickOrder.waitToSendMode();
		clearFaults.waitToSendMode();
		estop.waitToSendMode();
		approve.waitToSendMode();

		addEvent(getConfig.idOfCommand, () -> {
			try {
				readFloats(1857, pidConfigData);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		addEvent(getStatus.idOfCommand, () -> {
			readBytes(getStatus.idOfCommand, status);
		});

		addEvent(pidStatus.idOfCommand, () -> {
			try {
				if (piddata == null) {
					piddata = new double[1 + 2 * 7];
					readFloats(pidStatus.idOfCommand, piddata);
					setMyNumPid((int) piddata[0]);
					piddata = new double[1 + 2 * getMyNumPid()];
				}
				readFloats(pidStatus.idOfCommand, piddata);



			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});

	}

	public static List<RBE2001Robot> get(String name) throws Exception {
		HashSet<InetAddress> addresses = UDPSimplePacketComs.getAllAddresses(name);
		ArrayList<RBE2001Robot> robots = new ArrayList<>();
		if (addresses.size() < 1) {
			System.out.println("No " + RBE2001Robot.class.getSimpleName() + " found named " + name);
			return robots;
		}
		for (InetAddress add : addresses) {
			System.out.println("Got " + add.getHostAddress());
			RBE2001Robot e = new RBE2001Robot(add);
			e.connect();
			robots.add(e);
		}
		return robots;
	}

	@Override
	public String toString() {
		return getName();
	}

	public double getNumPid() {
		readFloats(1910, numPID);
		return numPID[0];
	}

	public double getPidSetpoint(int index) {
		double[] d = new double[1];
		readFloats(1910, d);
		return pidStatus.getUpstream()[1 + index * 2 + 0].doubleValue();
	}

	public double getPidPosition(int index) {
		return pidStatus.getUpstream()[1 + index * 2 + 1].doubleValue();
	}

	public void updatConfig() {
		getConfig.oneShotMode();
	}

	public void setPidGains(int index, double kp, double ki, double kd) {
		pidConfigData[3 * index + 0] = kp;
		pidConfigData[3 * index + 1] = ki;
		pidConfigData[3 * index + 2] = kd;
		writeFloats(setConfig.idOfCommand, pidConfigData);
		setConfig.oneShotMode();

	}

	public void setPidSetpoints(int msTransition, int mode, double[] data) {
		double down[] = new double[2 + myNumPid];
		down[0] = msTransition;
		down[1] = mode;
		for (int i = 0; i < myNumPid; i++) {
			down[2 + i] = data[i];
		}
		writeFloats(setSetpoint.idOfCommand, down);
		setSetpoint.oneShotMode();

	}

	public void setPidSetpoint(int msTransition, int mode, int index, double data) {
		double[] cur = new double[myNumPid];
		for (int i = 0; i < myNumPid; i++) {
			if (i == index)
				cur[index] = data;
			else
				cur[i] = getCurrentSetpoint(i);
		}
		cur[index] = data;
		setPidSetpoints(msTransition, mode, cur);

	}

	public void estop() {
		estop.oneShotMode();
	}

	public double getDriveStatus() {
		return driveStatus[0];
	}

	public void pickOrder(double material, double angle, double dropLocation) {
		pickOrderData[0] = material;
		pickOrderData[1] = angle;
		pickOrderData[2] = dropLocation;
		writeFloats(pickOrder.idOfCommand, pickOrderData);
		pickOrder.oneShotMode();

	}

	public WarehouseRobotStatus getStatus() {
		return WarehouseRobotStatus.fromValue(status[0]);
	}

	public void clearFaults() {
		clearFaults.oneShotMode();

	}

	public void approve() {
		approve.oneShotMode();

	}

	public double getCurrentSetpoint(int currentIndex) {
		return piddata[1 + currentIndex * 2 + 0];
	}

	public double getCurrentPosition(int currentIndex) {
		return piddata[1 + currentIndex * 2 + 1];
	}


	public int getMyNumPid() {
		return myNumPid;
	}

	public void setMyNumPid(int myNumPid) {
		this.myNumPid = myNumPid;
	}
}
