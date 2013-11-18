package core;

import java.util.ArrayList;
import java.util.List;

public class HyperGraph {

	List<Integer> matrix;
	int v;

	public HyperGraph(int v) {
		matrix = new ArrayList<Integer>();
		this.v = v;
	}
	
	List<Integer> getMatrix() {
		return matrix;
	}
	
	void setMatrix(List<Integer> matrix) {
		this.matrix = matrix;
	}
	
	public int getV() {
		return v;
	}

	public void setV(int v) {
		this.v = v;
	}

	// list = numbers of incident vertex
	public void addEdge(List<Integer> list) {
		int row = 0;
		for (int i=v-1; i>=0; i--) {
			if (list.contains(i)) {
				row=row*2+1;
			} else {
				row*=2;
			}		
		}
		matrix.add(row);
	}
	
	public void removeEdge(int index) {
		matrix.remove(index);
	}
	
	// TODO: implement
	public boolean isConnected() {
		for (int i=0; i<v; i++) {
			
		}
		return true;
	}
	
	public void print() {
		for (int i=0; i<matrix.size(); i++) {
			int row = matrix.get(i);
			for (int j=0; j<v; j++,row/=2) {
				System.out.print(row%2);
				System.out.print("\t");
			}
			System.out.println();
		}
	}
}
