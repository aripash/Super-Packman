package Game;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;

import Coords.MyCoords;
import Geom.Point3D;
import graph.Graph;
import graph.Graph_Algo;
import graph.Node;

public class Dijkstra {

	static Map map;
	

	/**
	 * calculates the best angle the player needs to rotate to.
	 * @param data the current data on the board.
	 * @return number that represents the angle that the player needs to move to.
	 */
	public static double solve(ArrayList<String> data) {
	
			
		
		
		
		
		double rotate;
		ArrayList<Point3D> fruits=new ArrayList<Point3D>();
		ArrayList<String> Ghoests=new ArrayList<String>();
		ArrayList<Point3D> blocks=new ArrayList<Point3D>();

		//the player
		String row=data.get(0);
		String [] collum=row.split(",");
		double x=Double.parseDouble(collum[3]);
		double y=Double.parseDouble(collum[2]);
		double z=Double.parseDouble(collum[4]);
		Point3D packman=new Point3D(x,y,z);

		//set the array lists
		for(int i=1;i<data.size();i++) {
			row=data.get(i);
			collum=row.split(",");
			if(collum[0].equalsIgnoreCase("F")) {
				x=Double.parseDouble(collum[3]);
				y=Double.parseDouble(collum[2]);
				z=Double.parseDouble(collum[4]);
				fruits.add(new Point3D(x,y,z));
			}
			else if(collum[0].equalsIgnoreCase("G"))
				Ghoests.add(row);
			else if(collum[0].equalsIgnoreCase("B")) {
				double bloat=0.000011;
				x=Double.parseDouble(collum[3]);
				y=Double.parseDouble(collum[2]);
				z=Double.parseDouble(collum[4]);
				blocks.add(new Point3D(x-bloat,y-bloat,z));
				x=Double.parseDouble(collum[6]);
				y=Double.parseDouble(collum[5]);
				z=Double.parseDouble(collum[7]);
				blocks.add(new Point3D(x+bloat,y+bloat,z));
			}
		}

		//determines the angle to the closest fruit(or block point path to get to the fruit)
		Point3D closeFruit=closestFruit(packman,fruits,blocks);
		if(closeFruit==null) {
			int notWorking=(int)(Math.random()*4);
			return notWorking*90;
		}
		MyCoords c=new MyCoords();
		double[] AED=c.azimuth_elevation_dist(packman, closeFruit);
		rotate=AED[0];
		double angle1=rotate+45;
		double angle2=rotate-45;
		for(int i=0;i<Ghoests.size();i++) {
			row=Ghoests.get(i);
			collum=row.split(",");
			x=Double.parseDouble(collum[3]);
			y=Double.parseDouble(collum[2]);
			z=Double.parseDouble(collum[4]);
			Point3D tempGhoest=new Point3D(x,y,z);
			if(c.distance3d(packman, tempGhoest)<=Double.parseDouble(collum[6])+1) {
				AED=c.azimuth_elevation_dist(packman, tempGhoest);
				double tempAzi=AED[0];
				if(tempAzi>angle2&&tempAzi<rotate)rotate+=5;
				else if(tempAzi<angle1&&tempAzi>rotate)rotate-=5;
				else if(tempAzi==rotate)rotate-=5;
			}
		}


		return rotate;
	}
	/**
	 * returns the closest fruit to the packman
	 * @param packman the player packman
	 * @param fruits list of existing fruits
	 * @return gps to the closest fruit
	 */
	public static Point3D closestFruit(Point3D packman, ArrayList<Point3D> fruits,ArrayList<Point3D> blocks) {
		
		BufferedImage image=null;
		try {
			image = ImageIO.read(new File("Ariel1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}	
		 map=new Map(image);
		map.setHeight(image.getHeight());
		map.setWidth(image.getWidth());
		MyCoords c=new MyCoords();

		//closest fruit
		Iterator<Point3D> i=fruits.iterator();
		Point3D min=i.next();
		boolean blocked = blocked(packman,min,blocks);//is the first fruit blocked from the pack man.
		while(i.hasNext()) {
			Point3D fi=i.next();
			if(!blocked(packman,fi,blocks)) {//is the current fruit blocked from the pack man
				if(blocked) {//if the first fruit is blocked take a new min fruit that isn't blocked
					min=fi;
					blocked=false;
				}
				else if(c.distance3d(packman, fi)<c.distance3d(packman, min))min=fi;//take the closest fruit to you 
			}
		}

		if(!blocks.isEmpty()) { //if there are no blocks on the map go to the min fruit
			if(blocked(packman, min, blocks)) {//if there are but the path isn't blocked go to it

				//making the corners and building the graph.
				Point3D[] allCorners = new Point3D[blocks.size()*2+2];
				Graph G = new Graph(); 
				String source = "a";
				String target = "b";
				int [] pix;
				pix=map.convC2P(packman);
				allCorners[0]=new Point3D(pix[0],pix[1]);
				G.add(new Node(source));
				int j=1;
				for(int ii=0;ii<blocks.size();ii+=2) {//making all the corners  
					Point3D lD=blocks.get(ii);
					Point3D rU=blocks.get(ii+1);
					Point3D lU=new Point3D(lD.x(),rU.y(),0);
					Point3D rD=new Point3D(rU.x(),lD.y(),0);


					pix=map.convC2P(lD);
					lD=new Point3D(pix[0],pix[1]);
					allCorners[j]=lD;G.add(new Node(j+""));j++;

					pix=map.convC2P(rU);
					rU=new Point3D(pix[0],pix[1]);
					allCorners[j]=rU;G.add(new Node(j+""));j++;

					pix=map.convC2P(lU);
					lU=new Point3D(pix[0],pix[1]);
					allCorners[j]=lU;G.add(new Node(j+""));j++;

					pix=map.convC2P(rD);
					rD=new Point3D(pix[0],pix[1]);
					allCorners[j]=rD;G.add(new Node(j+""));j++;

				}
				pix=map.convC2P(min);
				allCorners[allCorners.length-1]=new Point3D(pix[0],pix[1]);
				G.add(new Node(target));

				//edges path
				for (int j2 = 0; j2 < allCorners.length-1; j2++) {//all corners not including pack man and fruit.
					for (int k = 0; k < allCorners.length; k++) {
						String Left=""+j2;
						if(j2==0) Left=source;//if we are checking from the pack man
						if(k!=allCorners.length-1) {//as long as we aren't checking against the fruit 
							if(j2!=k)//if we aren't checking between the same corner
								if(!blockedP(allCorners[j2],allCorners[k],blocks))G.addEdge(Left, ""+k, allCorners[j2].distance2D(allCorners[k]));
						}
						else if(!blockedP(allCorners[j2],allCorners[k],blocks))G.addEdge(Left, target,allCorners[j2].distance2D(allCorners[k]));
					}
				}


				//the next point to move to
				Graph_Algo.dijkstra(G, source);
				Node n = G.getNodeByName(target);
				ArrayList<String> shortestPath = n.getPath();
				Point3D closeP;
				try {
					String ans=shortestPath.get(1);
					pix[0]=(int) allCorners[Integer.parseInt(ans)].x();
					pix[1]=(int) allCorners[Integer.parseInt(ans)].y();
					closeP=map.convP2C(pix[0], pix[1], 0);
				}
				catch(Exception e){
					return null;
				}
				return closeP;
			}
			else return min;
		}
		return min;

	}
	public static boolean blockedP(Point3D packman, Point3D min, ArrayList<Point3D> blocks) {
		// TODO Auto-generated method stub
		
		double left=0;
		double right=0;
		if(packman.x()<min.x()) {
			left=packman.x();
			right=min.x();
		}
		else {
			right=packman.x();
			left=min.x();

		}
		double m=min.y()-packman.y();
		m/=(min.x()-packman.x());
		double n=packman.y();
		n-=(packman.x()*m);
		
		for(int i=0;i<blocks.size();i+=2) {
			int []pix=map.convC2P(blocks.get(i));
			Point3D lD=new Point3D(pix[0],pix[1]);
			pix=map.convC2P(blocks.get(i+1));
			Point3D rU=new Point3D(pix[0],pix[1]);
		
			
			for(double j=left;j<right;j+=1) {
				double Y=j*m+n;
				if(Y<lD.y()&&Y>rU.y())
					if(j<=rU.x()&&j>=lD.x()){
						return true;
					}
			}
		}
		return false;
	}
	public static boolean blocked(Point3D packman, Point3D min, ArrayList<Point3D> blocks) {
		// TODO Auto-generated method stub
		for(int i=0;i<blocks.size();i+=2) {
			Point3D lD=new Point3D(blocks.get(i));
			Point3D rU=new Point3D(blocks.get(i+1));
			double m=(min.y()-packman.y())/(min.x()-packman.x());
			double n=(packman.x()*m*-1)+packman.y();
			double left=0;
			double right=0;
			if(packman.x()<min.x()) {
				left=packman.x();
				right=min.x();
			}
			else {
				right=packman.x();
				left=min.x();

			}
			for(double j=left;j<right;j+=0.0000001) {
				double Y=j*m+n;
				if(Y>lD.y()&&Y<rU.y()) 
					if(j<rU.x()&&j>lD.x()){
						return true;
					}
			}
		}
		return false;
	}
}
