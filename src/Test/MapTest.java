package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Test;

import Game.Map;
import Geom.Point3D;

class MapTest {

	@Test
	void ConvC2P2C() {
		Point3D point=new Point3D(35.00005,32.00005,0);
		BufferedImage image=null;
		try {
			 image = ImageIO.read(new File("Ariel1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}	
		Map map=new Map(image);
		map.setHeight(image.getHeight());
		map.setWidth(image.getWidth());
		int [] pix =map.convC2P(point);
		Point3D point2=map.convP2C(pix[0], pix[1], 0);
		if((int)(point2.x()*100000)==(int)(point.x()*100000)) assertEquals((int)(point2.y()*100000),(int)(point.y()*100000));
		else fail("failed");
	}

}
