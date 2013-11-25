package core;

import java.awt.Color;
import java.awt.Graphics;
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
		for (int i = 0; i < graph.getV(); i++) {
			headers[i] = "V-" + (i + 1);
		}
		matrix.setModel(new DefaultTableModel(graph.toArray(), headers));
		return matrix;
	}

	public static JTable fillMatrix(JTable matrix, SubGraph graph) {
		String[] headers = new String[graph.getVertexs().size()];
		for (int i = 0; i < graph.getVertexs().size(); i++) {
			headers[i] = "V-" + (graph.getVertexs().get(i) + 1);
		}
		matrix.setModel(new DefaultTableModel(graph.toArray(), headers));
		return matrix;
	}

	public static JTable fillMatrix(JTable matrix, ScreedGraph graph) {
		String[] headers = new String[graph.getVertexs().size()];
		for (int i = 0; i < graph.getVertexs().size(); i++) {
			headers[i] = "V-" + (graph.getVertexs().get(i) + 1);
		}
		matrix.setModel(new DefaultTableModel(graph.toArray(), headers));
		return matrix;
	}

	private static void drawEdge(Graphics g, List<Point> points, int edge,
			int index) {
		List<Integer> row = new ArrayList<Integer>();
		for (int i = 0; i < points.size(); i++, edge /= 2) {
			row.add(edge % 2);
		}

		g.setColor(colors[index]);
		int prev = -1;
		int first = -1;
		for (int i = 0; i < row.size(); i++) {
			if (row.get(i) == 1) {
				if (prev == -1) {
					first = i;
					prev = i;
				} else {
					g.drawLine(points.get(prev).x, points.get(prev).y,
							points.get(i).x, points.get(i).y);
					prev = i;
				}
			}
		}
		g.drawLine(points.get(first).x, points.get(first).y,
				points.get(prev).x, points.get(prev).y);
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
		List<Point> points = drawVertexs(graph, g, x0, y0);

		for (int i = 0; i < graph.getMatrix().size(); i++) {
			drawEdge(g, points, graph.getMatrix().get(i), i);
		}

		return new ImageIcon(img);
	}

	private static List<Point> drawVertexs(HyperGraph graph, Graphics g,
			int x0, int y0) {
		int r = (int) (Math.min(MAX_HEIGHT, MAX_WIDTH) / 2.5);
		List<Point> points = new ArrayList<Point>();

		if (graph instanceof SubGraph) {
			SubGraph sgraph = (SubGraph) graph;
			double du = 2 * Math.PI / sgraph.getVertexs().size();

			for (int i = 0; i < sgraph.getVertexs().size(); i++) {
				int x = (int) (x0 + r
						* Math.cos(sgraph.getVertexs().get(i) * du));
				int y = (int) (y0 - r
						* Math.sin(sgraph.getVertexs().get(i) * du));
				points.add(new Point(x, y));
				g.fillOval(x - 5, y - 5, 10, 10);
				x = (int) (x0 + (r + 30)
						* Math.cos(sgraph.getVertexs().get(i) * du));
				y = (int) (y0 - (r + 30)
						* Math.sin(sgraph.getVertexs().get(i) * du));
				g.drawString("V-"
						+ (((SubGraph) graph).getVertexs().get(i) + 1), x, y);
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
				g.drawString("V-"
						+ (((ScreedGraph) graph).getVertexs().get(i) + 1), x, y);
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
