package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import GIS.MetaData;
import GIS.Project;

class ProjectTest {

	@Test
	void get_Meta_dataTest() {
		Project p=new Project(new MetaData(111,"test"));
		assertEquals(p.get_Meta_data().toString(), "test");
	}
	@Test
	void toStringTest() {
		Project p=new Project(new MetaData(111,"test"));
		assertEquals(p.toString(), "111 test ");
	}
}
