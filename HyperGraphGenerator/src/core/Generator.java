package core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Generator {
	private HyperGraph graph = null;
	
	private final static int MAX_VERTEX = 6;
	private final static int DELTA_EDGE = 2; 
	private List<Integer> fixedVertex;
	
	private Random r;
	
	Generator() {
		fixedVertex = new ArrayList<Integer> ();
		r = new Random();
		generate();
	}
	
	public void generate() {
		fixedVertex.clear();
		int v = r.nextInt(MAX_VERTEX)+2;
		for (int i=0; i<v; i++)
			fixedVertex.add(i);
		graph = new HyperGraph(v);
		int i;
		for (i=0; i<v-1; i++) {
			fixedVertex.remove(generateEdge());
		}
		System.gc();
	}
	
	private int generateEdge() {
		int k = Math.min(r.nextInt(DELTA_EDGE)+2, fixedVertex.size());
		List<Integer> edge = new ArrayList<Integer>();
		int e = 0;
		while (edge.size()<k) {
			e = r.nextInt(fixedVertex.size());
			if (!edge.contains(fixedVertex.get(e)))
				edge.add(fixedVertex.get(e));
		}
		graph.addEdge(edge);
		return e;
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
