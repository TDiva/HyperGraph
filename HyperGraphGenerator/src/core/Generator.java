package core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Generator {
	private HyperGraph graph = null;
	
	private final static int MAX_VERTEX = 10;
	private final static int DELTA_EDGE = 2; 
	
	Generator() {
		generate();
	}
	
	// TODO: implement
	public void generate() {
		Random r = new Random();
		int v = r.nextInt(MAX_VERTEX)+2;
		graph = new HyperGraph(v);
		List<Integer> edge = new ArrayList<Integer>();
		edge.add(r.nextInt(v));
		edge.add(r.nextInt(v));
		graph.addEdge(edge);
		for (int i=0; i<v-2; i++) {
			edge = new ArrayList<Integer>();
			int e = r.nextInt(DELTA_EDGE)+2;
			for (int j=0; j<e; j++) {
				edge.add(r.nextInt(v));
			}
			graph.addEdge(edge);
		}
		System.gc();
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
