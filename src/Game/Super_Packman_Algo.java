package Game;

import java.util.ArrayList;
import java.util.Iterator;

import Coords.MyCoords;
import Geom.Point3D;

public class Super_Packman_Algo {
	public static double recommandedRotation(ArrayList<String> data) {
		double rotate;
		ArrayList<Point3D> fruits=new ArrayList<Point3D>();
		ArrayList<String> Ghoests=new ArrayList<String>();
		String row=data.get(0);
		String [] collum=row.split(",");
		double x=Double.parseDouble(collum[3]);
		double y=Double.parseDouble(collum[2]);
		double z=Double.parseDouble(collum[4]);
		Point3D packman=new Point3D(x,y,z);
		for(int i=1;i<data.size();i++) {
			row=data.get(i);
			collum=row.split(",");
			if(collum[0].equalsIgnoreCase("F")) {
				x=Double.parseDouble(collum[3]);
				y=Double.parseDouble(collum[2]);
				z=Double.parseDouble(collum[4]);
				fruits.add(new Point3D(x,y,z));
			}
			else if(collum[0].equalsIgnoreCase("G"))
				Ghoests.add(row);
		}
		Point3D closeFruit=closestFruit(packman,fruits);
		MyCoords c=new MyCoords();
		double[] AED=c.azimuth_elevation_dist(packman, closeFruit);
		rotate=AED[0];
		double angle1=rotate+45;
		double angle2=rotate-45;
		for(int i=0;i<Ghoests.size();i++) {
			row=Ghoests.get(i);
			collum=row.split(",");
			x=Double.parseDouble(collum[3]);
			y=Double.parseDouble(collum[2]);
			z=Double.parseDouble(collum[4]);
			Point3D tempGhoest=new Point3D(x,y,z);
			if(c.distance3d(packman, tempGhoest)<=Double.parseDouble(collum[6])+1) {
				AED=c.azimuth_elevation_dist(packman, tempGhoest);
				double tempAzi=AED[0];
				if(tempAzi>angle2&&tempAzi<rotate)rotate+=5;
				else if(tempAzi<angle1&&tempAzi>rotate)rotate-=5;
				else if(tempAzi==rotate)rotate-=5;
			}
		}
		return rotate;
	}
	private static Point3D closestFruit(Point3D packman, ArrayList<Point3D> fruits) {
		MyCoords c=new MyCoords();
		Iterator<Point3D> i=fruits.iterator();
		Point3D min=i.next();
		while(i.hasNext()) {
			Point3D fi=i.next();
			if(c.distance3d(packman, fi)<c.distance3d(packman, min))min=fi;
		}
		return min;

	}
}
