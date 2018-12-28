package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import GIS.Element;
import GIS.MetaData;
import Geom.Point3D;

class ElementTest {

	@Test
	void getGeomTest() {
		Element el=new Element(new Point3D(1,2,3),new MetaData(111,"test"));
		assertEquals(el.getGeom().toString(), new Point3D(1,2,3).toString());
	}
	@Test
	void getDataTest() {
		Element el=new Element(new Point3D(1,2,3),new MetaData(111,"test"));
		assertEquals(el.getData().toString(), new MetaData(111,"test").toString());
	}
	@Test
	void translateTest() {
		Element el=new Element(new Point3D(1,2,3),new MetaData(111,"test"));
		el.translate(new Point3D(1,2,3));
		assertEquals(el.getGeom().toString(), new Point3D(2.0,4.0,6.0).toString());
	}
	@Test
	void toStringTest() {
		Element el=new Element(new Point3D(1,2,3),new MetaData(111,"test"));
		String s=el.toString();
		s=s.trim();
		assertTrue(s.equalsIgnoreCase("111 test 1.0,2.0,3.0"));
	}
}
