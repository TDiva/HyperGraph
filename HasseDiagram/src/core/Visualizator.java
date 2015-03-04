package core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

import entities.HyperGraph;
import entities.SubGraph;

public class Visualizator {

	public static ImageIcon createImage(HyperGraph graph) {
		int width = graph.getV() < 9 ? 450 : graph.getV() * 50;
		int height = graph.getV() < 7 ? 420 : graph.getV() * 60;
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
				printSgraphsMap(graph, g);
			}
		}

		return new ImageIcon(img);
	}
	
	private static void printSgraphsMap(HyperGraph h, Graphics g) {
		Map<Integer, List<SubGraph>> map = getLayers(h);
		int i = 0;
		for (List<SubGraph> list: map.values()) {
			g.drawString(list.toString(), 10, i++ * 20 + 10);
		}
	}

	private static Map<Integer, List<SubGraph>> getLayers(HyperGraph h) {
		List<SubGraph> list = h.getSSubGraphs();
		Map<Integer, List<SubGraph>> map = new HashMap<>();
		
		for (SubGraph s: list) {
			int index = s.getV();
			if (!map.containsKey(index)) {
				map.put(index, new ArrayList<SubGraph>());
			} 
			map.get(index).add(s);
		}

		return map;
	}

	private static void printSSubGraphs(HyperGraph graph, Graphics g) {
		List<SubGraph> list = graph.getSSubGraphs();
		for (int i = 0; i < list.size(); i++) {
			g.drawString(list.get(i).toString(), 10, i * 20 + 10);
		}

	}

}
