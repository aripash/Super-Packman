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
				x=Double.parseDouble(collum[3]);
				y=Double.parseDouble(collum[2]);
				z=Double.parseDouble(collum[4]);
				blocks.add(new Point3D(x,y,z));
				x=Double.parseDouble(collum[6]);
				y=Double.parseDouble(collum[5]);
				z=Double.parseDouble(collum[7]);
				blocks.add(new Point3D(x,y,z));
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
		MyCoords c=new MyCoords();

		//closest fruit
		Iterator<Point3D> i=fruits.iterator();
		Point3D min=i.next();
		while(i.hasNext()) {
			Point3D fi=i.next();
			if(c.distance3d(packman, fi)<c.distance3d(packman, min)&&!blocked(packman,fi,blocks))min=fi;
		}
		if(!blocks.isEmpty()) 
			if(blocked(packman, min, blocks))
			{
				//making the corners and building the graph.
				BufferedImage image=null;
				try {
					 image = ImageIO.read(new File("Ariel1.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}	
				Map map=new Map(image);
				map.setHeight(image.getHeight());
				map.setWidth(image.getWidth());
				
				
				Point3D[] allCorners = new Point3D[blocks.size()*2+1];
				Graph G = new Graph(); 
				String source = "a";
				String target = "b";
				int [] pix;
				pix=map.convC2P(packman);
				allCorners[0]=new Point3D(pix[0],pix[1]);
				G.add(new Node(source));
				int j=1;
				for(int ii=0;ii<blocks.size()/2;ii++) {
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

				//edges
				for(int i1=1;i1<allCorners.length;i1++) {
					if(i1!=allCorners.length-1) {
						if(!blockedP(allCorners[0], allCorners[i1], blocks))G.addEdge(source, i1+"  ",packman.distance2D( allCorners[i1]));
					}
					else if(!blockedP(allCorners[0], allCorners[i1], blocks))G.addEdge(source, target+"  ", packman.distance2D( allCorners[i1]));
				}
				for (int j2 = 1; j2 < allCorners.length-1; j2++) {
					for (int k = j2+1; k < allCorners.length; k++) {
						if(k!=allCorners.length-1) {
							if(!blockedP(allCorners[j2],allCorners[k],blocks))G.addEdge(""+j2, ""+k, allCorners[j2].distance2D(allCorners[k]));
						}
						else if(!blockedP(allCorners[j2],allCorners[k],blocks))G.addEdge(""+j2, target,allCorners[j2].distance2D(allCorners[k]));
					}
				}


				//the next point to move to
				try{Graph_Algo.dijkstra(G, source);}
				catch(Exception e) {return null;}
				Node n = G.getNodeByName(target);
				ArrayList<String> shortestPath = n.getPath();
				String ans=shortestPath.get(1);
				System.out.println(ans);
				Point3D closeP;
				try {
					pix[0]=(int) allCorners[Integer.parseInt(ans)].x();
					pix[1]=(int) allCorners[Integer.parseInt(ans)].y();
					closeP=map.convP2C(pix[0], pix[1], 0);
				}
				catch(Exception e){
					return min;
				}
				return closeP;
			}
		return min;

	}
	public static boolean blockedP(Point3D packman, Point3D min, ArrayList<Point3D> blocks) {
		// TODO Auto-generated method stub
		BufferedImage image=null;
		try {
			 image = ImageIO.read(new File("Ariel1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}	
		Map map=new Map(image);
		map.setHeight(image.getHeight());
		map.setWidth(image.getWidth());
		for(int i=0;i<blocks.size();i+=2) {
			int []pix=map.convC2P(blocks.get(i));
			Point3D lD=new Point3D(pix[0],pix[1]);
			pix=map.convC2P(blocks.get(i+1));
			Point3D rU=new Point3D(pix[0],pix[1]);
			double m=Math.abs(min.y()-packman.y())/(min.x()-packman.x());
			double n=((packman.x()*m*-1)+packman.y());
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
			for(double j=left;j<right;j+=0.00001) {
				double Y=j*m+n;
				if(Y>=lD.y()&&Y<=rU.y())
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
