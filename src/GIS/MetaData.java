package GIS;

import Geom.Point3D;
/**
 * this class represents the MetaData of elements
 */
public class MetaData implements Meta_data{
	private long UTC;
	private Point3D Orientation;
	private String data;
	/**
	 * full constructor
	 */
	public MetaData(long UTC,String data) {
		this.UTC=UTC;
		this.data=data;
	}
	/**
	 * copy constructor
	 */
	public MetaData(Meta_data Mdata) {
		this.UTC=Mdata.getUTC();
		this.data=Mdata.toString();
		//Orientation=new Point3D(Mdata.get_Orientation());				/////////////////is this needed?///////////////////////////
	}

	/** return a long representing this UTC */
	public long getUTC() {
		return UTC;
	}

	/** return a point representing this orientation */
	public Point3D get_Orientation() {
		return new Point3D(Orientation);
	}
	/** return a String representing this data */
	public String toString() {
		return data;
	}
	/** returns true if MetaData is equals to speicifed MetaData */
	public boolean Equals(Meta_data Mdata) {
		return (this.toString().equalsIgnoreCase(Mdata.toString())&&UTC==Mdata.getUTC());
	}
}
