package edu.wpi.rbe.rbe2001.fieldsimulator.gui;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
@SuppressWarnings("restriction")
public class GraphManager {
	private ArrayList<XYChart.Series> pidGraphSeries=new ArrayList<>();
	private LineChart<Double, Double> pidGraph;
	private double start = ((double) System.currentTimeMillis()) / 1000.0;
	private long lastPos;
	private long lastSet;
	private long lastHw;
//	private HashMap<Integer,ArrayList<Double>> posExp = new HashMap<>();
//	private HashMap<Integer,ArrayList<Double>> setExp = new HashMap<>();
//	private HashMap<Integer,ArrayList<Double>> hwExp = new HashMap<>();
//	private HashMap<Integer,ArrayList<Double>> timeExp = new HashMap<>();
	private int currentIndex=0;
	private int numPid=0;
	public GraphManager(LineChart<Double, Double> g, int num ) {
		pidGraph=g;
		numPid=num;
		for (int i = 0; i < numPid; i++) {
			Series e = new XYChart.Series();

			pidGraphSeries.add(i, e);
			pidGraph.getData().add(e);
//			posExp.put(i,new ArrayList<>());
//			setExp.put(i,new ArrayList<>());
//			hwExp.put(i,new ArrayList<>());
//			timeExp.put(i,new ArrayList<>());
		}
		pidGraph.getXAxis().autoRangingProperty().set(true);
		
	}
	
	@SuppressWarnings("unchecked")
	public  void updateGraph(double pos, double set, double hw) {
		if (pidGraphSeries.size() == 0)
			return;
		double now = ((double) System.currentTimeMillis()) / 1000.0 - start;
		long thispos = (long) (pos*100.0);
		long thisSet = (long) (set*100.0);
		long thisHw  = (long) (hw*100.0);
		if (thispos != lastPos || thisSet != lastSet || thisHw!=lastHw) {
			pidGraphSeries.get(0).getData().add(new XYChart.Data(now - 0.0001, pos));
			pidGraphSeries.get(1).getData().add(new XYChart.Data(now - 0.0001, set));
			pidGraphSeries.get(2).getData().add(new XYChart.Data(now - 0.0001, hw));
			lastSet = thisSet;
			lastPos = thispos;
			lastHw=thisHw;
			pidGraphSeries.get(0).getData().add(new XYChart.Data(now, pos));
			pidGraphSeries.get(1).getData().add(new XYChart.Data(now, set));
			pidGraphSeries.get(2).getData().add(new XYChart.Data(now , hw));
//			posExp.get(currentIndex).add(pos);
//			setExp.get(currentIndex).add(set);
//			hwExp.get(currentIndex).add(hw);
//			timeExp.get(currentIndex).add(now);
//			if(posExp.get(currentIndex).size()>5000) {
//				posExp.get(currentIndex).remove(0);
//				setExp.get(currentIndex).remove(0);
//				hwExp.get(currentIndex).remove(0);
//				timeExp.get(currentIndex).remove(0);
//			}
		}
		for (Series s : pidGraphSeries) {
			while (s.getData().size() > 2000) {
				s.getData().remove(0);
			}
		}
	}
	public void clearGraph(int currentIndex) {
		for (Series s : pidGraphSeries) {
			s.getData().clear();

		}
		this.currentIndex=currentIndex;
	}


}
