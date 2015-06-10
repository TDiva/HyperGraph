package core;

import java.awt.Color;
import java.awt.Graphics;

import entities.SubGraph;

// ����� �����, ����� ������� ���� ��������
public class Point {

	public static final int POINT_RADIUS = 3;
	public int x;
	public int y;

//	������� � �������� ��������� �����
	public SubGraph s;

//	������������
	public Point() {
		x = 0;
		y = 0;
	}

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Point(int x, int y, SubGraph s) {
		this(x, y);
		this.s = s;
	}

//	��������� �����
	public void draw(Graphics g, Color c, boolean showText) {
		g.setColor(c);
		g.fillOval(x - POINT_RADIUS, y - POINT_RADIUS, POINT_RADIUS * 2,
				POINT_RADIUS * 2);
		if (showText) {
			g.setColor(Color.RED);
			String text = s == null ? "" : s.toString();
			int dx = text.length() * 2;
			g.drawString(text, x - dx, y + 15);
		}
	}

	public void draw(Graphics g, boolean showText) {
		draw(g, Color.BLACK, showText);
	}

//	������ �����
	public void connect(Graphics g, Point p, Color c) {
		g.setColor(c);
		g.drawLine(x, y, p.x, p.y);
	}

	public void connect(Graphics g, Point p) {
		connect(g, p, Color.BLACK);
	}

//	���������� �����
	public void move(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void move(int x) {
		this.x = x;
	}

//	������� ����������
	public int sqrDistTo(Point p) {
		return (x - p.x) * (x - p.x) + (y - p.y) * (y - p.y);
	}

//  ������ �� ������ � �����
	public boolean isClose(Point p) {
		return sqrDistTo(p) <= 4 * POINT_RADIUS * POINT_RADIUS;
	}

//	��������� �������������
	public String toString() {
		StringBuffer sb = new StringBuffer(s.toString());
		sb.append(": (");
		sb.append(x);
		sb.append(", ");
		sb.append(y);
		sb.append(")");
		return sb.toString();
	}
}
