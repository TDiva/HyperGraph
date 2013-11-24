package core;

import java.awt.BorderLayout;
import java.awt.Dialog;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class SubGraphsFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	JLabel img;
	
	public SubGraphsFrame() {
		img = new JLabel("Hello!");
		setLayout(new BorderLayout());
		add(img,BorderLayout.CENTER);
		
		pack();
	}

}
