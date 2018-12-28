package File_format;

import java.util.Collection;
/**
 *this class is used to represent a KML document and assist in class 'Csv2kml'
 */

public class Document {
private Collection<Placemark> placemarks;
private String name;


public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
	this.name+=".kml";
}


public Collection<Placemark> getPlacemark() {
	return placemarks;
}

public void setPlacemark(Collection<Placemark> placemarks) {
	this.placemarks = placemarks;
}

}
