package core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.ImageIcon;

import entities.HyperGraph;
import entities.SubGraph;

public class Visualizator {

	public static final int WIDTH_GAP = 50;
	public static final int HEIGHT_GAP = 60;

	public static ImageIcon createImage(HyperGraph graph) {
		int width = 450;
		int height = 420;
		Image img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		Graphics g = img.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);

		g.setColor(Color.BLACK);
		if (graph.getR() > 0) {
			if (!graph.isCorrect()) {
				g.drawString("Hypergraph is incorrect!", width / 2 - 50,
						height / 2);
			} else if (!graph.hasDoubleEdge()) {
				g.drawString(
						"First condition: hypergraph doesn't have double edge",
						width / 2 - 150, height / 2);
			} else if (!graph.isClose()) {
				g.drawString("Second condition: hypergraph isn't close",
						width / 2 - 100, height / 2);
			} else if (!graph.isCorrectVertexs()) {
				g.drawString("Third condition: E != V - 1", width / 2 - 50,
						height / 2);
			} else {
				img = drawHasseDiagram(graph);
			}
		}

		return new ImageIcon(img);
	}

	private static BufferedImage drawHasseDiagram(HyperGraph h) {
		Map<Integer, List<SubGraph>> map = getLayers(h);
		int layers = map.size();
		int maxN = 0;
		for (Integer i : map.keySet()) {
			if (map.get(i).size() > maxN) {
				maxN = map.get(i).size();
			}
		}

		int height = layers < 7 ? 420 : layers * HEIGHT_GAP;
		int width = maxN < 9 ? 450 : maxN * WIDTH_GAP;

		BufferedImage img = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);

		List<Point> points = new ArrayList<>();
		int index = 0;
		for (Integer i : map.keySet()) {
			int layerY = (int) ((0.5 + index++) * HEIGHT_GAP);

			List<SubGraph> list = map.get(i);
			int dx = width / list.size();

			for (int j = 0; j < list.size(); j++) {
				Point p = new Point((int) ((j + 0.5) * dx), layerY, list.get(j));
				points.add(p);
			}
		}

		for (Point p : points) {
			for (Point q : points) {
				if (p.equals(q)) {
					continue;
				}
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
			p.draw(g);
		}

		return img;
	}

	@SuppressWarnings("unused")
	private static void printSgraphsMap(HyperGraph h, Graphics g) {
		Map<Integer, List<SubGraph>> map = getLayers(h);
		int i = 0;
		for (List<SubGraph> list : map.values()) {
			g.drawString(list.toString(), 10, i++ * 20 + 10);
		}
	}

	private static Map<Integer, List<SubGraph>> getLayers(HyperGraph h) {
		List<SubGraph> list = h.getSSubGraphs();
		Map<Integer, List<SubGraph>> map = new TreeMap<>();

		for (SubGraph s : list) {
			int index = s.getV();
			if (!map.containsKey(index)) {
				map.put(index, new ArrayList<SubGraph>());
			}
			map.get(index).add(s);
		}

		return map;
	}

	@SuppressWarnings("unused")
	private static void printSSubGraphs(HyperGraph graph, Graphics g) {
		List<SubGraph> list = graph.getSSubGraphs();
		for (int i = 0; i < list.size(); i++) {
			g.drawString(list.get(i).toString(), 10, i * 20 + 10);
		}

	}

}
