package core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.ImageIcon;

import app.Application;
import entities.HyperGraph;
import entities.SubGraph;

//  ласс дл€ рисовани€
public class Visualizator {

//	рассто€ние между вершинами
	public static final int WIDTH_GAP = 100;
	public static final int HEIGHT_GAP = 65;

	private static List<Point> points;
	private static Set<Point> showPoints;
	private static int width = Application.WIDTH-3;
	private static int height = Application.HEIGHT;

	
//	находим точку по клику мышки (координатам)
	public static Point getPoint(int x, int y) {
		if (points == null || points.isEmpty()) {
			return null;
		}
		Point clicked = new Point(x, y);
//		перебираем все вершины и находим, кака€ близко достаточно
		for (Point p : points) {
			if (clicked.isClose(p)) {
				return p;
			}
		}
		return null;
	}

	public static List<Point> getPoints() {
		return points;
	}

//	создаем новое изображение
	public static BufferedImage createImage(HyperGraph h) {
//		если нет стандартного изображени€ (не было ошибок) создаем новую диаграмму
		BufferedImage img = getDefaultImage(h);
		if (img == null) {
			img = createHasseDiagram(h);
		}
		return img;
	}

//	получить изображение (не перерисовывать новую диаграмму)
	public static BufferedImage getImage(HyperGraph h) {
		BufferedImage img = getDefaultImage(h);
		if (img == null) {
			img = getHasseDiagram();
		}

		return img;
	}

//	стандартное изображение
	public static BufferedImage getDefaultImage(HyperGraph graph) {
		BufferedImage img = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);

		Graphics g = img.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);

		g.setColor(Color.BLACK);
		if (graph.getV() > 0) {
//			если есть ошибки
			if (graph.hasErrors()) {
				g.drawString("Hypergraph is incorrect! Check input area!", width / 2 - 70,
						height / 2);
//				нет двойного ребра
			} else if (!graph.hasDoubleEdge()) {
				g.drawString(
						"First condition: hypergraph doesn't have double edge",
						width / 2 - 150, height / 2);
//				не св€зный граф
			} else if (!graph.isClose()) {
				g.drawString("Second condition: hypergraph isn't close",
						width / 2 - 100, height / 2);
//				не совпадает количество вершин -1 и количество ребер
			} else if (!graph.isCorrectVertexs()) {
				g.drawString("Third condition: E != V - 1", width / 2 - 50,
						height / 2);
			} else {
				img = null;
			}
		}

		return img;
	}

	public static ImageIcon getImageIcon(HyperGraph graph) {
		Image img = getImage(graph);
		return new ImageIcon(img);
	}

	public static ImageIcon createImageIcon(HyperGraph h) {
		return new ImageIcon(createImage(h));
	}

//	нарисовать диаграмму по уже обработанному графу (не создаем новую)
	public static BufferedImage getHasseDiagram() {
		BufferedImage img = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);

		for (Point p : points) {
			for (Point q : points) {
				if (p.equals(q)) {
					continue;
				}
//				провер€ем услови€ св€зи вершин, и соедин€ем их
				if (p.s.contains(q.s)) {
					boolean f = false;
					for (Point w : points) {
						if (w.equals(p) || w.equals(q)) {
							continue;
						}
						f |= p.s.contains(w.s) && w.s.contains(q.s);
					}
					if (!f) {
						p.connect(g, q);
					}
				}
			}
		}

		for (Point p : points) {
			p.draw(g, showPoints.contains(p));
		}

		return img;
	}
	
	public static String getHasseDiagramText(HyperGraph h) {
		Map<Integer, List<SubGraph>> map = getLayers(h);
		StringBuffer sb=  new StringBuffer();
		List<Integer> l = new ArrayList<>(map.keySet());
		Collections.sort(l, Collections.reverseOrder());
		for (Integer i : l) {
			List<SubGraph> list = map.get(i);
			for (SubGraph sg: list) {
				sb.append(sg.toString());
				sb.append("\t");
			}
			sb.append("\r\n");
		}		
		return sb.toString();
	}

//	создаем новую диаграмму
	private static BufferedImage createHasseDiagram(HyperGraph h) {
//		получаем уровни (все подграфы котогые ст€гиваемы теоретически)
		Map<Integer, List<SubGraph>> map = getLayers(h);
		int layers = map.size();
		int maxN = 0;
//		находим максимальную ширину
		for (Integer i : map.keySet()) {
			if (map.get(i).size() > maxN) {
				maxN = map.get(i).size();
			}
		}

		height = layers < 10 ? width : layers * HEIGHT_GAP;
		width = maxN < 9 ? height : maxN * WIDTH_GAP;

//		создаем вершины и записываем их местоположение. рисуем их
		points = new ArrayList<>();
		showPoints = new HashSet<>();
		int index = 0;
		List<Integer> l = new ArrayList<>(map.keySet());
		Collections.sort(l, Collections.reverseOrder());
		for (Integer i : l) {
			int layerY = (int) ((0.5 + index++) * HEIGHT_GAP);

			List<SubGraph> list = map.get(i);
			int dx = width / list.size();

			for (int j = 0; j < list.size(); j++) {
				Point p = new Point((int) ((j + 0.5) * dx), layerY, list.get(j));
				points.add(p);
			}
		}

		return getHasseDiagram();
	}

	private static Map<Integer, List<SubGraph>> getLayers(HyperGraph h) {
//		получаем все подграфы
		List<SubGraph> list = h.getSSubGraphs();
		Map<Integer, List<SubGraph>> map = new TreeMap<>();

//		распредел€ем подграфы по уровн€м
		for (SubGraph s : list) {
			int index = s.getV();
			if (!map.containsKey(index)) {
				map.put(index, new ArrayList<SubGraph>());
			}
			map.get(index).add(s);
		}

		return map;
	}
	
//	показываем или скрываем название вершины
	public static void showPoint(int x, int y) {
		Point p = getPoint(x,y);
		if (p != null) {
			if (showPoints.contains(p))
				showPoints.remove(p);
			else
				showPoints.add(p);
		}
	}

}
