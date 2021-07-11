package edu.wpi.rbe.rbe2001.fieldsimulator.robot;

import java.util.Arrays;

import edu.wpi.SimplePacketComs.BytePacketType;
import edu.wpi.SimplePacketComs.FloatPacketType;
import edu.wpi.SimplePacketComs.PacketType;

public interface IWarehouseRobot {
	// add new packets here
	PacketType estop = new BytePacketType(1989, 64);
	PacketType getStatus = new BytePacketType(2012, 64);
	PacketType clearFaults = new BytePacketType(1871, 64);
	PacketType park = new FloatPacketType(1945, 64);
	PacketType navigate = new FloatPacketType(1966, 64);
	PacketType deliverBin = new FloatPacketType(1908, 64);
	PacketType returnBin = new FloatPacketType(1912, 64);
	PacketType homeLift = new FloatPacketType(2077, 64);
	PacketType setLiftHeight = new FloatPacketType(2020, 64);
	byte[] status = new byte[1];
	double[] driveStatus = new double[1];
	double[] desiredLocation = new double[3];
	double[] liftHeightMM = new double[1];
	double[] placeholder = new double[1];

	default public void addWarehouseRobot() {

		clearFaults.waitToSendMode();
		estop.waitToSendMode();
		park.waitToSendMode();
		navigate.waitToSendMode();
		deliverBin.waitToSendMode();
		returnBin.waitToSendMode();
		getStatus.pollingMode();
		homeLift.waitToSendMode();
		setLiftHeight.waitToSendMode();
		for (PacketType pt : Arrays.asList(clearFaults, getStatus, estop, park, navigate, deliverBin, returnBin, homeLift, setLiftHeight)) {
			addPollingPacket(pt);
		}
	}

	default public void sendPark(double row, double col){
		desiredLocation[0] = row;
		desiredLocation[1] = col;
		desiredLocation[2] = 0;
		writeFloats(park.idOfCommand, desiredLocation);
		park.oneShotMode();
		System.out.println("Send Park");
		System.out.println(Arrays.toString(desiredLocation));
	}

	default public void sendNavGoal(double row, double col){
		desiredLocation[0] = row;
		desiredLocation[1] = col;
		desiredLocation[2] = 0;
		writeFloats(navigate.idOfCommand, desiredLocation);
		navigate.oneShotMode();
		System.out.println(Arrays.toString(desiredLocation));
		System.out.println("Send Nav");
	}
	default public void sendDeliverBin(double row, double col, double height){
		desiredLocation[0] = row;
		desiredLocation[1] = col;
		desiredLocation[2] = height;
		writeFloats(deliverBin.idOfCommand, desiredLocation);
		deliverBin.oneShotMode();
		System.out.println(Arrays.toString(desiredLocation));
		System.out.println("Send DLV");
	}
	default public void sendReturnBin(double row, double col, double height){
		desiredLocation[0] = row;
		desiredLocation[1] = col;
		desiredLocation[2] = height;
		writeFloats(returnBin.idOfCommand, desiredLocation);
		returnBin.oneShotMode();
		System.out.println(Arrays.toString(desiredLocation));
		System.out.println("Send Return");
	}

	default public void sendHomeLift(){
		placeholder[0] = 1;
		writeFloats(homeLift.idOfCommand,placeholder );
		homeLift.oneShotMode();
	}

	default public void sendMoveLift(double mm){
		liftHeightMM[0] = mm;
		writeFloats(setLiftHeight.idOfCommand, liftHeightMM);
		setLiftHeight.oneShotMode();
	}

	default public void estop() {
		estop.oneShotMode();
	}

	default public double getDriveStatus() {
		return driveStatus[0];
	}

	default public WarehouseRobotStatus getStatus() {
		return WarehouseRobotStatus.fromValue(status[0]);
	}

	default public void clearFaults() {
		clearFaults.oneShotMode();

	}

	void addPollingPacket(PacketType packet);

	public void addEvent(Integer id, Runnable event);

	void readBytes(int id, byte[] values);

	void writeFloats(int idOfCommand, double[] data);
}
