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

import entities.HyperGraph;

/*
 * Класс, занимающийся визуализацией графов
 */
public class Visualizator {
	// будем считать, что это размеры окна, доступного для рисования
	private static final int MAX_WIDTH = 500;
	private static final int MAX_HEIGHT = 500;

	// а это цвета ребер, чтобы удобнее было рисовать
	public static Color[] colors = new Color[] { Color.BLUE, Color.CYAN,
			Color.DARK_GRAY, Color.GREEN, Color.LIGHT_GRAY, Color.MAGENTA,
			Color.ORANGE, Color.PINK, Color.RED, Color.YELLOW, Color.BLUE,
			Color.CYAN, Color.DARK_GRAY, Color.GREEN, Color.LIGHT_GRAY,
			Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.YELLOW };

	// класс "точка", для удобство рисования
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

	// рисует ребра графа
	private static void drawEdge(Graphics g, int edge, int index, int v) {
		List<Integer> row = new ArrayList<Integer>();
		int deg = 0;
		// получаем список вершин, которые соединяет ребро
		for (int i = 0; i < v; i++, edge /= 2) {
			if (edge % 2 == 1) {
				deg++;
				row.add(i);
			}
		}
		// добавляем первую вершину в конец, чтобы не обрабатывать крайние
		// случаи (зацилить рисование)
		row.add(row.get(0));

		// центр экрана
		int x0 = MAX_WIDTH / 2;
		int y0 = MAX_HEIGHT / 2;
		// расстояние до нарисованных вершин
		int r = (int) (Math.min(MAX_HEIGHT, MAX_WIDTH) / 2.5);
		// расстояние от центра вершины, то места, где будет проходить ребро -
		// чтобы ребря не накладывались друг на друга делаем их наразных
		// расстояниях
		int l = 5 + 2 * index;
		// угол между двумя вершинами
		double du = 2 * Math.PI / v;

		// задаем параметры рисования: цвет и толщину
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(colors[index]);
		g2.setStroke(new BasicStroke(2));

		// если ребро соединяет 2 вершины - рисуем линию соединяющую центры
		if (deg == 2) {
			int x2 = (int) (x0 + r * Math.cos(row.get(0) * du));
			int y2 = (int) (y0 - r * Math.sin(row.get(0) * du));
			int x1 = (int) (x0 + r * Math.cos(row.get(1) * du));
			int y1 = (int) (y0 - r * Math.sin(row.get(1) * du));
			g2.drawLine(x1, y1, x2, y2);
			// если больше - рисуем вокруг них ребро
		} else {
			for (int i = 0; i < row.size() - 1; i++) {
				// центры вершин, которые соединяем
				int x2 = (int) (x0 + r * Math.cos(row.get(i) * du));
				int y2 = (int) (y0 - r * Math.sin(row.get(i) * du));
				int x1 = (int) (x0 + r * Math.cos(row.get(i + 1) * du));
				int y1 = (int) (y0 - r * Math.sin(row.get(i + 1) * du));

				// расстояние между центрами
				int d = (int) Math.sqrt(Math.pow(x2 - x1, 2d)
						+ Math.pow(y2 - y1, 2d));
				int dx = x2 - x1;
				int dy = y2 - y1;

				// точка касательной окружности, проходщей на расстоянии l от
				// центра - от нее и будем вести линию
				int x = x2 + l * (y2 - y1) / d;
				int y = y2 - l * (x2 - x1) / d;
				g2.drawLine(x, y, x - dx, y - dy);
			}
			row.add(row.get(1));
			// рисуем продолжение реба - окружность вокруг вершины
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

	// рисуем граф
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
			if (!graph.isSingleEdge(i))
				drawEdge(g, graph.getMatrix().get(i), i, graph.getV());
		}

		// освобождаем ресурсв ОС
		g.dispose();
		return new ImageIcon(img);
	}

	// рисуем вершины
	private static List<Point> drawVertexs(HyperGraph graph, Graphics g,
			int x0, int y0) {
		int r = (int) (Math.min(MAX_HEIGHT, MAX_WIDTH) / 2.5);
		List<Point> points = new ArrayList<Point>();
		int x, y;

		double du = 2 * Math.PI / graph.getV();

		for (int i = 0; i < graph.getV(); i++) {
			drawPoint(g, x0, y0, r, points, du, i);
			x = (int) (x0 + (r + 30) * Math.cos(i * du));
			y = (int) (y0 - (r + 30) * Math.sin(i * du));
			g.drawString(new Integer(i + 1).toString(), x, y);
		}

		return points;
	}

	private static void drawPoint(Graphics g, int x0, int y0, int r,
			List<Point> points, double du, int i) {
		int x;
		int y;
		x = (int) (x0 + r * Math.cos(i * du));
		y = (int) (y0 - r * Math.sin(i * du));
		points.add(new Point(x, y));
		g.fillOval(x - 5, y - 5, 10, 10);
	}

}
