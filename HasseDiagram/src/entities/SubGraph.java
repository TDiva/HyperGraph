package entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SubGraph extends HyperGraph implements Comparable<SubGraph> {

//	вершины, на которых строится подграф
	private List<Integer> baseVertexs;

//	строим индуцированный подграф
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

//	задать вершины, на которых строится подграф
	public List<Integer> getBaseVertexs() {
		return baseVertexs;
	}

//	задать вершины, на которых строится подграф
	public void setBaseVertexs(List<Integer> baseVertexs) {
		this.baseVertexs = baseVertexs;
	}

//	является ли стягиваемым
	public boolean isScreed() {
		// TODO: это необходимые, а не достаточные условия
		return !hasErrors() && hasDoubleEdge() && isClose()
				&& isCorrectVertexs();
	}

//	сравнить подграфы
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
	
//	проверяем является ли один подграф подграфом другого
	public boolean contains(SubGraph s) {
		return baseVertexs.containsAll(s.baseVertexs);
	}

//	текстовое представление
	public String toString() {
		if (baseVertexs.isEmpty()) {
			return "[]";
		}
		return baseVertexs.toString();
	}

}
