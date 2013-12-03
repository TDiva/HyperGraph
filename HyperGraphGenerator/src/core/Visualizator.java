package core;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import entities.HyperGraph;
import entities.ScreedGraph;
import entities.SubGraph;

public class Visualizator {
	private static final int MAX_WIDTH = 700;
	private static final int MAX_HEIGHT = 500;

	public static Color[] colors = new Color[] { Color.BLUE, Color.CYAN,
			Color.DARK_GRAY, Color.GREEN, Color.LIGHT_GRAY, Color.MAGENTA,
			Color.ORANGE, Color.PINK, Color.RED, Color.YELLOW };

	public static class Point {
		public int x;
		public int y;

		public Point() {
			x = 0;
			y = 0;
		}

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

	}

	public static JTable fillMatrix(JTable matrix, HyperGraph graph) {
		String[] headers = new String[graph.getV()];

		for (int i=0; i<graph.getV(); i++) {
			headers[i] = (new Integer(i+1)).toString();

		}
		matrix.setModel(new DefaultTableModel(graph.toArray(), headers));
		return matrix;
	}

	public static JTable fillMatrix(JTable matrix, SubGraph graph) {
		String[] headers = new String[graph.getVertexs().size()];
		for (int i=0; i<graph.getVertexs().size(); i++) {
			headers[i] = (new Integer(graph.getVertexs().get(i)+1)).toString();

		}
		matrix.setModel(new DefaultTableModel(graph.toArray(), headers));
		return matrix;
	}

	public static JTable fillMatrix(JTable matrix, ScreedGraph graph) {
		String[] headers = new String[graph.getVertexs().size()];

		for (int i=0; i<graph.getVertexs().size(); i++) {
			headers[i] =graph.getVertexsName(i);
		}
		matrix.setModel(new DefaultTableModel(graph.toArray(), headers));
		return matrix;
	}

	private static void drawEdge(Graphics g, int edge, int index, int v) {
		List<Integer> row = new ArrayList<Integer>();
		int deg = 0;
		for (int i = 0; i < v; i++, edge /= 2) {
			if (edge % 2 == 1) {
				deg++;
				row.add(i);
			}
		}
		row.add(row.get(0));

		int x0 = MAX_WIDTH / 2;
		int y0 = MAX_HEIGHT / 2;
		int r = (int) (Math.min(MAX_HEIGHT, MAX_WIDTH) / 2.5);
		int l = 5 + 2 * index;
		double du = 2 * Math.PI / v;

		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(colors[index]);
		g2.setStroke(new BasicStroke(2));

		if (deg == 2) {
			int x2 = (int) (x0 + r * Math.cos(row.get(0) * du));
			int y2 = (int) (y0 - r * Math.sin(row.get(0) * du));
			int x1 = (int) (x0 + r * Math.cos(row.get(1) * du));
			int y1 = (int) (y0 - r * Math.sin(row.get(1) * du));
			g2.drawLine(x1, y1, x2, y2);
		} else {
			for (int i = 0; i < row.size() - 1; i++) {
				int x2 = (int) (x0 + r * Math.cos(row.get(i) * du));
				int y2 = (int) (y0 - r * Math.sin(row.get(i) * du));
				int x1 = (int) (x0 + r * Math.cos(row.get(i + 1) * du));
				int y1 = (int) (y0 - r * Math.sin(row.get(i + 1) * du));

				int d = (int) Math.sqrt(Math.pow(x2 - x1, 2d)
						+ Math.pow(y2 - y1, 2d));
				int dx = x2 - x1;
				int dy = y2 - y1;

				int x = x2 + l * (y2 - y1) / d;
				int y = y2 - l * (x2 - x1) / d;
				g2.drawLine(x, y, x - dx, y - dy);
			}
			row.add(row.get(1));
			for (int i = 0; i < row.size() - 2; i++) {
				int x1 = (int) (x0 + r * Math.cos(row.get(i) * du));
				int y1 = (int) (y0 - r * Math.sin(row.get(i) * du));
				int x2 = (int) (x0 + r * Math.cos(row.get(i + 1) * du));
				int y2 = (int) (y0 - r * Math.sin(row.get(i + 1) * du));
				int x3 = (int) (x0 + r * Math.cos(row.get(i + 2) * du));
				int y3 = (int) (y0 - r * Math.sin(row.get(i + 2) * du));

				int d1 = (int) Math.sqrt(Math.pow(x2 - x1, 2d)
						+ Math.pow(y2 - y1, 2d));
				int d2 = (int) Math.sqrt(Math.pow(x3 - x2, 2d)
						+ Math.pow(y3 - y2, 2d));

				int ax = x1 + l * (y1 - y2) / d1 + (x2 - x1);
				int ay = y1 - l * (x1 - x2) / d1 + (y2 - y1);
				int bx = x2 + l * (y2 - y3) / d2;
				int by = y2 - l * (x2 - x3) / d2;

				double u1 = Math.acos(Math.abs((double) (ax - x2) / l)) * 360
						/ (2 * Math.PI);
				double u2 = Math.acos(Math.abs((double) (bx - x2) / l)) * 360
						/ (2 * Math.PI);
				if (ax - x2 < 0) {
					u1 = 180 - u1;
				}
				if (ay - y2 > 0) {
					u1 = 360 - u1;
				}
				if (bx - x2 < 0) {
					u2 = 180 - u2;
				}
				if ((by - y2) > 0) {
					u2 = 360 - u2;
				}
				if (u2 < u1) {
					u2 += 360;
				}

				g2.drawArc(x2 - l, y2 - l, 2 * l, 2 * l, (int) u1,
						(int) (u2 - u1));

			}
		}

	}

	public static ImageIcon createImage(HyperGraph graph) {
		Image img = new BufferedImage(MAX_WIDTH, MAX_HEIGHT,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();

		int x0 = MAX_WIDTH / 2;
		int y0 = MAX_HEIGHT / 2;
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, MAX_WIDTH, MAX_HEIGHT);
		g.setColor(Color.BLACK);
		drawVertexs(graph, g, x0, y0);
		for (int i = 0; i < graph.getMatrix().size(); i++) {
			drawEdge(g, graph.getMatrix().get(i), i, graph.getV());
		}

		g.dispose();
		return new ImageIcon(img);
	}

	private static List<Point> drawVertexs(HyperGraph graph, Graphics g,
			int x0, int y0) {
		int r = (int) (Math.min(MAX_HEIGHT, MAX_WIDTH) / 2.5);
		List<Point> points = new ArrayList<Point>();

		if (graph instanceof SubGraph) {
			SubGraph sgraph = (SubGraph) graph;
			double du = 2 * Math.PI / sgraph.getV();

			for (int i = 0; i < sgraph.getV(); i++) {
				if (sgraph.getVertexs().contains(i)) {
					int x = (int) (x0 + r * Math.cos(i * du));
					int y = (int) (y0 - r * Math.sin(i * du));
					points.add(new Point(x, y));
					g.fillOval(x - 5, y - 5, 10, 10);
					x = (int) (x0 + (r + 30) * Math.cos(i * du));
					y = (int) (y0 - (r + 30) * Math.sin(i * du));
					g.drawString((new Integer(i + 1).toString()), x, y);
				} else {
					points.add(new Point(x0, y0));
				}
			}
		} else if (graph instanceof ScreedGraph) {

			double du = 2 * Math.PI / graph.getV();

			for (int i = 0; i < graph.getV(); i++) {
				int x = (int) (x0 + r * Math.cos(i * du));
				int y = (int) (y0 - r * Math.sin(i * du));
				points.add(new Point(x, y));
				g.fillOval(x - 5, y - 5, 10, 10);
				x = (int) (x0 + (r + 30) * Math.cos(i * du));
				y = (int) (y0 - (r + 30) * Math.sin(i * du));
				g.drawString(((ScreedGraph) graph).getVertexsName(i), x, y);
			}

		} else {

			double du = 2 * Math.PI / graph.getV();

			for (int i = 0; i < graph.getV(); i++) {
				int x = (int) (x0 + r * Math.cos(i * du));
				int y = (int) (y0 - r * Math.sin(i * du));
				points.add(new Point(x, y));
				g.fillOval(x - 5, y - 5, 10, 10);
				x = (int) (x0 + (r + 30) * Math.cos(i * du));
				y = (int) (y0 - (r + 30) * Math.sin(i * du));
				g.drawString("V-" + (i + 1), x, y);
			}
		}
		return points;
	}

}
