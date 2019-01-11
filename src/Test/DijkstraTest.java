package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Test;

import Game.Dijkstra;
import Game.Map;
import Geom.Point3D;

class DijkstraTest {

	@Test
	void testBlocked() {
		Dijkstra d=new Dijkstra();
		ArrayList<Point3D> blocks=new ArrayList<Point3D>();
		blocks.add(new Point3D(1,-1));
		blocks.add(new Point3D(2,1));
		assertTrue(d.blocked(new Point3D(0,0), new Point3D(3,0), blocks));
	}
	@Test
	void testBlocked2() {
		Dijkstra d=new Dijkstra();
		ArrayList<Point3D> blocks=new ArrayList<Point3D>();
		blocks.add(new Point3D(3,-1));
		blocks.add(new Point3D(4,1));
		assertFalse(d.blocked(new Point3D(0,0), new Point3D(3,0), blocks));
	}
	@Test
	void testBlockedP() {
		Dijkstra d=new Dijkstra();
		ArrayList<Point3D> blocks=new ArrayList<Point3D>();
		BufferedImage image=null;
		try {
			 image = ImageIO.read(new File("Ariel1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}	
		Map map=new Map(image);
		map.setHeight(image.getHeight());
		map.setWidth(image.getWidth());
		blocks.add(map.convP2C(1, -1, 0));
		blocks.add(map.convP2C(2, 1, 0));
		assertTrue(d.blockedP(new Point3D(0,0), new Point3D(3,0), blocks));
	}
	@Test
	void testBlockedP2() {
		Dijkstra d=new Dijkstra();
		ArrayList<Point3D> blocks=new ArrayList<Point3D>();
		BufferedImage image=null;
		try {
			 image = ImageIO.read(new File("Ariel1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}	
		Map map=new Map(image);
		map.setHeight(image.getHeight());
		map.setWidth(image.getWidth());
		blocks.add(map.convP2C(4, -1, 0));
		blocks.add(map.convP2C(5, 1, 0));
		assertFalse(d.blockedP(new Point3D(0,0), new Point3D(3,0), blocks));
	}
}
