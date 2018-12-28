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
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import File_format.Path2KML;
import Game.Fruit;
import Game.Game;
import Game.Map;
import Game.PackMan;
import Game.Path;
import Game.ShortestPathAlgo;
import Geom.Point3D;

public class MyFrame extends JFrame implements MouseListener,ActionListener,Runnable{
	public BufferedImage image;
	private Game game;
	private Map map;
	private String what;
	private int count=0;
	private boolean started=false;
	private int timeStop=9999;

	public MyFrame(String imagePath,String name) {
		super(name);
		this.game=new Game();
		
		startimage(imagePath);		
		this.addMouseListener(this); 

	}
	public void startimage(String imagePath) {	
		try {
			image = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}	
		this.map=new Map(image);
		MenuBar menuBar = new MenuBar();
		Menu menu1=new Menu("File");
		MenuItem item1a = new MenuItem("Save");
		MenuItem item1b = new MenuItem("Load");
		MenuItem item1c = new MenuItem("Save to KML");

		menuBar.add(menu1);
		menu1.add(item1a);
		menu1.add(item1b);
		menu1.add(item1c);

		Menu menu2 = new Menu("Add"); 
		MenuItem item2a = new MenuItem("Packman");
		MenuItem item2b = new MenuItem("Fruit");

		menuBar.add(menu2);
		menu2.add(item2a);
		menu2.add(item2b);

		Menu menu3 = new Menu("Game"); 
		MenuItem item3 = new MenuItem("Start");

		menuBar.add(menu3);
		menu3.add(item3);
		this.setMenuBar(menuBar);

		item1a.addActionListener(this);
		item1b.addActionListener(this);
		item1c.addActionListener(this);
		item2a.addActionListener(this);
		item2b.addActionListener(this);
		item3.addActionListener(this);

	}

	public void paint(Graphics g){
		g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
		map.setWidth(this.getWidth());
		map.setHeight(this.getHeight());
		if(!game.getFruits().isEmpty()) {
			Iterator<Fruit> j=this.game.getFruits().iterator();
			while(j.hasNext()) {
				g.setColor(Color.red);
				Fruit fi=j.next();
				if(fi.getTime()>=count||!started) {
					int[] pix=map.convC2P(fi.getFruit());
					g.fillOval(pix[0],pix[1], 15, 15);
					g.setColor(Color.black);
					g.drawOval(pix[0],pix[1], 15, 15);
				}
			}
		}
		if(!game.getPackmen().isEmpty()) {
			ArrayList<PackMan> arrpack=new ArrayList<PackMan>(this.game.getPackmen());
			Iterator<PackMan> i=arrpack.iterator();
			while(i.hasNext()) {
				g.setColor(Color.yellow);
				PackMan pi=i.next();
				int[] pix=map.convC2P(pi.currentPoint(count));
				g.fillOval(pix[0], pix[1], 18, 18);
				g.setColor(Color.black);
				g.drawOval(pix[0], pix[1], 18, 18);
				Path path=pi.getPath();
				Iterator<Point3D> iteratorpoint=path.iterator();
				g.setColor(Color.GREEN);
				Point3D point=iteratorpoint.next();
				int[]pix1=map.convC2P(point);
				while(iteratorpoint.hasNext()) {
					Point3D temp=iteratorpoint.next();
					int[]pix2=this.map.convC2P(temp);
					g.drawLine(pix1[0], pix1[1], pix2[0], pix2[1]);
					point=temp;
					pix1=pix2;
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		try {
			if(what.equalsIgnoreCase("P")) {
				Point3D point=map.convP2C(arg0.getX(), arg0.getY(), 0);
				game.addPackMan(new PackMan(point));
				repaint();
			}
			else if(what.equalsIgnoreCase("F")) {
				Point3D point=map.convP2C(arg0.getX(), arg0.getY(), 0);
				game.addFruit(new Fruit(point));
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
		if(e.getActionCommand().equalsIgnoreCase("Save")) {
			JFileChooser fc = new JFileChooser();
			int value=fc.showSaveDialog(null);
			if(value==fc.APPROVE_OPTION) {
				File file=fc.getSelectedFile();
				game.write(file.getPath()+".csv");
			}
		}
		if(e.getActionCommand().equalsIgnoreCase("Load")) {
			JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("csv","CSV");
			fc.setFileFilter(filter);
			int value=fc.showOpenDialog(null);
			if(value==fc.APPROVE_OPTION) {
				File file=fc.getSelectedFile();
				this.game=new Game(file.getPath());
				repaint();
			}
		}
		if(e.getActionCommand().equalsIgnoreCase("Save to KML")) {
			Path2KML pathKml = new Path2KML(game);
			JFileChooser fc = new JFileChooser();
				int value=fc.showSaveDialog(null);
				if(value==fc.APPROVE_OPTION) {
					File file=fc.getSelectedFile();
					pathKml.write(file.getPath()+".kml");
				}
		}
		if(e.getActionCommand().equalsIgnoreCase("Packman")) {
			what="P";
		}
		if(e.getActionCommand().equalsIgnoreCase("Fruit")) {
			what="F";
		}
		if(e.getActionCommand().equalsIgnoreCase("Start")) {
			ShortestPathAlgo algo=new ShortestPathAlgo();
			algo.Start(game);
			timeStop=game.lastTime();
			count=0;
			started=true;
			repaint();
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		repaint();
		count++;
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(count<timeStop+2)run();
	}
}
