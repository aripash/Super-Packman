package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import Coords.MyCoords;
import Game.Map;
import Game.Super_Packman_Algo;
import Geom.Point3D;
import Robot.Play;

public class Super_PackMan_GUI extends JFrame implements MouseListener,ActionListener{
	public BufferedImage image;
	private Map map;
	private boolean addMe=false;
	private boolean started=false;
	private Play play;

	public Super_PackMan_GUI() {
		super("Super Packman");
		startimage("Ariel1.png");		
		this.addMouseListener(this); 

	}
	/**
	 * reads the image and builds the menu bar for the game
	 * @param imagePath path to the displayed image
	 */
	public void startimage(String imagePath) {	
		try {
			image = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}	
		this.map=new Map(image);

		MenuBar menuBar = new MenuBar();
		Menu menu1=new Menu("Game");
		MenuItem item1a = new MenuItem("Load");
		MenuItem item1b = new MenuItem("Add Player");

		menuBar.add(menu1);
		menu1.add(item1a);
		menu1.add(item1b);

		Menu menu2 = new Menu("Start"); 
		MenuItem item2a = new MenuItem("Manual");
		MenuItem item2b = new MenuItem("Auto");

		menuBar.add(menu2);
		menu2.add(item2a);
		menu2.add(item2b);

		item1a.addActionListener(this);
		item1b.addActionListener(this);
		item2a.addActionListener(this);
		item2b.addActionListener(this);

		this.setMenuBar(menuBar);
	}
	public void paint(Graphics g){
		g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
		map.setWidth(this.getWidth());
		map.setHeight(this.getHeight());
		if(started) {
			ArrayList<String> boardData=play.getBoard();
			for(int i=0;i<boardData.size();i++) {
				String row=boardData.get(i);
				String [] collum=row.split(",");
				if(collum[0].equalsIgnoreCase("B")) {
					double x1=Double.parseDouble(collum[3]);
					double y1=Double.parseDouble(collum[2]);
					double x2=Double.parseDouble(collum[6]);
					double y2=Double.parseDouble(collum[5]);
					Point3D point1=new Point3D(x1,y1,0);
					Point3D point2=new Point3D(x2,y2,0);
					int []pix1=map.convC2P(point1);
					int []pix2=map.convC2P(point2);
					int widthRec=Math.abs(pix1[0]-pix2[0]);
					int heightRec=Math.abs(pix1[1]-pix2[1]);
					g.setColor(Color.black);
					g.fillRect(pix1[0], pix2[1], widthRec, heightRec);
				}
			}
			for(int i=0;i<boardData.size();i++) {
				String row=boardData.get(i);
				String [] collum=row.split(",");
				Color colour=Color.black;
				int radius=0;
				if(collum[0].equalsIgnoreCase("M")) {colour=Color.CYAN;radius=30;}
				else if(collum[0].equalsIgnoreCase("P")) {colour=Color.YELLOW;radius=25;}
				else if(collum[0].equalsIgnoreCase("G")) {colour=Color.RED;radius=20;}
				else if(collum[0].equalsIgnoreCase("F")) {colour=Color.GREEN;radius=18;}
				if(!collum[0].equalsIgnoreCase("B")) {
					double x=Double.parseDouble(collum[3]);
					double y=Double.parseDouble(collum[2]);
					double z=Double.parseDouble(collum[4]);
					Point3D point=new Point3D(x,y,z);
					int []pix=map.convC2P(point);
					g.setColor(Color.BLACK);
					g.fillOval(pix[0], pix[1], radius+2, radius+2);
					g.setColor(colour);
					g.fillOval(pix[0], pix[1], radius, radius);
				}
			}
			String info=play.getStatistics();
			g.setColor(Color.BLACK);
			g.drawString(info, 101, 101);
			g.setColor(Color.yellow);
			g.drawString(info, 100, 100);
		}
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		try {
			if(addMe) {
				Point3D point=map.convP2C(arg0.getX(), arg0.getY(), 0);
				play.setInitLocation(point.y(),point.x());
				repaint();
			}
			else {
				ArrayList<String> boardData=play.getBoard();
				String row=boardData.get(0);
				String [] collum=row.split(",");
				Point3D playerPoint=new Point3D(Double.parseDouble(collum[3]),Double.parseDouble(collum[2]),Double.parseDouble(collum[4]));
				Point3D point=map.convP2C(arg0.getX(), arg0.getY(), 0);
				MyCoords c=new MyCoords();
				double[] data=c.azimuth_elevation_dist(playerPoint, point);
				play.rotate(data[0]);
				repaint();
			}

		}
		catch(Exception e){

		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equalsIgnoreCase("Load")) {
			JFileChooser fc = new JFileChooser("data/");
			FileNameExtensionFilter filter = new FileNameExtensionFilter("csv","CSV");
			fc.setFileFilter(filter);
			int value=fc.showOpenDialog(null);
			if(value==fc.APPROVE_OPTION) {
				File file=fc.getSelectedFile();
				play=new Play(file.getPath());
				play.setIDs(205356801,314291808,3333);
				started=true;
				repaint();
			}
		}
		if(e.getActionCommand().equalsIgnoreCase("Add Player")) {
			addMe=true;
		}
		if(e.getActionCommand().equalsIgnoreCase("Manual")) {
			ArrayList<String> data=play.getBoard();
			if(data.get(0).charAt(0)=='M') {//start only if there is a player
			play.start();
			addMe=false;
			}
		}
		if(e.getActionCommand().equalsIgnoreCase("Auto")) {
			
			ArrayList<String> data=play.getBoard();
			if(data.get(0).charAt(0)=='M') {//start only if there is a player
				addMe=false;
				play.start();

				new Thread()
				{
					public void run()
					{
						while(play.isRuning()) {
							double angle=Super_Packman_Algo.recommandedRotation(data);
							play.rotate(angle);
							repaint();
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							//run();
						}
					}
				}.start();
			}
		}
	}


}
