package Coords;

import Geom.Point3D;

/**
 * this class represents a basic coordinate system converter that implements coords_converter.
 */

public class MyCoords implements coords_converter {
	/**
	 * this function is an empty constructor
	 */
	public MyCoords(){
		
	}
	/**
	 * this function receives a number in meters converts meters to coordinates
	 * @param x is the the number you wish to use the function on
	 * @return a double representing the number having changed into coordinate
	 */
	public double conM2C(double x) {
		x/=6371000;
		x=Math.asin(x);
		x/=Math.PI;
		x*=180;
		return x;
	}
	/**
	 *  this function converts coordinates to meters
	 * @param x1 first 
	 * @param x2 last
	 * @return double which represnetes the distance
	 */
	public double conC2M(double x1,double x2) {
		double ans=x2-x1;
		ans=(ans*Math.PI)/180;
		ans=(Math.sin(ans))*6371000;
		return ans;
	}
	/** computes a new point which is the gps point transformed by a 3D vector*/
	public Point3D add(Point3D gps, Point3D local_vector_in_meter) {
		Point3D newP=new Point3D(gps);
		newP.add(
				this.conM2C(local_vector_in_meter.x()),
				this.conM2C(local_vector_in_meter.y())/
					(Math.cos((gps.x()*Math.PI)/180)),
				local_vector_in_meter.z());
		return newP;
	}

	/** computes the 3D distance  between the two gps like points */
	public double distance3d(Point3D gps0, Point3D gps1) {	
		Point3D dis=this.vector3D(gps0, gps1);
		double ans=(dis.x()*dis.x())+
				   (dis.y()*dis.y())+
				    dis.z()*dis.z();
		return Math.sqrt(ans);
	}

	/** computes the 3D vector (in meters) between two gps like points */
	public Point3D vector3D(Point3D gps0, Point3D gps1) {
		double metx=conC2M(gps0.x(),gps1.x());
		double mety=conC2M(gps0.y(),gps1.y())*Math.cos(gps0.x()*Math.PI/180);
		double metz=gps1.z()-gps0.z();
		return new Point3D(metx,mety,metz);
	}

	/** computes the polar representation of the 3D vector be gps0-->gps1 
	 * Note: this method should return an azimuth (aka yaw), elevation (pitch), and distance*/
	public double[] azimuth_elevation_dist(Point3D gps0, Point3D gps1) {  
		double []ans=new double[3];
		ans[2]=this.distance3d(gps0, gps1);
		ans[1]=Math.asin((gps1.z()-gps0.z())/ans[2]);
		ans[1]=ans[1]/Math.PI*180;
		double y=gps1.y()-gps0.y();
		double x=gps1.x()-gps0.x();
		double deg=Math.atan2(y, x);
		deg=Math.toDegrees(deg);
		deg=90-deg;
		ans[0]=deg;
		return ans;
	}

	/**
	 * return true if this point is a valid lat, lon , lat coordinate: [-180,+180],[-90,+90],[-450, +inf]
	 * @param p is the point the function works on
	 * @return true if this point is vaild
	 */
	public boolean isValid_GPS_Point(Point3D p) {
		return valid(p.x(),'x')&&valid(p.y(),'y')&&valid(p.z(),'z');
	}
	
	private boolean valid(double a,char c) {
		switch(c){
		case 'x':return a<90&&a>-90;
		case 'y':return a<180&&a>-180;
		case 'z':return a>-450;
		}
		return false;
	}

}
