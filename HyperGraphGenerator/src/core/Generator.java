package core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Generator {
	private HyperGraph graph = null;
	
	private final static int MAX_VERTEX = 10;
	private final static int DELTA_EDGE = 2; 
	private Set<Integer> fixedVertex;
	
	private Random r;
	
	Generator() {
		fixedVertex = new HashSet<Integer> ();
		r = new Random();
		generate();
	}
	
	// TODO: implement
	public void generate() {
		fixedVertex.clear();
		int v = r.nextInt(MAX_VERTEX)+2;
		graph = new HyperGraph(v);
		while (graph.getMatrix().size() < v-1) {
			fixedVertex.add(generateEdge());
		}
		System.gc();
	}
	
	private int generateEdge() {
		int k = Math.min(r.nextInt(DELTA_EDGE)+2, graph.getV()-fixedVertex.size());
		List<Integer> edge = new ArrayList<Integer>();
		while (edge.size()<k) {
			int e = r.nextInt(graph.getV());
			if (!edge.contains(e) && !fixedVertex.contains(e))
				edge.add(e);
		}
		graph.addEdge(edge);
		return edge.get(0);
	}

	public HyperGraph getGraph() {
		return graph;
	}

	public void setGraph(HyperGraph graph) {
		this.graph = graph;
	}
	
	public void print() {
		graph.print();
	}

}
