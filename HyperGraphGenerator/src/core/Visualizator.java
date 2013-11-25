package core;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Visualizator {
	private static final int MAX_WIDTH = 700;
	private static final int MAX_HEIGHT = 500;

	
	public static JTable fillMatrix(JTable matrix, HyperGraph graph) {
		String[] headers = new String[graph.getV()];
		for (int i=0; i<graph.getV(); i++) {
			headers[i] = "V-"+(i+1);
		}
		matrix.setModel(new DefaultTableModel(graph.toArray(), headers));
		matrix.doLayout();
		return matrix;
	}
	
	public static JTable fillMatrix(JTable matrix, SubGraph graph) {
		String[] headers = new String[graph.getVertexs().size()];
		for (int i=0; i<graph.getVertexs().size(); i++) {
			headers[i] = "V-"+(graph.getVertexs().get(i)+1);
		}
		matrix.setModel(new DefaultTableModel(graph.toArray(), headers));
		matrix.doLayout();
		return matrix;
	}
}
