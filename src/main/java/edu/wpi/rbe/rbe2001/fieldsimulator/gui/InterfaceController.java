package edu.wpi.rbe.rbe2001.fieldsimulator.gui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;

import edu.wpi.rbe.rbe2001.fieldsimulator.robot.IRBE2001Robot;
import edu.wpi.rbe.rbe2001.fieldsimulator.robot.IRBE2002Robot;
import edu.wpi.rbe.rbe2001.fieldsimulator.robot.RBE2001Robot;
import edu.wpi.rbe.rbe2001.fieldsimulator.robot.RBE3001Robot;
import edu.wpi.rbe.rbe2001.fieldsimulator.robot.ISimplePIDRobot;
import edu.wpi.rbe.rbe2001.fieldsimulator.robot.WarehouseRobotStatus;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.CheckBox;

public class InterfaceController {

	@FXML
	private CheckBox use2001;

	@FXML
	private CheckBox useIMU;

	@FXML
	private CheckBox useIR;
	@FXML
	private Label accelx;

	@FXML
	private Label accely;

	@FXML
	private Label accelz;

	@FXML
	private Label gyrox;

	@FXML
	private Label gyroy;

	@FXML
	private Label gyroz;

	@FXML
	private Label gravx;

	@FXML
	private Label gravy;

	@FXML
	private Label gravz;

	@FXML
	private Label eulx;

	@FXML
	private Label euly;

	@FXML
	private Label eulz;
	@FXML
	private Tab connectTab;

	@FXML
	private TextField teamName;

	@FXML
	private Button connectToDevice;

	@FXML
	private Button pidExport;

	@FXML
	private Button velExport;

	@FXML
	private Label robotName;

	@FXML
	private Tab pidVelTab;
	@FXML
	private Tab tab2001Field;
	@FXML
	private TextField kpVel;

	@FXML
	private TextField kdVel;
	
	@FXML
	private TextField velKiField;
	@FXML
	private Button pidConstUpdateVelocity;

	@FXML
	private ChoiceBox<Integer> pidChannelVelocity;

	@FXML
	private TextField setpointVelocity;

	@FXML
	private Button setSetpointVelocity;
	@FXML
	private Button teensyButton;
	@FXML
	private Label velocityVal;

	@FXML
	private Tab pidTab;
	@FXML
	private Tab imutab;
	@FXML
	private Tab irtab;

	@FXML
	private TextField kp;

	@FXML
	private TextField ki;

	@FXML
	private TextField kd;

	@FXML
	private Button pidConstUpdate;

	@FXML
	private TextField setpoint;

	@FXML
	private Button setSetpoint;

	@FXML
	private Label position;

	@FXML
	private Label hardwareOut;

	@FXML
	private Label posHwValue;
	@FXML
	private ChoiceBox<Integer> pidChannel;

	@FXML
	private Button stop;

	@FXML
	private TextArea response;

	@FXML
	private Button send;

	@FXML
	private RadioButton heartBeat;

	@FXML
	private ChoiceBox<String> choiceBoxWeight;

	@FXML
	private ChoiceBox<String> choiceBoxSide;

	@FXML
	private ChoiceBox<String> choiceBoxPos;

	@FXML
	private Button approveButton;

	@FXML // fx:id="setDuration"
	private TextField setDuration; // Value injected by FXMLLoader

	@FXML // fx:id="setType"
	private ChoiceBox<String> setType; // Value injected by FXMLLoader
	@FXML
	private ScatterChart<Double, Double> irChart;
	@FXML
	private LineChart<Double, Double> pidGraph;
	private GraphManager pidManager = null;
	private GraphManager velManager = null;
	@FXML
	private LineChart<Double, Double> pidGraphVel;
//	private ArrayList<XYChart.Series> pidGraphSeriesVel = new ArrayList<>();
//	private ArrayList<XYChart.Series> pidGraphSeries = new ArrayList<>();
	private WarehouseRobotStatus status = WarehouseRobotStatus.Fault_E_Stop_pressed;
	private ObservableList<String> weights = FXCollections.observableArrayList("Aluminum", "Plastic");
	private ObservableList<String> sides = FXCollections.observableArrayList("25", "45");
	private ObservableList<String> pos = FXCollections.observableArrayList("1", "2");
	private double datas[] = null;
	private double irdata[] = null;

	private DecimalFormat formatter = new DecimalFormat();

	static InterfaceController me;
	private static ISimplePIDRobot robot;
	private static IRBE2002Robot rbe2002;
	private static IRBE2001Robot rbe2001;

	private int numPIDControllers = -1;
	private int currentIndex = 0;
	private static final int numPIDControllersOnDevice = 3;
	private File lastSearchedName = new File(
			System.getProperty("user.home") + "/" + "rbeFieldControllerLastSearchedRobot.txt");
	private CSVManager csv=new CSVManager();
	private double Azimuth =0;
	@FXML
	private void initialize() {
		me = this;
		formatter.setMaximumFractionDigits(6);

		assert connectTab != null : "fx:id=\"connectTab\" was not injected: check your FXML file 'MainScreen.fxml'.";
		assert teamName != null : "fx:id=\"teamName\" was not injected: check your FXML file 'MainScreen.fxml'.";
		assert connectToDevice != null : "fx:id=\"connectToDevice\" was not injected: check your FXML file 'MainScreen.fxml'.";

		assert pidTab != null : "fx:id=\"pidTab\" was not injected: check your FXML file 'MainScreen.fxml'.";
		assert pidGraph != null : "fx:id=\"pidGraph\" was not injected: check your FXML file 'MainScreen.fxml'.";
		assert kp != null : "fx:id=\"kp\" was not injected: check your FXML file 'MainScreen.fxml'.";
		assert ki != null : "fx:id=\"ki\" was not injected: check your FXML file 'MainScreen.fxml'.";
		assert kd != null : "fx:id=\"kd\" was not injected: check your FXML file 'MainScreen.fxml'.";
		assert pidConstUpdate != null : "fx:id=\"pidConstUpdate\" was not injected: check your FXML file 'MainScreen.fxml'.";
		assert pidChannel != null : "fx:id=\"pidChannel\" was not injected: check your FXML file 'MainScreen.fxml'.";
		assert setpoint != null : "fx:id=\"setpoint\" was not injected: check your FXML file 'MainScreen.fxml'.";
		assert setSetpoint != null : "fx:id=\"setSetpoint\" was not injected: check your FXML file 'MainScreen.fxml'.";
		assert position != null : "fx:id=\"position\" was not injected: check your FXML file 'MainScreen.fxml'.";

		pidManager = new GraphManager(pidGraph, 3);
		velManager = new GraphManager(pidGraphVel, 3);

		irChart.getData().add(new XYChart.Series());
		irChart.getData().add(new XYChart.Series());
		irChart.getData().add(new XYChart.Series());
		irChart.getData().add(new XYChart.Series());

		choiceBoxWeight.setValue(weights.get(0));
		choiceBoxWeight.setItems(weights);
		choiceBoxSide.setValue("25");
		choiceBoxSide.setItems(sides);
		choiceBoxPos.setValue("1");
		choiceBoxPos.setItems(pos);

		choiceBoxWeight.getSelectionModel().select(weights.get(0));

		stop.setDisable(true);
		// PLE.setDisable(true);
		// RHE.setDisable(true);
		send.setDisable(true);
		approveButton.setDisable(true);
		if (lastSearchedName.exists()) {
			try {
				String text = new String(Files.readAllBytes(lastSearchedName.toPath()));
				Platform.runLater(() -> teamName.setText(text));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void connectToDevice() {
		if (getRobot() == null) {
			connectToDevice.setDisable(true);
			new Thread(() -> {
				String name = teamName.getText();
				try {
					if (!lastSearchedName.exists())
						lastSearchedName.createNewFile();
					BufferedWriter writer = new BufferedWriter(new FileWriter(lastSearchedName));
					writer.write(name);
					writer.close();
					System.out.println("Writing to " + lastSearchedName.getAbsolutePath());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				try {
					setFieldSim(new RBE2001Robot(name, numPIDControllersOnDevice));
					// getFieldSim().setReadTimeout(1000);
					if (getRobot() != null) {
						Platform.runLater(() -> connectToDevice.setDisable(true));
						Platform.runLater(() -> teensyButton.setDisable(true));
						Platform.runLater(() -> {
							robotName.setText("Connected to: "+name);
							pidTab.setDisable(false);
							pidVelTab.setDisable(false);
						});

					}
				} catch (Exception ex) {
					// ex.printStackTrace();
					Platform.runLater(() -> robotName.setText( "Robot Not Found!"));
				}
				if (getRobot() == null) {
					Platform.runLater(() -> connectToDevice.setDisable(false));
				}

			}).start();
		}
	}

	@FXML
	void onConnect() {
		System.out.println("onConnect");
		connectToDevice();
	}

	@FXML
	void connectTeensy() {
		try {
			System.out.println("connectTeensy");
			if (getRobot() == null) {
				new Thread(() -> {
					try {
						setFieldSim(new RBE3001Robot(0x16C0, 0x0486, numPIDControllersOnDevice));
						// getFieldSim().setReadTimeout(1000);
						if (getRobot() != null) {
							Platform.runLater(() -> connectToDevice.setDisable(true));
							Platform.runLater(() -> teensyButton.setDisable(true));
							Platform.runLater(() -> {
								robotName.setText("Connected to: TeensyHID");
								pidTab.setDisable(false);
								pidVelTab.setDisable(false);
							});

						}
					} catch (Exception ex) {
						// ex.printStackTrace();
						Platform.runLater(() -> robotName.setText("TeensyHID Not Found!"));
					}
					if (getRobot() == null) {
						Platform.runLater(() -> connectToDevice.setDisable(false));
					}

				}).start();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void onSetGains() {
		double kpv = Double.parseDouble(kp.getText());
		double kiv = Double.parseDouble(ki.getText());
		double kdv = Double.parseDouble(kd.getText());
		// for (int i = 0; i < numPIDControllers; i++)
		robot.setPidGains(currentIndex, kpv, kiv, kdv);
	}

	@FXML
	void onSetSetpoint() {
		clearGraph();
		robot.setPidSetpoint(Integer.parseInt(setDuration.getText()),
				setType.getSelectionModel().getSelectedItem().equals("LIN") ? 0 : 1, currentIndex,
				Double.parseDouble(setpoint.getText()));

	}

	public ISimplePIDRobot getRobot() {
		return robot;
	}

	@SuppressWarnings("unchecked")
	private void updateIR(double[] pos) {
		for (int i = 0; i < 4; i++) {
			double x = pos[i * 2];
			double y = pos[i * 2 + 1];
			Series e = irChart.getData().get(i);
			e.getData().clear();
			e.getData().add(new XYChart.Data(x, y));
		}
	}

	private void setFieldSim(ISimplePIDRobot r) {
		// fieldSim.setReadTimeout(1000);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		InterfaceController.robot = r;
		Platform.runLater(() -> use2001.setDisable(false));
		Platform.runLater(() -> useIMU.setDisable(false));
		Platform.runLater(() -> useIR.setDisable(false));
		if (IRBE2001Robot.class.isInstance(robot))
			rbe2001 = (IRBE2001Robot) robot;
		if (IRBE2002Robot.class.isInstance(robot))
			rbe2002 = (IRBE2002Robot) robot;
		use2001.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (rbe2001 != null) {
				Platform.runLater(() -> use2001.setDisable(true));
				Platform.runLater(() -> useIMU.setDisable(true));
				Platform.runLater(() -> useIR.setDisable(true));

				rbe2001.add2001();
				robot.addEvent(2012, () -> {
					WarehouseRobotStatus tmp = rbe2001.getStatus();
					if (status != tmp) {
						status = tmp;
						System.out.println(" New Status = " + status.name());
						Platform.runLater(() -> {
							heartBeat.setText(status.name());
						});
						Platform.runLater(() -> {
							if (status == WarehouseRobotStatus.Waiting_for_approval_to_pickup
									|| status == WarehouseRobotStatus.Waiting_for_approval_to_dropoff)
								approveButton.setDisable(false);
							else
								approveButton.setDisable(true);

						});

					}
				});
				Platform.runLater(() -> tab2001Field.setDisable(false));
				Platform.runLater(() -> {
					stop.setDisable(false);
					// PLE.setDisable(false);
					// RHE.setDisable(false);
					send.setDisable(false);
					approveButton.setDisable(true);
				});
			}
		});
		if (rbe2002 != null) {
			useIMU.selectedProperty().addListener((observable, oldValue, newValue) -> {
				Platform.runLater(() -> useIMU.setDisable(true));
				Platform.runLater(() -> use2001.setDisable(true));
				rbe2002.addIMU();
				robot.addEvent(1804, () -> {
					if (datas == null)
						datas = new double[15];
					robot.readFloats(1804, datas);
					Platform.runLater(() -> {
						int base = 12;
						accelx.setText(formatter.format(datas[base + 0]));
						accely.setText(formatter.format(datas[base + 1]));
						accelz.setText(formatter.format(datas[base + 2]));
						base = 3;
						gyrox.setText(formatter.format(datas[base + 0]));
						gyroy.setText(formatter.format(datas[base + 1]));
						gyroz.setText(formatter.format(datas[base + 2]));
						base = 6;
						gravx.setText(formatter.format(datas[base + 0]));
						gravy.setText(formatter.format(datas[base + 1]));
						gravz.setText(formatter.format(datas[base + 2]));
						base = 9;
						Azimuth=datas[base + 2];
						eulx.setText(formatter.format(datas[base + 0]));
						euly.setText(formatter.format(datas[base + 1]));
						eulz.setText(formatter.format(Azimuth));

					});
				});
				Platform.runLater(() -> imutab.setDisable(false));
			});
			useIR.selectedProperty().addListener((observable, oldValue, newValue) -> {
				Platform.runLater(() -> useIR.setDisable(true));
				Platform.runLater(() -> use2001.setDisable(true));
				rbe2002.addIR();
				robot.addEvent(1590, () -> {
					try {
						if (irdata == null)
							irdata = new double[8];
						robot.readFloats(1590, irdata);
						Platform.runLater(() -> updateIR(irdata));
						// System.out.println("IR "+irdata);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				});

				Platform.runLater(() -> irtab.setDisable(false));
			});
		}
		robot.addEvent(1910, () -> {
			try {

				if (numPIDControllers != robot.getMyNumPid()) {
					numPIDControllers = robot.getMyNumPid();
					setUpPid();
				}
				double pos = robot.getPidPosition(currentIndex);
				double set = robot.getPidSetpoint(currentIndex);
				double vel = robot.getHardwareOutput(currentIndex);
				String positionVal = formatter.format(pos);
				// System.out.println(positionVal+"");
				;
				Platform.runLater(() -> position.setText(positionVal));
				Platform.runLater(() -> pidManager.updateGraph(pos, set, vel));
				csv.addLine(System.currentTimeMillis(),
						robot.getPidPosition(0), robot.getPidPosition(1), robot.getPidPosition(2), 
						robot.getVelocity(0), robot.getVelocity(1), robot.getVelocity(2),
						robot.getHardwareOutput(0), robot.getHardwareOutput(1), robot.getHardwareOutput(2),
						robot.getVelSetpoint(0), robot.getVelSetpoint(1), robot.getVelSetpoint(2),
						robot.getPidSetpoint(0), robot.getPidSetpoint(1), robot.getPidSetpoint(2),
						Azimuth);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		robot.addEvent(1822, () -> {
			try {

				if (numPIDControllers != robot.getMyNumPid()) {
					numPIDControllers = robot.getMyNumPid();
					setUpPid();
				}
				double pos = robot.getVelocity(currentIndex);
				double set = robot.getVelSetpoint(currentIndex);
				double hw = robot.getHardwareOutput(currentIndex);
				
				String positionVal = formatter.format(pos);
				// System.out.println(positionVal+"");
				;
				Platform.runLater(() -> velocityVal.setText(positionVal));
				Platform.runLater(() -> hardwareOut.setText(formatter.format(hw)));
				Platform.runLater(() -> posHwValue.setText(formatter.format(hw)));
				Platform.runLater(() -> velManager.updateGraph(pos, set, hw));
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		robot.addEvent(1857, () -> {
			try {
				System.out.print("\r\nPID config update for axis " + currentIndex + " values ");

				Platform.runLater(() -> kp.setText(formatter.format(robot.getKp(currentIndex))));

				Platform.runLater(() -> ki.setText(formatter.format(robot.getKi(currentIndex))));

				Platform.runLater(() -> kd.setText(formatter.format(robot.getKd(currentIndex))));

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		robot.updatConfig();

		robot.addEvent(1825, () -> {
			try {
				System.out.print("\r\nPID config update for axis " + currentIndex + " values ");

				Platform.runLater(() -> kpVel.setText(formatter.format(robot.getVKp(currentIndex))));
				Platform.runLater(() -> velKiField.setText(formatter.format(robot.getVKi(currentIndex))));
				Platform.runLater(() -> kdVel.setText(formatter.format(robot.getVKd(currentIndex))));

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});

	}

	private void setUpPid() {
		System.out.println("PID controller has " + robot.getNumPid() + " controllers");
		if (robot.getNumPid() > 0) {
			for (int i = 0; i < robot.getNumPid(); i++) {
				int index = i;
				Platform.runLater(() -> pidChannel.getItems().add(index));
				Platform.runLater(() -> pidChannelVelocity.getItems().add(index));
			}
			pidChannel.getSelectionModel().selectedIndexProperty().addListener((obs, old, newVal) -> {
				Platform.runLater(() -> clearGraph());
			});
			pidChannelVelocity.getSelectionModel().selectedIndexProperty().addListener((obs, old, newVal) -> {
				Platform.runLater(() -> clearGraph());
			});
			Platform.runLater(() -> pidChannel.setValue(0));
			Platform.runLater(() -> pidChannelVelocity.setValue(0));
			Platform.runLater(() -> setType.getItems().add("LIN"));
			Platform.runLater(() -> setType.getItems().add("SIN"));
			Platform.runLater(() -> setType.setValue("LIN"));
			pidTab.selectedProperty().addListener((obs, old, newVal) -> {
				System.out.println("Selecting pidTab");
				clearGraph();
			});
			pidVelTab.selectedProperty().addListener((obs, old, newVal) -> {
				System.out.println("Selecting pidVelTab");
				clearGraph();
			});

		}
		clearGraph();
	}

	private void clearGraph() {
		if (pidTab.isSelected()) {
			currentIndex = pidChannel.getSelectionModel().getSelectedItem().intValue();
		}
		if (pidVelTab.isSelected()) {
			SingleSelectionModel<Integer> model = pidChannelVelocity.getSelectionModel();
			Integer item = model.getSelectedItem();
			// try {
			currentIndex = item.intValue();

		}
		System.out.println("Set to channel " + currentIndex);
		pidManager.clearGraph(currentIndex);
		velManager.clearGraph(currentIndex);
		robot.updatConfig();
	}

	public static void disconnect() {
		if (me.getRobot() != null)
			me.getRobot().disconnect();
	}

	@FXML
	void onSetVelocity() {
		clearGraph();
		double vel = Double.parseDouble(setpointVelocity.getText());
		if (vel != 0)
			robot.setVelocity(currentIndex, Double.parseDouble(setpointVelocity.getText()));
		else
			robot.stop(currentIndex);

	}

	@FXML
	void onSetGainsVelocity() {
		double kpv = Double.parseDouble(kpVel.getText());
		double kiv = Double.parseDouble(velKiField.getText());
		double kdv = Double.parseDouble(kdVel.getText());
		robot.setVelocityGains(currentIndex, kpv, kiv,kdv);
	}

	@FXML
	void onApprove() {
		System.out.println("approve");
		if (rbe2001 != null)
			rbe2001.approve();

	}

	@FXML
	void sendLocation() {
		System.out.println("sendLocation");
		double material;
		if (choiceBoxWeight.getSelectionModel().getSelectedItem().contains(weights.get(0))) {
			material = 1;
		} else {
			material = 2;
		}
		double angle = Double.parseDouble(choiceBoxSide.getSelectionModel().getSelectedItem());
		double position = Double.parseDouble(choiceBoxPos.getSelectionModel().getSelectedItem());
		if (rbe2001 != null)
			rbe2001.pickOrder(material, angle, position);

	}

	@FXML
	void start() {
		System.out.println("start");
		if (rbe2001 != null)
			rbe2001.clearFaults();

	}

	@FXML
	void stop() {
		System.out.println("stop");
		if (rbe2001 != null)
			rbe2001.estop();

	}

	@FXML
	void onPidExport() {
		try {
			csv.writeToFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void onVelExport() {
		try {
			csv.writeToFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
