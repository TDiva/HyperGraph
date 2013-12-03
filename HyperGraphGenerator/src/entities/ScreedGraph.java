package entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ScreedGraph extends HyperGraph {

	private HyperGraph parent;
	private int u1;
	private int u2;

	List<Set<Integer>> vertexs;

	public ScreedGraph(HyperGraph parent, int k) {
		super(parent.v - 1);
		this.parent = parent;

		getBorders(parent.getMatrix().get(k));

		for (int i = 0; i < parent.getMatrix().size(); i++) {
			if (i == k)
				continue;
			List<Integer> buf = new ArrayList<Integer>();
			int d = parent.getMatrix().get(i);
			for (int j = 0; j < parent.v; d /= 2, j++) {
				buf.add(d % 2);
			}
			buf.set(u1, buf.get(u1) | buf.get(u2));
			buf.remove(u2);

			int row = 0;
			for (int j = buf.size() - 1; j >= 0; j--) {
				row *= 2;
				row += buf.get(j);
			}

			if (!matrix.contains(row))
				addEdge(row);
		}

		vertexs = new ArrayList<Set<Integer>>();
		if (parent instanceof ScreedGraph) {
			for (Set<Integer> item : ((ScreedGraph) parent).vertexs)
				vertexs.add(new HashSet<Integer>(item));
		} else {
			for (int i = 0; i < parent.v; i++) {
				Set<Integer> row = new HashSet<Integer>();
				row.add(i + 1);
				vertexs.add(row);
			}
		}
		Set<Integer> removed = vertexs.get(u2);
		vertexs.remove(u2);
		vertexs.get(u1).addAll(removed);
	}

	public HyperGraph getParent() {
		return parent;
	}

	public void setParent(HyperGraph parent) {
		this.parent = parent;
	}

	public int getU1() {
		return u1;
	}

	public void setU1(int u1) {
		this.u1 = u1;
	}

	public int getU2() {
		return u2;
	}

	public void setU2(int u2) {
		this.u2 = u2;
	}

	public List<Set<Integer>> getVertexs() {
		return vertexs;
	}

	public void setVertexs(List<Set<Integer>> vertexs) {
		this.vertexs = vertexs;
	}

	public String getVertexsName(int index) {
		Set<Integer> screeds = vertexs.get(index);
		StringBuffer name = new StringBuffer();
		for (Integer item : screeds) {
			name.append(item.toString() + ", ");
		}
		name.delete(name.length() - 2, name.length());
		return name.toString();
	}

	private void getBorders(int edge) {
		u1 = -1;
		u2 = -1;
		for (int i = 0, e = edge; e > 0; e /= 2, i++) {
			if (e % 2 == 1) {
				if (u1 == -1) {
					u1 = i;
				} else {
					u2 = i;
				}
			}
		}
	}

	public boolean equals(Object o) {
		if (!(o instanceof ScreedGraph))
			return false;
		ScreedGraph g = (ScreedGraph) o;

		return (v == g.v && vertexs.containsAll(g.vertexs)
				&& g.vertexs.containsAll(vertexs)
				&& matrix.containsAll(g.matrix) && g.matrix.containsAll(matrix));

	}

	public String getDiff() {
		if (parent instanceof ScreedGraph) {
			return ((ScreedGraph) parent).getVertexsName(u1) + " + "
					+ ((ScreedGraph) parent).getVertexsName(u2) + " = "
					+ getVertexsName(u1);
		} else {
			return (new Integer(u1 + 1)).toString() + " + "
					+ (new Integer(u2 + 1)).toString() + " -> "
					+ getVertexsName(u1);
		}

	}
}
