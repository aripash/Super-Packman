package Game;

import java.util.ArrayList;
import java.util.Iterator;

import Coords.MyCoords;
import Geom.Point3D;

public class ShortestPathAlgo {
	public ShortestPathAlgo() {}
/**
 * builds paths for the packmen in game using the fruits
 * @param game the lists of packmen and fruits that the algorithm needs to calculate
 */
	public void Start(Game game) {
		ArrayList<Fruit> fruitsLeft=game.getFruits();		//new so the list wont be ruined
		ArrayList<PackMan> packmen=game.getPackmen();		//pointer the the list to make the paths for each packman
		MyCoords c=new MyCoords();
		while(!fruitsLeft.isEmpty()) {
			Iterator<PackMan> i=packmen.iterator();
			while(i.hasNext()&&!fruitsLeft.isEmpty()) {
				Iterator<Fruit> j=fruitsLeft.iterator();
				PackMan pack=i.next();		//the packman i want to check
				Fruit min=j.next();		//the fruit thats the closes to the packman pack
				Point3D packlocal=pack.getLastGps();
				while(j.hasNext()) {		//which fruit is the closest to the packman
					Fruit temp=j.next();
					if(c.distance3d(min.getFruit(),packlocal)>c.distance3d(temp.getFruit(), packlocal))min=temp;
				}
				boolean flag=true;		//if the closes fruit to the packman isn't closer to another packman
				Iterator<PackMan> iCheck=packmen.iterator();
				while(flag&&iCheck.hasNext()) {
					PackMan packCheck=iCheck.next();
					Point3D pakclocalCheck=packCheck.getLastGps();
					double totalDistance1=c.distance3d(packlocal, min.getFruit())+pack.getPath().distance()-pack.getRadius();
					double totalDistance2=c.distance3d(pakclocalCheck, min.getFruit())+packCheck.getPath().distance()-packCheck.getRadius();
					double Time1=pack.Time(totalDistance1);
					double Time2=packCheck.Time(totalDistance2);
					if(Time1>Time2)flag=false;		//if the time it takes the packman to reach the fruit isn't ideal
				}
				if(flag) {
					pack.add(min.getFruit());
					game.addTime(min,(pack.getPath().distance()/pack.getSpeed()));
					fruitsLeft.remove(min);
				}
			}
		}
	}
}
