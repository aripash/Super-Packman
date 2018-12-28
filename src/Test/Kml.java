package Test;

import java.io.IOException;

import File_format.Csv2kml;
import GIS.MultiCSV;

public class Kml {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Csv2kml no=new Csv2kml("C:/Users/Owner/Downloads/Ex2/Ex2/data/WigleWifi_20171201110209.csv");
		no.write("C:/Users/Owner/Downloads/Ex2/Ex2/data/");
		MultiCSV test=new MultiCSV("C:/Users/Owner/Downloads/Ex2/Ex2");
		test.kmlAll("C:/Users/Owner/Desktop/");
		System.out.println(test.getProject());
		System.out.println(test.getcsvPath().toString());
	}

}
