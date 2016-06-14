import java.lang.Math;
import java.math.BigInteger;
import java.util.*;

public class STreeNode {
	public int[] loc = new int[2];
	public double cost;
	public double hn;
	public double fn;
	public long depth;
	public STreeNode parent;
	public STreeNode leftChild;
	public STreeNode topChild;
	public STreeNode rightChild;
	public STreeNode bottomChild;
	
	public STreeNode(int[] l) {
		// Primarily used for root which has no parent
		loc[0] = l[0];
		loc[1] = l[1];
		cost = 0;
	}
	
	public STreeNode(int r,int c,STreeNode p) {
		// used for bfs and dfs
		loc[0] = r;
		loc[1] = c;
		this.parent = p;
		this.depth = p.depth + 1;
		cost = p.cost + 1;
		// for uniform cost only
		//cost = p.cost + Math.pow(0.8,c); // cost c1
		//cost = p.cost + Math.pow(2,c); // cost c2
	}
	
	public STreeNode(int r,int c,STreeNode p,int[] g) {
		// used for greedy and A*
		loc[0] = r;
		loc[1] = c;
		this.parent = p;
		cost = p.cost + 1;
		this.depth = p.depth + 1;
		hn = Math.abs(r-g[0]) + Math.abs(c-g[1]);
		fn = cost + hn;
	}
	
	public int check(PriorityQueue pq,String s) {
		Iterator it = pq.iterator();
		while (it.hasNext()) {
			STreeNode n = (STreeNode)it.next();
			if ((n.loc[0] == this.loc[0]) && (n.loc[1] == this.loc[1])) {
				if (s == "uniformCost" && (this.cost < n.cost)) {
					pq.remove(n);
					pq.add(this);
					return 1;
				}
				else if (s == "Astar" && (this.fn < n.fn)) {
					pq.remove(n);
					pq.add(this);
					return 1;
				}
				else
					return -1;
			}
		}
		return 0;
	}
}
