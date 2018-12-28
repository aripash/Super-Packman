package Game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import Coords.MyCoords;
import Geom.Point3D;

public class Game {
	private ArrayList<PackMan> packmen;
	private ArrayList<Fruit> fruits;

	public Game(String path) {
		packmen=new ArrayList<PackMan>();
		fruits=new ArrayList<Fruit>();
		read(path);
	}
	public Game() {
		packmen=new ArrayList<PackMan>();
		fruits=new ArrayList<Fruit>();
	}
	/**
	 * using the path it reads the csv file and builds packmen and fruits
	 */
	public void read(String path) {
		String line = "";
		String cvsSplitBy = ",";

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			br.readLine();
			while ((line = br.readLine()) != null) {
				String[] lineData = line.split(cvsSplitBy);
				Point3D gps=new Point3D(Double.parseDouble(lineData[3]),Double.parseDouble(lineData[2]),Double.parseDouble(lineData[4]));
				if(lineData[0].equalsIgnoreCase("F")) {
					fruits.add(new Fruit(gps));
				}
				else if(lineData[0].equalsIgnoreCase("P")) {
					packmen.add(new PackMan(gps,(int)Double.parseDouble(lineData[5]),(int)Double.parseDouble(lineData[6])));
				}
			}
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	/**
	 * writes a csv file using the packmen and fruits in the folder that the path points to
	 */
	public void write(String path) {
		PrintWriter pw = null;
		try 
		{
			pw = new PrintWriter(new File(path));
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
			return;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("Type,id,Lat,Lon,Alt,Speed,Radius,\n");
		Iterator<PackMan> i1=packmen.iterator();
		Iterator<Fruit> i2=fruits.iterator();
		int j=0;
		while(i1.hasNext()) {
			sb.append("P,");
			sb.append(j+",");
			j++;
			sb.append(i1.next().toString());
			sb.append("\n");
		}
		j=0;
		while(i2.hasNext()) {
			sb.append("F,");
			sb.append(j+",");
			j++;
			sb.append(i2.next().toString());
			sb.append("\n");
		}
		pw.write(sb.toString());
		pw.close();
	}
	public ArrayList<Fruit> getFruits(){
		return new ArrayList<Fruit>(fruits);
	}
	public ArrayList<PackMan> getPackmen(){//no new so we can change the path for each packman
		return packmen;
	}
	public void addPackMan(PackMan p) {
		packmen.add(p);
	}
	public void addFruit(Fruit f) {
		fruits.add(f);
	}
	/**
	 * sets the time the fruit got eaten
	 * @param min the fruit that needs to be set
	 * @param time the time that the fruit got eaten
	 */
	public void addTime(Fruit min, double time) {
		// TODO Auto-generated method stub
		Iterator<Fruit> i=fruits.iterator();
		boolean flag=true;
		MyCoords c=new MyCoords();
		while(i.hasNext()&&flag) {
			Fruit temp=i.next();
			if(c.distance3d(min.getFruit(), temp.getFruit())==0.0) {
				temp.setTime(time);
				flag=false;
			}
		}
	}
	/**
	 * gets the time that the last fruit got eaten
	 * @return time that the fruit got eaten last
	 */
	public int lastTime() {
		Iterator<Fruit> i=fruits.iterator();
		int maxTime=0;
		while(i.hasNext()) {
			Fruit fi=i.next();
			int temp=(int) fi.getTime();
			if(temp>maxTime)maxTime=temp;
		}
		return maxTime;
	}
}
