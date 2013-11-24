package core;

import java.util.ArrayList;
import java.util.List;

public class HyperGraph {

	List<Integer> matrix;
	int v;
	
	List<SubGraph> subgraphs;

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
	
	public int getR() {
		return matrix.size();
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
	
	public void addEdge(int edge) {
		matrix.add(edge);
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
	
	public Integer[][] toArray() {
		Integer[][] arr = new Integer[getR()][];
		for (int i=0; i<getR(); i++) {
			arr[i] = new Integer[v];
		}
		
		for (int i=0; i<matrix.size(); i++) {
			int row = matrix.get(i);
			for (int j=0; j<v; j++,row/=2) {
				arr[i][j] = row%2;
			}
		}
		
		return arr;
	}
	
	private boolean isEdgeBasedOnVertexs(int e, int v) {
		for (; e>0; v/=2, e/=2) {
			if (e%2 == 1 && v%2 == 0)
				return false;
		}
		return true;
	}
	
	private void generateSubgraphs(int v) {
		List<Integer> edges = new ArrayList<Integer>();
		for (Integer e: matrix) {
			if (isEdgeBasedOnVertexs(e,v))
				edges.add(e);
		}
		for (int e=0; e<Math.pow(2d, edges.size()); e++) {
			SubGraph sub = new SubGraph(this.v,v);
			for (int k=e, i=0;k>0; k/=2,i++) {
				if (k%2 == 1) 
					sub.addEdge(edges.get(i));
			}
			subgraphs.add(sub);
		}
	}
	
	private void generateSubGraphs() {
		subgraphs = new ArrayList<SubGraph>();
		for (int i=0; i<Math.pow(2d,v); i++) {
			generateSubgraphs(i);
		}
	}
	
	public List<SubGraph> getSubGraphs() {
		if (subgraphs==null)
			generateSubGraphs();
		return subgraphs;
	}
}
