package core;

import java.awt.Color;
import java.awt.Graphics;

import entities.SubGraph;

class Point {
	public int x;
	public int y;
	
	public SubGraph s;
	
	public Point() {
		x = 0;
		y = 0;
	}

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Point(int x, int y, SubGraph s) {
		this(x,y);
		this.s = s;
	}

	public void draw(Graphics g, Color c) {
		g.setColor(c);
		g.fillOval(x - 3, y - 3, 6, 6);
		g.setColor(Color.RED);
		String text = s == null ? "" : s.toString(); 
		int dx = text.length() * 2;
		g.drawString(text, x - dx, y + 15);
	}

	public void draw(Graphics g) {
		draw(g, Color.BLACK);
	}

	public void connect(Graphics g, Point p, Color c) {
		g.setColor(c);
		g.drawLine(x, y, p.x, p.y);
	}

	public void connect(Graphics g, Point p) {
		connect(g, p, Color.BLACK);
	}
	
	public void move(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
