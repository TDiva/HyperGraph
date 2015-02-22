package entities;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * ����� ����������
 */
public class HyperGraph {

	protected List<Integer> matrix;
	protected int v;

	public HyperGraph(int v) {
		matrix = new ArrayList<Integer>();
		this.v = v;
	}

	public HyperGraph(int v, int r) {
		this.v = v;
		matrix = new ArrayList<Integer>();
		for (int i = 0; i < r; i++) {
			matrix.add(0);
		}
	}

	public List<Integer> getMatrix() {
		return matrix;
	}

	public void setMatrix(List<Integer> matrix) {
		this.matrix = matrix;
	}

	public int getV() {
		return v;
	}

	public void setV(int v) {
		this.v = v;
	}

	public int getR() {
		return matrix.size();
	}

	// ��������� �����: list - ������ ������� ������
	public void addEdge(List<Integer> list) {
		if (list.size() < 2)
			return;
		int row = 0;
		// �������� ���: � ����� �� i-�� ������� � �������� ���� ����� �������,
		// ���� i-�� ������� ����� � ����� � 0 - ���� ���
		for (int i = v - 1; i >= 0; i--) {
			if (list.contains(i)) {
				row = row * 2 + 1;
			} else {
				row *= 2;
			}
		}
		matrix.add(row);
	}

	// ��������� ��� �������������� �����
	public void addEdge(int edge) {
		matrix.add(edge);
	}

	// ������� ����� �� �������
	public void removeEdge(int index) {
		matrix.remove(index);
	}

	// ��������� ������ ����� � ��������� ������ - ��� ������������ � �������
	public Integer[][] toArray() {
		Integer[][] arr = new Integer[getR()][];
		for (int i = 0; i < getR(); i++) {
			arr[i] = new Integer[v];
		}

		for (int i = 0; i < matrix.size(); i++) {
			int row = matrix.get(i);
			for (int j = 0; j < v; j++, row /= 2) {
				arr[i][j] = row % 2;
			}
		}

		return arr;
	}

	// ���������, ����� �� �� ��������� ��������� ����� v (������������)
	// ��������� ����� �
	// �� ���� ��� �� �������, ����������� � e ����������� � v
	private boolean isEdgeBasedOnVertexs(int e, int v) {
		for (; e > 0; v /= 2, e /= 2) {
			if (e % 2 == 1 && v % 2 == 0)
				return false;
		}
		return true;
	}

	// ���� ����� �������� ���� ������� - ��� ��� �� ����� :D
	public boolean isSingleEdge(int k) {
		int edge = matrix.get(k);
		int count = 0;
		for (int i = edge; i > 0; i /= 2) {
			count += i % 2;
		}
		return (count < 2);
	}

	// ���������, �������� �� ����� 2 �������, �� ���� ����� �� ��� �������
	protected boolean isDoubleEdge(int k) {
		int edge = matrix.get(k);
		int count = 0;
		for (int i = edge; i > 0; i /= 2) {
			count += i % 2;
		}
		return (count == 2);
	}

	public static HyperGraph readFromFile(File file) {
		try {
			Scanner s = new Scanner(file);

			int v = s.nextInt();
			int r = s.nextInt();
			HyperGraph h = new HyperGraph(v);
			for (int i = 0; i < r; i++) {
				List<Integer> edge = new ArrayList<>();
				for (int j = 0; j < v; j++) {
					if (s.nextInt() == 1) {
						edge.add(j);
					}
				}
				if (edge.size() > 1) {
					h.addEdge(edge);
				}
			}
			return h;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}

	}

	public void printToLog() {
		Integer[][] arr = toArray();
		for (int i = 0; i < getR(); i++) {
			for (int j = 0; j < getV(); j++) {
				System.out.print(arr[i][j] + " ");
			}
			System.out.println();
		}
	}

}
