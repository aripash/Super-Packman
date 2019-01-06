package Game;

import javax.swing.JFrame;

import GUI.Super_PackMan_GUI;

public class Start_super_packman {
	public static void main(String[]args) {
		Super_PackMan_GUI window = new Super_PackMan_GUI();
		window.setVisible(true);
		window.setSize(window.image.getWidth(),window.image.getHeight());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);
	}
}
