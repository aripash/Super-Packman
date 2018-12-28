package GIS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import File_format.Csv2kml;
import Geom.Point3D;
/**
 * this class represents a way to search for multiple CSV files and the ability to change all files to KML format
 */
public class MultiCSV{
	private Project proj;
	private ArrayList<String> csvPath;
	private static long time;
	
	/**
	 * Constructor of MultiCSV object, receives a path of a directory or csv file and then adds all the csv files into csvPath array list.
	 * @param path represents the directory or csv file path.
	 * @throws IOException if can't read a file be it protected of no longer available
	 */
	public MultiCSV(String path) throws IOException {
		time=new Date().getTime();
		csvPath=new ArrayList<String>();
		String name=path.substring(path.lastIndexOf("/"), path.length());
		proj=new Project(new MetaData(time,name));
		search(path);
	}	
	/** functions returns the project path*/
	public Project getProject() {
		return proj;
	}
	/**function returns the csv path*/
	public ArrayList<String> getcsvPath(){
		return csvPath;
	}
	/**
	 * function converts all csv file in folder to kml files
	 * @param path is the path of the directory
	 */
	public void kmlAll(String path) {
		Iterator<String> i=csvPath.iterator();
		Csv2kml file;
		while(i.hasNext()) {
			file=new Csv2kml(i.next());
			file.write(path);
		}
	}
	
	private void search(String path) throws IOException {
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		BufferedReader bufferReader = null;
		MetaData MD=new MetaData(time,csvPath.size()+"");
		for (File file : listOfFiles) {
			String f=file.getName();
			if(file.isDirectory())	search(file.getPath());
			else if (f.substring(f.length()-4, f.length()).equalsIgnoreCase(".csv")) {
				try{
					csvPath.add(file.getPath());
					InputStream inputStream = new FileInputStream(file.getPath()); 
					InputStreamReader streamReader = new InputStreamReader(inputStream);
					bufferReader = new BufferedReader(streamReader);
					bufferReader.readLine();
					bufferReader.readLine();
					String line;
					String cvsSplitBy = ",";
					String name=file.getPath().substring(file.getPath().lastIndexOf("\\")+1, file.getPath().length()-4);
					MD=new MetaData(time,"name: "+name);
					Layer lay=new Layer(MD);
					while ((line = bufferReader.readLine()) != null)   {
						String[] data=line.split(cvsSplitBy);
						Point3D Pe=new Point3D( Double.parseDouble(data[7]),
								Double.parseDouble(data[6]),
								Double.parseDouble(data[8]));
						String colour;
						if(data[10].equalsIgnoreCase("GSM")) colour="blue ";
						else  colour="red ";
						MD=new MetaData(time,"name: "+data[1]+" colour: "+colour);
						Element el=new Element(Pe,MD);
						lay.add(el);
					}
					proj.add(lay);
				}catch (Exception e) {
					e.printStackTrace();
				}finally{
					bufferReader.close();
				}
			}
		}
	}

}
