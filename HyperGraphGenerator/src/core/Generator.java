package core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import entities.HyperGraph;

/*
 * ��������� ���������� ���������
 */
public class Generator {
	private HyperGraph graph = null;

	private final static int DELTA_EDGE = 3;
	// �������, ��������� ��� ������������� � ������
	private List<Integer> fixedVertex;

	private Random r;

	// ���������� ���������
	public Generator() {
		fixedVertex = new ArrayList<Integer>();
		r = new Random();
	}

	// ������� ��������� ���������

	public void generate(int v) {
		fixedVertex.clear();
		// ������ ��� ������� ����������
		for (int i = 0; i < v; i++)
			fixedVertex.add(i);
		// ������ ������ ��������� � ��� ������������ ������ ������
		graph = new HyperGraph(v);
		int i;
		// ������� ��������� �����, ��������� ������ ��������� �������
		// ��������� ������� � ����� - ������� �� ���������, �� ���� ������ ��
		// ��� ����� �� ����� ��������
		// ��� ��� � ������ ����� ������� 2 �������, �� ���� ����� �������
		// ��� ��� ����� �� 1 ������ ��� ������, �� �� ��������� �������� �����
		// �������� ������ 2 �������
		// � ������ ���������� ����� ����� ������� 2
		for (i = 0; i < v - 1; i++) {
			fixedVertex.remove(generateEdge());
		}
		// �� ������ ������ �������� ������
		System.gc();
	}

	// ������� ��������� �����
	private int generateEdge() {
		// ��������, ������� ������ ����� ����� ���������: ��� �������� ��� 4
		// ��� ���������� ��������� ������
		int k = Math.min(r.nextInt(DELTA_EDGE) + 2, fixedVertex.size());
		List<Integer> edge = new ArrayList<Integer>();
		int e = 0;
		// ���������� ��������� ������� � ������ ��������� ������
		while (edge.size() < k) {
			e = r.nextInt(fixedVertex.size());
			if (!edge.contains(fixedVertex.get(e)))
				edge.add(fixedVertex.get(e));
		}
		// ��������� ��������� �����
		graph.addEdge(edge);
		// ���������� ����� ��������� ������� � �����, ����� ����� �� ������� ��
		// ���������
		return e;
	}

	public HyperGraph getGraph() {
		return graph;
	}

	public void setGraph(HyperGraph graph) {
		this.graph = graph;
	}

}
