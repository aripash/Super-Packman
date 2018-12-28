package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import File_format.Document;
import File_format.Placemark;

class DocumentTest {

	@Test
	void getNameTest() {
		Document d=new Document();
		d.setName("test");
		assertEquals(d.getName(), "test.kml");
	}
	@Test
	void setNameTest() {
		Document d=new Document();
		d.setName("test");
		assertEquals(d.getName(), "test.kml");
	}
	@Test
	void getPlacemarkTest() {
		Document d=new Document();
		ArrayList<Placemark> ar=new ArrayList<Placemark>();
		Placemark p=new Placemark();
		p.setDescription("a");
		p.setName("1");
		p.setPoint("1,1");
		p.setTime("65");
		ar.add(p);
		d.setPlacemark(ar);
		assertTrue(d.getPlacemark().equals(ar));
	}
	@Test
	void setPlacemarkTest() {
		Document d=new Document();
		ArrayList<Placemark> ar=new ArrayList<Placemark>();
		Placemark p=new Placemark();
		p.setDescription("a");
		p.setName("1");
		p.setPoint("1,1");
		p.setTime("65");
		ar.add(p);
		d.setPlacemark(ar);
		assertTrue(d.getPlacemark().equals(ar));
	}

}
