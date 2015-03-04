package entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SubGraph extends HyperGraph implements Comparable<SubGraph> {

	private List<Integer> baseVertexs;

	public SubGraph(List<Integer> vertexs, HyperGraph h) {
		super(vertexs.size());
		baseVertexs = vertexs;
		Collections.sort(baseVertexs);
		if (v > 1) {
			for (Edge e : h.getE()) {
				boolean f = true;
				for (Integer u : e.getVertexs()) {
					f &= vertexs.contains(u);
				}
				if (f) {
					List<Integer> edge = new ArrayList<>();
					for (Integer w : e.getVertexs()) {
						edge.add(vertexs.indexOf(w) + 1);
					}
					addEdge(new Edge(edge));
				}
			}
		}
	}

	public List<Integer> getBaseVertexs() {
		return baseVertexs;
	}

	public void setBaseVertexs(List<Integer> baseVertexs) {
		this.baseVertexs = baseVertexs;
	}

	public boolean isScreed() {
		// TODO: это необходимые, а не достаточные условия
		return isCorrect() && hasDoubleEdge() && isClose()
				&& isCorrectVertexs();
	}

	@Override
	public int compareTo(SubGraph o) {
		if (this.getV() == o.getV()) {
			for (int i = 0; i < this.getV(); i++) {
				if (this.baseVertexs.get(i) != o.baseVertexs.get(i)) {
					return this.baseVertexs.get(i) - o.baseVertexs.get(i);
				}
			}
		}
		return this.getV() - o.getV();
	}
	
	public boolean contains(SubGraph s) {
		return baseVertexs.containsAll(s.baseVertexs);
	}

	public String toString() {
//		StringBuffer sb = new StringBuffer("[");
//		for (Integer v : baseVertexs) {
//			sb.append(v + ",");
//		}
//		sb.append("]");
//		return sb.toString();
		return baseVertexs.toString();
	}

}
