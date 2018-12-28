package GIS;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
/**
 * this class represents a set of layers
 */
public class Project implements GIS_project{
	private MetaData Data;
	ArrayList<GIS_layer> layer;
	
	public Project() {
		layer=new ArrayList<GIS_layer>();
		Data=new MetaData(0,"");
	}

	public Project(Meta_data Data) {
		layer=new ArrayList<GIS_layer>();
		this.Data=new MetaData(Data);
	}
	public Project(GIS_project p) {
		Data=(MetaData) p.get_Meta_data();
		layer=new ArrayList<GIS_layer>();
		Iterator<GIS_layer> i=p.iterator();
		while(i.hasNext()) {
		layer.add(i.next());
		}
	}
	/**function adds an layer to project*/
	public boolean add(GIS_layer e) {
		// TODO Auto-generated method stub
		return layer.add(e);
	}

	/**function adds all layers from specified collection to project*/
	public boolean addAll(Collection<? extends GIS_layer> c) {
		// TODO Auto-generated method stub
		return layer.addAll(c);
	}

	/**function removes all layers from project*/
	public void clear() {
		// TODO Auto-generated method stub
		layer.clear();
		Data=null;
	}

	/**function returns true if project contains specified layer*/
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		if(Data.equals(o))return true;
		return layer.contains(o);
	}

	/**function returns true if project contains all of element of specified collection*/
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return layer.containsAll(c);
	}

	/**
	 * function returns true if project is empty
	 */
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return layer.isEmpty()&&Data==null;
	}

	@Override
	public Iterator<GIS_layer> iterator() {
		// TODO Auto-generated method stub
		return layer.iterator();
	}
	/**
	 * function removes specified layer from project
	 */
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		if(Data.equals(o)) {
			Data=null;
			return true;
		}
		return layer.remove(o);
	}

	/**
	 * function removes layer that specified collection contains from project
	 */
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return layer.removeAll(c);
	}

	/**
	 * Retains only the layers in this project that are contained in the specified collection
	 */
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return layer.retainAll(c);
	}

	/**
	 * Returns the number of layers in project
	 */
	public int size() {
		// TODO Auto-generated method stub
		return layer.size();
	}

	/**
	 * Returns an Object array containing all of the layers in project in proper sequence (from first to last element). 
	 */
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return layer.toArray();
	}

	/**
	 * Returns an array of chosen type containing all of the layers in project in proper sequence (from first to last element). 
	 */
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return layer.toArray(a);
	}

	/**
	 * function returnes MetaData of project
	 */
	public Meta_data get_Meta_data() {
		// TODO Auto-generated method stub
		return new MetaData(Data);
	}
	/**
	 * basic function to show project in string form
	 */
	public String toString() {
		String s=Data.getUTC()+" ";
		s+=Data.toString()+" ";
		Iterator<GIS_layer> i=this.iterator();
		while(i.hasNext()) {
			s+=i.next().toString();
		}
		return s;
	}

}
