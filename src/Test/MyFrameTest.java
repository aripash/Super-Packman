package Test;

import javax.swing.JFrame;

import GUI.MyFrame;


public class MyFrameTest{
	public static void main(String[] args){
		MyFrame window = new MyFrame("C:/Users/Owner/Desktop/Ex3/Ex3/Ex3/Ariel1.png","Packman");
		window.setVisible(true);
		window.setSize(window.image.getWidth(),window.image.getHeight());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);
		window.setName("Packman");
	}
}
