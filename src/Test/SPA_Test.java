package Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import Game.Dijkstra;


public class SPA_Test {
@Test
public void recommandedRotationTest1() {
	ArrayList<String> data=new ArrayList<String>();
	data.add("M,0,32.202,35.202,0.0,20.0,1.0");
	data.add("F,43,32.202,35.303,0.0,1.0");
	double actual=Dijkstra.solve(data);
	assertEquals(90.0, actual);
}
@Test
public void recommandedRotationTest2() {
	ArrayList<String> data=new ArrayList<String>();
	data.add("M,0,32.202,35.202,0.0,20.0,1.0");
	data.add("F,43,32.202,35.303,0.0,1.0");
	data.add("G,9,32.202,35.20200000001,0.0,10.0,1.0");
	double actual=Dijkstra.solve(data);
	System.out.println(actual);
	assertEquals(85.0, actual);
}
}
