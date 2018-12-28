package File_format;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
/**
 * this class represents a converter from CSV files to KML files.
 * read more:
 * https://en.wikipedia.org/wiki/Comma-separated_values
 * https://en.wikipedia.org/wiki/Keyhole_Markup_Language
 *
 */

public class Csv2kml {
	private String csvFile;
	/**
	 * constructor that receives a string representing a csv file
	 * @param csvFile
	 */
	public Csv2kml(String csvFile) {
		if(csvFile.charAt(csvFile.length()-1)=='/')this.csvFile=csvFile.substring(0, csvFile.length()-1);
		else this.csvFile=csvFile;
	}
/**
 * function is used to read a csv file, split each line by commas and then create a Document object. the Document object will be ready to use
 * @param path
 * @return
 */
	public Document read(String path) {
		String line = "";
		String cvsSplitBy = ",";

		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
			ArrayList<Placemark> colP=new ArrayList<Placemark>();
			br.readLine();
			br.readLine();
			while ((line = br.readLine()) != null) {
				String[] lineData = line.split(cvsSplitBy);
				Placemark place=new Placemark();
				place.setName(lineData[1]);
				place.setDescription(lineData[10]);
				place.setPoint(lineData[7]+","+lineData[6]+","+lineData[8]);
				place.setTime(lineData[3]);
				colP.add(place);
			}
			Document doc=new Document();
			if(csvFile.indexOf("/")==-1)
			path+=csvFile.substring(csvFile.lastIndexOf("\\")+1, csvFile.length()-4);
			else path+=csvFile.substring(csvFile.lastIndexOf("/")+1, csvFile.length()-4);
			doc.setName(path);
			doc.setPlacemark(colP);

			return doc;
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * this function receives a csv file path and prints it in kml format
	 * @param path is a string that represents a csv file path
	 */
	public void write(String path) {
		Document doc=this.read(path);
		PrintWriter pw = null;

		try 
		{
			pw = new PrintWriter(new File(doc.getName()));
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
			return;
		}
		StringBuilder sb = new StringBuilder();
		Iterator<Placemark> i=doc.getPlacemark().iterator();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<kml xmlns=\"http://www.opengis.net/kml/2.2\">");
		sb.append("<Document>");
		while(i.hasNext()) {
			Placemark temp=i.next();
			sb.append("<Placemark>");
			sb.append("<name>");
			sb.append(temp.getName());
			sb.append("</name>");
			sb.append("<description>");
			sb.append(temp.getDescription());
			sb.append("</description>");
			sb.append("<Point>");
			sb.append("<coordinates>");
			sb.append(temp.getPoint());
			sb.append("</coordinates>");
			sb.append("</Point>");
			sb.append("<time>");
			sb.append(temp.getTime());
			sb.append("</time>");
			sb.append("</Placemark>");
		}
		sb.append("</Document>");
		sb.append("</kml>");
		pw.write(sb.toString());
		pw.close();
		System.out.println("done!");
	}
	/**
	 * function should be used to get the original string that represents the csv file
	 * @return a string that represents the original csv file
	 */
	public String getCsvFile() {
		return csvFile;
	}
	private void setCsvFile(String csvFile) {
		this.csvFile=csvFile;
	}
}
