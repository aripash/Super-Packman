package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import GIS.MetaData;

class MetaDataTest {

	@Test
	void getUTCTest() {
		MetaData md=new MetaData(111,"test");
		assertEquals(111, md.getUTC());
	}
	@Test
	void toStringTest() {
		MetaData md=new MetaData(111,"test");
		assertEquals(md.toString(), "test");
	}
	@Test
	void EqualsTest() {
		MetaData md=new MetaData(111,"test");
	}
}
