package entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Edge {

	private List<Integer> vertexs;

	public Edge(List<Integer> vertexs) {
		this.vertexs = vertexs;
		Collections.sort(vertexs);
	}

	public Edge(String s) {
		StringTokenizer tkz = new StringTokenizer(s);
		vertexs = new ArrayList<>();
		while (tkz.hasMoreTokens()) {
			vertexs.add(Integer.valueOf(tkz.nextToken()));
		}
	}

	public List<Integer> getVertexs() {
		return vertexs;
	}

	public void setVertexs(List<Integer> vertexs) {
		this.vertexs = vertexs;
	}

	public boolean containsVertex(Integer v) {
		return vertexs.contains(v);
	}

	public Integer getDegree() {
		return vertexs.size();
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (Integer v : vertexs) {
			sb.append(v + " ");
		}
		return sb.toString();
	}

}
