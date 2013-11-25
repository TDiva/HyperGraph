package core;

import java.util.ArrayList;
import java.util.List;


public class ScreedGraph extends HyperGraph {
	
	private HyperGraph parent;
	int u;
	int v;

	public ScreedGraph(HyperGraph parent, int k) {
		super(parent.getV()-1);
		this.parent = parent;
		
		getBorders(parent.getMatrix().get(k));
		
		for (int i=0; i<parent.getMatrix().size(); i++) {
			if (i==k)
				continue;
			List<Integer> buf = new ArrayList<Integer>();
			for (int d = parent.getMatrix().get(i),j=0; d>0; d/=2,j++ ) {
				buf.set(j, d%2);
			}
			buf.set(u, buf.get(u) & buf.get(v));
			buf.remove(v);
			
			addEdge(buf);
		}
	}

	public HyperGraph getParent() {
		return parent;
	}


	public void setParent(HyperGraph parent) {
		this.parent = parent;
	}


	public int getU() {
		return u;
	}


	public void setU(int u) {
		this.u = u;
	}


	public int getV() {
		return v;
	}


	public void setV(int v) {
		this.v = v;
	}


	private void getBorders(int edge) {
		u = -1;
		v = -1;
		for (int i=0, e=edge; e>0; e/=2,i++) {
			if (e%2 == 1) {
				if (u == -1) {
					u = i;
				} else {
					v = i;
				}
					
			}
		}
	}
	
	
}
