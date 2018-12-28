package Game;

import java.util.ArrayList;
import java.util.Iterator;

import Coords.MyCoords;
import Geom.Point3D;

public class Path {
private ArrayList<Point3D> points;

public Path() {
	points=new ArrayList<Point3D>();
}
public Path(Path path) {
	points=new ArrayList<Point3D>(path.getPoints());
}
/**
 * adds a gps point to the path of the packman
 * @param p gps point
 */
public void add(Point3D p) {
	points.add(p);
}
/**
 * calculates the distance the packman needs to travel
 * @return the sum of all the distances between all the gps points in its path
 */
public double distance() {
	double ans=0;
	MyCoords c=new MyCoords();
	Iterator<Point3D> i=points.iterator();
	Point3D tempPoint1=i.next();
	while(i.hasNext()) {
		Point3D tempPoint2=i.next();
		ans+=c.distance3d(tempPoint1, tempPoint2);
		tempPoint1=tempPoint2;
	}
	return ans;
}
public Iterator<Point3D> iterator(){
	return points.iterator();
}
public String toString() {
	return points.toString();
}
public ArrayList<Point3D> getPoints(){
	return new ArrayList<Point3D>(points);
}
}
