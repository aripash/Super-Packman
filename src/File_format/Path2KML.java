package File_format;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Iterator;

import Game.Fruit;
import Game.Game;
import Game.PackMan;

public class Path2KML {
	private Game game;
	public Path2KML(Game game) {
		this.game=game;
	}
	/**
	 * writes the game into a kml file
	 * @param path where the function needs to write the kml
	 */
	public void write(String path) {
		PrintWriter pw = null;
		try 
		{
			pw = new PrintWriter(new File(path));
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
			return;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<kml xmlns=\"http://www.opengis.net/kml/2.2\">");
		sb.append("<Document>");
		sb.append("<name>PackMan</name>");
		pw.write(sb.toString());
		sb.delete(0, sb.toString().length());
		Iterator<PackMan> i=game.getPackmen().iterator();
		while(i.hasNext()) {
			PackMan pi=i.next();
			int time=0;
			while(time<game.lastTime()+2) {
				sb.append("<Placemark>");
				sb.append("<Style id=\"Packman\">");
				sb.append("<IconStyle>");
				sb.append("<scale>1.3</scale>");
				sb.append("<Icon>");
				sb.append("<href>http://maps.google.com/mapfiles/kml/pal3/icon49.png</href>"); 
				sb.append("</Icon>"); 
				sb.append("</IconStyle>");
				sb.append("</Style>");
				sb.append("<TimeStamp>");
				sb.append("<when>");
				if(time<10)sb.append("2018-08-16T22:00:0"+time);
				else sb.append("2018-08-16T22:00:"+time);
				sb.append("</when>");
				sb.append("</TimeStamp>");
				sb.append("<Point>");
				sb.append("<coordinates>");
				sb.append(pi.currentPoint(time).toString());
				sb.append("</coordinates>");
				sb.append("</Point>");
				sb.append("</Placemark>");
				pw.write(sb.toString());
				sb.delete(0, sb.toString().length());
				time++;
			}
		}
		Iterator<Fruit> j=game.getFruits().iterator();
		while(j.hasNext()) {
			Fruit fi=j.next();
				sb.append("<Placemark>");
				sb.append("<Style id=\"Fruit\">");
				sb.append("<IconStyle>");
				sb.append("<scale>1.3</scale>");
				sb.append("<Icon>");
				sb.append("<href>http://maps.google.com/mapfiles/kml/paddle/F.png</href>"); 
				sb.append("</Icon>"); 
				sb.append("</IconStyle>");
				sb.append("</Style>");
				sb.append("<TimeSpan>");
				sb.append("<begin>");
				sb.append("2018-08-16T22:00:00");
				sb.append("</begin>");
				sb.append("<end>");
				int time=(int) fi.getTime();
				if(time<10)sb.append("2018-08-16T22:00:0"+time);
				else sb.append("2018-08-16T22:00:"+time);
				sb.append("</end>");
				sb.append("</TimeSpan>");
				sb.append("<Point>");
				sb.append("<coordinates>");
				sb.append(fi.getFruit().toString());
				sb.append("</coordinates>");
				sb.append("</Point>");
				sb.append("</Placemark>");
				pw.write(sb.toString());
				sb.delete(0, sb.toString().length());
			}
		
		sb.append("</Document>");
		sb.append("</kml>");
		pw.write(sb.toString());
		pw.close();
	}
}
