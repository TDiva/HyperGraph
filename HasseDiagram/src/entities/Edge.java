package entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Edge {

//	ребро есть множество вершин
	private List<Integer> vertexs;

	public Edge(List<Integer> vertexs) {
		this.vertexs = new ArrayList<>(vertexs);
		Collections.sort(this.vertexs);
	}

//	чтение из строки
	public Edge(String s) {
		StringTokenizer tkz = new StringTokenizer(s);
		vertexs = new ArrayList<>();
		while (tkz.hasMoreTokens()) {
			vertexs.add(Integer.valueOf(tkz.nextToken()));
		}
		Collections.sort(vertexs);
	}

//	получить вершины
	public List<Integer> getVertexs() {
		return vertexs;
	}

//	задать вершины
	public void setVertexs(List<Integer> vertexs) {
		this.vertexs = vertexs;
	}

//	содежрит ли вершину
	public boolean containsVertex(Integer v) {
		return vertexs.contains(v);
	}

//	количество вершин в ребре
	public Integer getDegree() {
		return vertexs.size();
	}

//	текстовое представление
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (Integer v : vertexs) {
			sb.append(v + " ");
		}
		return sb.toString();
	}

	@Override
	public int hashCode() {
		return vertexs.hashCode();
	}

//	провера являются ли ребра одинаковыми
	@Override
	public boolean equals(Object obj) {
		return hashCode() == obj.hashCode();
	}

}
