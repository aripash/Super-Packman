package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import File_format.Placemark;

class PlacemarkTest {

	@Test
	void getNameTest() {
		Placemark p=new Placemark();
		p.setName("Test");
		assertEquals(p.getName(), "Test");
	}
	@Test
	void setNameTest() {
		Placemark p=new Placemark();
		p.setName("Test");
		assertEquals(p.getName(), "Test");
	}
	@Test
	void getDescriptionTest() {
		Placemark p=new Placemark();
		p.setDescription("test2");
		assertEquals(p.getDescription(), "test2");
	}
	@Test
	void setDescriptionTest() {
		Placemark p=new Placemark();
		p.setDescription("test2");
		assertEquals(p.getDescription(), "test2");
	}
	@Test
	void getPointTest() {
		Placemark p=new Placemark();
		p.setPoint("1,0,1");
		assertEquals(p.getPoint(),"1,0,1");
	}
	@Test
	void setPointTest() {
		Placemark p=new Placemark();
		p.setPoint("1,0,1");
		assertEquals(p.getPoint(),"1,0,1");
	}
	@Test
	void getTimeTest() {
		Placemark p=new Placemark();
		p.setTime("1:00");
		assertEquals(p.getTime(),"1:00");
	}
	@Test
	void setTimeTest() {
		Placemark p=new Placemark();
		p.setTime("1:00");
		assertEquals(p.getTime(),"1:00");
	}

}
