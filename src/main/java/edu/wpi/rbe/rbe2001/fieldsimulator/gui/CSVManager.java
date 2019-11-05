package edu.wpi.rbe.rbe2001.fieldsimulator.gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Date;
public class CSVManager {
	long timestampLast;
	ArrayList<double[]> hashMap= new ArrayList<double[]>(); 

	public void addLine(long timestamp, double pos0, double pos1, double pos2, double vel0, double vel1, double vel2,
			double hw0, double hw1, double hw2, double velsetpoint0, double velsetpoint1, double velsetpoint2,
			double setpoint0, double setpoint1, double setpoint2, double Azimuth) {
		if(timestampLast+50>timestamp)
			return;
		double[] line = new double[] { timestamp,pos0, pos1, pos2, vel0, vel1, vel2, hw0, hw1, hw2, velsetpoint0, velsetpoint1,
				velsetpoint2, setpoint0, setpoint1, setpoint2,Azimuth };
		hashMap.add( line);
		if(hashMap.size()>100000)
			 writeToFile();
		
	}

	public void writeToFile() {
		new Thread(()->{
			File desktop = new File(System.getProperty("user.home")+"/Desktop/");
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
			String timestamp =dateFormat.format(new Date());
			System.out.println(timestamp);
			String filename = desktop.getAbsolutePath()+"/Motor-Data_"+timestamp+".csv";
			System.out.println(filename);
			File exportFile = new File(filename);
			if(!exportFile.exists())
				try {
					exportFile.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			String content = "timestamp,pos0, pos1, pos2, vel0, vel1, vel2, hw0, hw1, hw2, velsetpoint0, velsetpoint1,velsetpoint2, setpoint0, setpoint1, setpoint2,Azimuth\n";
			for(int j=0;j<hashMap.size();j++) {
				double[] line=hashMap.get(j);
				for(int i=0;i<line.length;i++)
					content+=line[i]+",";
				
				content+="\n";
			}
			PrintWriter out;
			try {
				out = new PrintWriter(filename);
				out.println(content);
				out.flush();
				out.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			hashMap.clear();
		}).start();
	}

}
