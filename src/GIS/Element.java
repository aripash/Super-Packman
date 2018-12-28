package GIS;

import Coords.MyCoords;
import Geom.Geom_element;
import Geom.Point3D;
/**
 * this class represents a GIS element with geometric representation and meta data
 *
 */
public class Element implements GIS_element {
	private Point3D Geom;
	private MetaData Data;
	
	/** this function is a constructor that creates Element using a point */
	public Element(Point3D gps) {
		this.Geom=new Point3D(gps);
	}
	/** this function is a constructor that creates Element using a point and MetaData*/
	public Element(Point3D gps,Meta_data Data) {
		this.Geom=new Point3D(gps);
		this.Data=new MetaData(Data);
	}
	/** this function is a copy constructor  */
	public Element(GIS_element el) {
		this.Geom=(Point3D) el.getGeom();
		this.Data=(MetaData) el.getData();
	}
	/** this function is used to get Element's point */
	public Geom_element getGeom() {
		// TODO Auto-generated method stub
		return Geom;
	}

	/** this function is used to get Element's meta data */
	public Meta_data getData() {
		// TODO Auto-generated method stub
		return new MetaData (Data);
	}

	/** this function translates the element along a vector */
	public void translate(Point3D vec) {
		MyCoords coord=new MyCoords();
		this.Geom=coord.add(this.Geom, vec);
		
	}
	/**
	 * basic function to see object Element in string form
	 */
	public String toString() {
		String s="";
		s+=Data.getUTC()+" ";
		s+=Data.toString()+" ";
		s+=Geom.toString()+"\n";
		return s;
	}

}
