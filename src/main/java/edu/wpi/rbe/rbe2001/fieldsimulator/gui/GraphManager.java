package edu.wpi.rbe.rbe2001.fieldsimulator.gui;

import java.util.ArrayList;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
public class GraphManager {
	private ArrayList<XYChart.Series> pidGraphSeries=new ArrayList<>();
	private LineChart<Double, Double> pidGraph;
	private double start = ((double) System.currentTimeMillis()) / 1000.0;
	private long lastPos;
	private long lastSet;
	private long lastHw;
	public GraphManager(LineChart<Double, Double> g) {
		pidGraph=g;
		for (int i = 0; i < 3; i++) {
			Series e = new XYChart.Series();

			pidGraphSeries.add(i, e);
			pidGraph.getData().add(e);
		}
		pidGraph.getXAxis().autoRangingProperty().set(true);
	}
	
	@SuppressWarnings("unchecked")
	public  void updateGraph(double pos, double set, double hw) {
		if (pidGraphSeries.size() == 0)
			return;
		double now = ((double) System.currentTimeMillis()) / 1000.0 - start;
		long thispos = (long) pos;
		long thisSet = (long) set;
		long thisHw  = (long) hw;
		if (thispos != lastPos || thisSet != lastSet || thisHw!=lastHw) {
			pidGraphSeries.get(0).getData().add(new XYChart.Data(now - 0.0001, lastPos));
			pidGraphSeries.get(1).getData().add(new XYChart.Data(now - 0.0001, lastSet));
			pidGraphSeries.get(2).getData().add(new XYChart.Data(now - 0.0001, lastHw));
			lastSet = thisSet;
			lastPos = thispos;
			lastHw=thisHw;
			pidGraphSeries.get(0).getData().add(new XYChart.Data(now, pos));
			pidGraphSeries.get(1).getData().add(new XYChart.Data(now, set));
			pidGraphSeries.get(2).getData().add(new XYChart.Data(now , hw));
		}
		for (Series s : pidGraphSeries) {
			while (s.getData().size() > 2000) {
				s.getData().remove(0);
			}
		}
	}
	public void clearGraph() {
		for (Series s : pidGraphSeries) {
			s.getData().clear();

		}
	}
}
