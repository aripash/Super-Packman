package Game;

import Geom.Point3D; 

public class Fruit{
private Point3D gps;
private double time=0;//time eaten

public Fruit(Point3D gps) {
	this.gps=new Point3D(gps);
}
public String toString() {
	return gps.y()+","+gps.x()+","+gps.z()+",";
}
public Point3D getFruit() {
	return gps;
}
public double getTime() {
	return time;
	}
public void setTime(double time) {
	this.time=time;
}
}
