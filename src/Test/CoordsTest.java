package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Coords.MyCoords;
import Geom.Point3D;

class CoordsTest {

	@Test
	void conM2CTest() {
		MyCoords testC=new MyCoords();
		double t=testC.conM2C(337.6989921);
		assertEquals(t,0.0030370000003472125);
	}


	@Test
	void conC2MTest() {
		MyCoords testC=new MyCoords();
		double t=testC.conC2M(32.103315, 32.106352);
		assertEquals(t,337.69899206128815);
	}



	@Test
	void addTest() {
		MyCoords testC=new MyCoords();
		Point3D gps=new Point3D(32.103315,35.209039,670);
		Point3D vec=new Point3D(337.69899206128815,-359.2492069,-20);
		Point3D newP=testC.add(gps, vec);
		assertTrue(testC.distance3d(new Point3D(32.106352,35.20522500000121,650.0),newP)==0);
	}

	@Test
	void distance3dTest() {
		MyCoords testC=new MyCoords();
		Point3D gps0=new Point3D(32.103315,35.209039,670);
		Point3D gps1=new Point3D(32.106352,35.205225,650);
		assertEquals(testC.distance3d(gps0, gps1), 493.45780156501763);
	}

	@Test
	void vector3DTest() {
		MyCoords testC=new MyCoords();
		Point3D gps0=new Point3D(32.103315,35.209039,670);
		Point3D gps1=new Point3D(32.106352,35.205225,650);
		Point3D vec1=testC.vector3D(gps0, gps1);
		Point3D vec2=new Point3D(337.69899206128815,-359.24920693881893,-20.0);
		assertEquals(vec1.toString(), vec2.toString());
	}

	@Test
	void azimuth_elevation_dist_test() {
		MyCoords testC=new MyCoords();
		Point3D gps0=new Point3D(32.103315,35.209039,670);
		Point3D gps1=new Point3D(32.103400,35.209039,650);
		double[]a=testC.azimuth_elevation_dist(gps0, gps1);
		double[]b= { 90.0,-2.322852232927616,493.45780156501763};
		boolean flag=true;
		for(int i=0;i<3;i++)if(a[i]!=b[i])flag=false;
		System.out.println(a[0]);
		assertTrue(flag);
		
	}

	@Test
	void isValid_GPS_PointTest1() {
		MyCoords testC=new MyCoords();
		Point3D gps0=new Point3D(32.103315,35.209039,670);
		assertTrue(testC.isValid_GPS_Point(gps0));
	}

	@Test
	void isValid_GPS_PointTest2() {
		MyCoords testC=new MyCoords();
		Point3D gps0=new Point3D(-190.103315,35.209039,670);
		assertFalse(testC.isValid_GPS_Point(gps0));
	}
}