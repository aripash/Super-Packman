package Game;

import java.awt.image.BufferedImage;
import Coords.MyCoords;
import Geom.Point3D;

public class Map {
private	BufferedImage mapImage;
private int width;
private int height;
	public Map(BufferedImage mage) {
		this.mapImage=mage;
		this.width=mapImage.getWidth();
		this.height=mapImage.getHeight();
	}
	public Map() {}
	/**
	 * converts points in pixels to point3D
	 * @param pixX x of the pixel(x,y)
	 * @param pixY y of the pixel (x,y)
	 * @param alt the altitude of the point
	 * @return point as a point3D type
	 */
public Point3D convP2C(int pixX,int pixY,double alt) {
	
	double diffX=pixX/(double)width;
	double diffY=pixY/(double)height;
	diffX*=0.009531;//the difference in longitude between the top left and top right (32.105733,35.202401)-(32.105685,35.211932)
	diffY*=-0.00383;//the difference in latitude between the top left and bottom left(32.105733,35.202401)-(32.101903,35.202338)
	diffX+=35.202401;
	diffY+=32.105733;
	return new Point3D(diffX,diffY,alt);
}
/**
 * Converts point3D to array that represent pixel
 * @param gps point3D
 * @return point represented as pixel [x,y]
 */
public int [] convC2P(Point3D gps) {
	double pixX=gps.x();
	double pixY=gps.y();
	pixX-=35.202401;
	pixY=32.105733-pixY;
	pixX/=0.009531;
	pixY/=0.00383;
	pixX*=width;
	pixY*=height;
	int[]ans= {(int)pixX,(int)pixY};
	return ans;
}
/**
 * returns data between two pixels
 * @param x1 x of the first pixel(x,y)
 * @param y1 y of the first pixel(x,y)
 * @param alt1
 * @param x2 x of the second pixel(x,y)
 * @param y2 t of the second pixel(x,y)
 * @param alt2
 * @return[azimuth,elevation,distance] between two points
 */
public double[] distance(int x1,int y1,double alt1,int x2,int y2,double alt2) {
	Point3D a=convP2C(x1,y1,alt1);
	Point3D b=convP2C(x2,y2,alt2);
	MyCoords cord=new MyCoords();
	return cord.azimuth_elevation_dist(a, b);
}
public void setWidth(int width) {
	this.width=width;
}
public void setHeight(int height) {
	this.height=height;
}
}
