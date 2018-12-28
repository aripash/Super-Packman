package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import GIS.Layer;
import GIS.MetaData;

class LayerTest {

	@Test
	void get_Meta_dataTest() {
		Layer l=new Layer(new MetaData(111,"test"));
		assertEquals(l.get_Meta_data().toString(),"test");
	}
	@Test
	void toStringTest() {
		Layer l=new Layer(new MetaData(111,"test"));
		String s=l.toString().trim();
		assertEquals(s,"test");
	}
}
