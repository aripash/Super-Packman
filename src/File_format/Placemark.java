package File_format;


/**
 * this class represents the tags in a kml file and assist in class 'Csv2kml'
 *
 */

public class Placemark {
private String name;
private String description;
private String Point;
private String time;


public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}


public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}


public String getPoint() {
	return Point;
}

public void setPoint(String point) {
	Point = point;
}

public String getTime() {
	return time;
}
public void setTime(String time) {
	this.time=time;
}


}
