	package Game;

import java.util.Iterator;

import Coords.MyCoords;
import Geom.Point3D;

public class PackMan {
	private Point3D gps;
	private int speed;
	private double radius;
	private Path path;
	public PackMan(Point3D gps,int speed,int radius) {
		this.gps=new Point3D(gps);
		this.speed=speed;
		this.radius=radius;
		this.path=new Path();
		path.add(gps);
	}
	public PackMan(Point3D gps) {
		this.gps=new Point3D(gps);
		this.speed=20;
		this.radius=1;
		this.path=new Path();
		path.add(gps);
	}
	public String toString() {
		return gps.y()+","+gps.x()+","+gps.z()+","+speed+","+radius+",";
	}
	public Path getPath() {
		return new Path(path);
	}
	/**
	 * adds a point to the path of the packman
	 * @param p the next gps point the packman needs to move
	 */
	public void add(Point3D p) {
		path.add(p);
	}
	public int getSpeed() {
		return speed;
	}
	public double pathDist() {
		return path.distance();
	}
	public double Time(double dist) {
		double ans=dist/speed;
		return ans;
	}
	public double getRadius() {
		return radius;
	}
	/**
	 * gets the last gps on the path 
	 * @return last Point3D in the Point3D array in path
	 */
	public Point3D getLastGps() {
		return path.getPoints().get(path.getPoints().size()-1);
	}
	/**
	 *  returns the point that the packman should be in the current time
	 * @param time the amout time the packman traveled
	 * @return Point3D of the current location of the packman
	 */
	public Point3D currentPoint(double time) {
		if(path.getPoints().size()==1)return gps;//check if the game started
		Point3D[] range=new Point3D[2];
		MyCoords c=new MyCoords();
		double searchDistance1=0;
		double searchDistance2=0;
		double distance=time*speed;
		double distanceX0=0;
		double distanceX1=0;
		Iterator<Point3D> i=path.iterator();
		Point3D pi=i.next();
		while(i.hasNext()) {//between which 2 points the packman is located
			Point3D temp=i.next();
			searchDistance2=c.distance3d(pi, temp);
			searchDistance2+=searchDistance1;
			if(distance>=searchDistance1&&distance<=searchDistance2) {
				range[0]=pi;
				range[1]=temp;
				distanceX0=searchDistance1;
				distanceX1=searchDistance2;
			}
			pi=temp;
			searchDistance1=searchDistance2;
		}
		if(distance>=searchDistance1)return this.getLastGps();//if the time exceeds the time to complete the game
		double ratio1=distance-distanceX0;
		double ratio2=distanceX1-distance;
		double newX=ratio2*range[0].x()+ratio1*range[1].x();
		double newY=ratio2*range[0].y()+ratio1*range[1].y();
		double newZ=ratio2*range[0].z()+ratio1*range[1].z();
		newX/=(ratio1+ratio2);
		newY/=(ratio1+ratio2);
		newZ/=(ratio1+ratio2);
		Point3D ans=new Point3D(newX,newY,newZ);
		return ans;
	}
	public void setGPS(Point3D gps) {
		this.gps=gps;
	}
	public void resetPath() {
		this.path=new Path();
		this.add(this.gps);
	}
	public Point3D getGPS() {
		return this.gps;
	}
	public void setRadius(int radius) {
		this.radius=radius;
	}
	public void setSpeed(int speed) {
		this.speed=speed;
	}
	public void move(Point3D gps,int width,int height) {
		double deltaX=gps.x()-this.getGPS().x();
		double deltaY=gps.y()-this.getGPS().y();
		double deltaZ=gps.z()-this.getGPS().z();
		deltaX/=width;
		deltaY/=height;
		deltaX*=this.getSpeed();
		deltaY*=this.getSpeed();
		Point3D ans=new Point3D(this.getGPS().x()+deltaX,this.getGPS().y()+deltaY,this.getGPS().z()+deltaZ);
		this.setGPS(ans);
	}
}
