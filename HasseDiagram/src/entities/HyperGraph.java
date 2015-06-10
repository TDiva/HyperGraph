package entities;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class HyperGraph {

//	список ребер
	protected List<Edge> e;
//	количество вершин
	protected int v;

//	конструктор
	public HyperGraph(int v) {
		e = new ArrayList<Edge>();
		this.v = v;
	}

	public int getV() {
		return v;
	}

	public void setV(int v) {
		this.v = v;
	}

	public List<Edge> getE() {
		return e;
	}

	public void setE(List<Edge> e) {
		this.e = e;
	}

	public int getR() {
		return e.size();
	}

//	добавить ребро
	public void addEdge(Edge e) {
		this.e.add(e);
	}

//	удалить ребро
	public void removeEdge(int index) {
		e.remove(index);
	}

//	получить ребро
	public Edge getEdge(int index) {
		return e.get(index);
	}
	
// чтение из строки всего графа 
	public static HyperGraph readFromString(String text) {
		BufferedReader r = new BufferedReader(new StringReader(text));
		return read(r);
	}

//	чтение графа из потока
	private static HyperGraph read(BufferedReader r) {
		String line;
		try {
			line = r.readLine();
//			первая строка количество вершин
			Integer v = line == null ? 0 : Integer.valueOf(line);
			HyperGraph hyperGraph = new HyperGraph(v);

//			остальные описывают ребра
			while ((line = r.readLine()) != null) {
				Edge e = new Edge(line);
				hyperGraph.addEdge(e);
			}

			return hyperGraph;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}

//	чтение из файла
	public static HyperGraph readFromFile(File file)
			throws FileNotFoundException {
		BufferedReader r;
		r = new BufferedReader(new FileReader(file));
		return read(r);
	}

//	записать в файл
	public void printToFile(PrintWriter w) {
		w.println(v);
		for (Edge edge : e) {
			w.println(edge.toString());
		}
	}

//	текстовое представление
	public String toString() {
		StringBuffer sb = new StringBuffer(v + "\n");
		for (Edge edge : e) {
			sb.append(edge.toString() + "\n");
		}
		return sb.toString();
	}

	// первое условие
	public boolean hasDoubleEdge() {
		for (Edge edge : e) {
			if (edge.getDegree() == 2) {
				return true;
			}
		}
		return false || v < 2;
	}

	// второе условие
	public boolean isClose() {
		if (v == 1)
			return true;
		Boolean p[] = new Boolean[v];
		Arrays.fill(p, false);

//		поиск в ширину
		Integer v = 1;
		Queue<Integer> q = new LinkedList<>();
		q.add(v);
		do {
			v = q.poll();
			for (Edge edge : e) {
				if (edge.containsVertex(v)) {
					for (Integer u : edge.getVertexs()) {
						if (!p[u - 1]) {
							q.add(u);
							p[u - 1] = true;
						}
					}
				}
			}

		} while (!q.isEmpty());

		boolean flag = true;
		for (int i = 0; i < p.length; i++) {
			flag &= p[i];
		}
		return flag;
	}

	// третье условие
	public boolean isCorrectVertexs() {
		return (getV() == getR() + 1) || getV() == 0;
	}

//	получение всех подграфов
	public List<SubGraph> getSSubGraphs() {
		List<SubGraph> list = new ArrayList<>();

//		генерируем все возможные подмножества вершин
		for (int i = 0; i < Math.pow(2d, v); i++) {
			List<Integer> vertexs = new ArrayList<>();
			int x = i;
			int index = 1;
			while (x > 0) {
				if (x % 2 == 1) {
					vertexs.add(index);
				}
				x /= 2;
				index++;
			}
//			получаем индуцированый подграф
			SubGraph sg = new SubGraph(vertexs, this);

//			проверяем условия стягиваемости
			if (sg.isScreed()) {
				list.add(sg);
			}
		}
		Collections.sort(list);
		return list;
	}

//	проверяем на ошибки
	public boolean hasErrors() {
		Set<Edge> cache = new HashSet<>();
		for (Edge edge : e) {
			if (cache.contains(edge)) {
				return true;
			} else {
				Set<Integer> v = new HashSet<>(edge.getVertexs());
				if (v.size() != edge.getDegree() || v.size() < 2) {
					return true;
				}
			}
			cache.add(edge);
		}
		return false;
	}

//	выводим ошибки
	public void outputErrors(JTextPane output) {

		StyleContext sc1 = StyleContext.getDefaultStyleContext();
		AttributeSet standardSet = sc1.addAttribute(SimpleAttributeSet.EMPTY,
				StyleConstants.Background, Color.WHITE);

		StyleContext sc2 = StyleContext.getDefaultStyleContext();
		AttributeSet wrongSet = sc2.addAttribute(SimpleAttributeSet.EMPTY,
				StyleConstants.Background, Color.RED);

		output.setCharacterAttributes(standardSet, false);
		output.replaceSelection(v + "\n");
		output.setText("");
		output.setCaretPosition(0);
		output.setCharacterAttributes(standardSet, false);
		output.replaceSelection(v + "\n");

		Set<Edge> cache = new HashSet<>();
		for (Edge edge : e) {
//			повторяющееся ребро
			if (cache.contains(edge)) {
				output.setCaretPosition(output.getDocument().getLength());
				output.setCharacterAttributes(wrongSet, false);
				output.replaceSelection(edge.toString() + "\n");
			} else {
				Set<Integer> v = new HashSet<>(edge.getVertexs());
				output.setCharacterAttributes(standardSet, false);
//				меньше чем 2 вершины в ребре
				if (v.size() < 2) {
					output.setCaretPosition(output.getDocument().getLength());
					output.setCharacterAttributes(wrongSet, false);
					output.replaceSelection(edge.toString() + "\n");
//					все хорошо
				} else if (v.size() == edge.getDegree()) {
					output.setCaretPosition(output.getDocument().getLength());
					output.setCharacterAttributes(standardSet, false);
					output.replaceSelection(edge.toString() + "\n");
				} else {
//					в ребре повторяются вершины
					output.setCaretPosition(output.getDocument().getLength());
					output.setCharacterAttributes(standardSet, false);
					output.replaceSelection(edge.getVertexs().get(0) + " ");
					for (int i = 1; i < edge.getDegree(); i++) {
						output.setCaretPosition(output.getDocument()
								.getLength());
						if (edge.getVertexs().get(i) == edge.getVertexs().get(
								i - 1)) {
							output.setCharacterAttributes(wrongSet, false);
						} else {
							output.setCharacterAttributes(standardSet, false);
						}
						output.replaceSelection(edge.getVertexs().get(i) + " ");
					}
					output.setCaretPosition(output.getDocument().getLength());
					output.setCharacterAttributes(standardSet, false);
					output.replaceSelection("\n");
				}
			}
			cache.add(edge);
		}
		output.setCharacterAttributes(standardSet, false);
	}
}
