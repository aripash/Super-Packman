package GIS;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
/**
 * this class represents a set of Elements
 */
public class Layer implements GIS_layer{
	private MetaData Data;
	private ArrayList<GIS_element> element;
	
	/**
	 *empty constructor
	 */
	public Layer() {
		element=new ArrayList<GIS_element>();
	}
	/**
	 *constructor that creates a Layer with given MetaData
	 */
	public Layer(Meta_data Data) {
		this.Data=new MetaData(Data);
		element=new ArrayList<GIS_element>();
	}
	/**
	 * copy constuctor
	 */
	public Layer(GIS_layer lay) {
		Data=(MetaData) lay.get_Meta_data();
		element=new ArrayList<GIS_element>();
		Iterator<GIS_element> i=lay.iterator();
		while(i.hasNext()) {
		element.add(i.next());
		}
	}
	/**function adds an element to layer*/
	public boolean add(GIS_element arg0) {
		// TODO Auto-generated method stub
		return element.add(arg0);
	}

	/**function adds all elements from specified collection to layer*/
	public boolean addAll(Collection<? extends GIS_element> arg0) {
		// TODO Auto-generated method stub
		return element.addAll(arg0);
	}
	
	/**function removes all elements from layer*/
	public void clear() {
		// TODO Auto-generated method stub
		element.clear();
		Data=null;
	}

	/**function returns true if layer contains specified element*/
	public boolean contains(Object arg0) {
		// TODO Auto-generated method stub
		if(Data.equals(arg0))return true;
		return element.contains(arg0);
	}

	/**function returns true if layer contains all of element of specified collection*/
	public boolean containsAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return element.containsAll(arg0);
	}

	/**
	 * function returns true if layer is empty
	 */
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return element.isEmpty()&&Data==null;
	}

	public Iterator<GIS_element> iterator() {
		// TODO Auto-generated method stub
		return element.iterator();
	}

	/**
	 * function removes specified element  from layer
	 */
	public boolean remove(Object arg0) {
		// TODO Auto-generated method stub
		if(arg0.equals(Data)) {
			Data=null;
			return true;
		}
		return element.remove(arg0);
	}

	/**
	 * function removes elements that specified collection contains from layer
	 */
	public boolean removeAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return	element.removeAll(arg0);
	}

	/**
	 * Retains only the elements in this layer that are contained in the specified collection
	 */
	public boolean retainAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return element.retainAll(arg0);
	}

	/**
	 * Returns the number of elements in layer
	 */
	public int size() {
		// TODO Auto-generated method stub
		return element.size();
	}

	/**
	 * Returns an Object array containing all of the elements in layer in proper sequence (from first to last element). 
	 */
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return element.toArray();
	}

	/**
	 * Returns an array of chosen type containing all of the elements in layer in proper sequence (from first to last element). 
	 */
	public <T> T[] toArray(T[] arg0) {
		// TODO Auto-generated method stub
		return element.toArray(arg0);
	}

	/**
	 * function returnes MetaData of layer
	 */
	public Meta_data get_Meta_data() {
		// TODO Auto-generated method stub
		return new MetaData (Data);
	}
	/**
	 * basic function to show layer in string form
	 */
	public String toString() {
		String s="";
		s+=Data.toString()+"\n";
		Iterator<GIS_element> i=this.iterator();
		while(i.hasNext()) {
			s+=i.next().toString();
		}
		return s;
	}
}
