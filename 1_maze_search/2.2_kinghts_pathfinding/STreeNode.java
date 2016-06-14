import java.lang.Math;

public class STreeNode {
	public int[] loc = new int[2];
	public double cost;
	public double hn;
	public double fn;
	public STreeNode parent;
	public STreeNode aChild;
	public STreeNode bChild;
	public STreeNode cChild;
	public STreeNode dChild;
	public STreeNode eChild;
	public STreeNode fChild;
	public STreeNode gChild;
	public STreeNode hChild;
	
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
		cost = p.cost + 1;
	}
	
	public STreeNode(int r,int c,STreeNode p,int[] g) {
		// used for greedy and A*
		loc[0] = r;
		loc[1] = c;
		this.parent = p;
		cost = p.cost + 1;
		hn = Math.sqrt(Math.pow(r-g[0],2) + Math.pow(c-g[1],2));
		fn = cost + hn;
	}
}
