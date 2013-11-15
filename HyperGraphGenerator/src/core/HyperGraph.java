package core;

import java.util.ArrayList;
import java.util.List;

public class HyperGraph {

	List<List<Boolean>> matrix;

	public HyperGraph(int v, int r) {
		matrix = new ArrayList<List<Boolean>>();
		for (int i=0; i<v; i++) {
			List<Boolean> row = new ArrayList<Boolean>();
			for (int j=0; j<r; j++)
				row.add(false);
			matrix.add(row);
		}
	}
	
	List<List<Boolean>> getMatrix() {
		return matrix;
	}
	
	void setMatrix(List<List<Boolean>> matrix) {
		this.matrix = matrix;
	}
	
}
