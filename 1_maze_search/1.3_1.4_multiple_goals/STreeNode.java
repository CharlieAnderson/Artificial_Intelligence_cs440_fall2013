import java.lang.Math;
import java.math.BigInteger;
import java.util.*;

public class STreeNode {
	public int[] loc = new int[2];
	public double cost;
	public double hn;
	public double fn;
	public long depth;
	public List<Dots> dotsEaten;
	public List<STreeNode> nodesInBranch;
	public List<Dots> dotsNotEaten;
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
		depth = 0;
		this.nodesInBranch = new ArrayList<STreeNode>();
		this.dotsEaten = new ArrayList<Dots>();
	}

	public STreeNode(int r,int c,STreeNode p) {
		// used for bfs and dfs
		loc[0] = r;
		loc[1] = c;
		this.parent = p;
		this.depth = p.depth + 1;
		this.cost = p.cost + 1;
		// Create new instance of the dotsEaten list in the child node
		this.dotsEaten = new ArrayList<Dots>(p.dotsEaten);
		this.nodesInBranch = new ArrayList<STreeNode>(p.nodesInBranch);
	}

	public STreeNode(int r,int c,STreeNode p,ReadMaze rm) {
		// used for greedy and A*
		this(r,c,p);
		List<Dots> temp = new ArrayList<Dots>();
		Iterator it1 = rm.dl.iterator();
		while (it1.hasNext()) {
			Dots d1 = (Dots)it1.next();
			boolean inList = false;
			Iterator it2 = p.dotsEaten.iterator();
			while (it2.hasNext()) {
				Dots d2 = (Dots)it2.next();
				if (d1.r == d2.r && d1.c == d2.c) {
					inList = true;
					break;
				}
			}
			if (!inList) {
				temp.add(d1);
			}
		}

		hn = maxManhattan(temp, r, c);
		// hn = heuristic14(temp, r, c); /* Non-admissable, use only for sub-optimal */
		fn = cost + hn;
	}

	private int maxManhattan(List<Dots> dots, int r, int c) {
		int x = 0;
		Iterator it = dots.iterator();
		//System.out.println("Here now");
		while (it.hasNext()) {
			Dots d = (Dots)it.next();
			int val = Math.abs(d.r-r) + Math.abs(d.c-c);
			if (val > x) {
				x = val;
			}
		}

		return x;
	}

	private double sumAverageDots(List<Dots> dots, int r, int c) {
		double x = 0;
		Iterator it = dots.iterator();
		//System.out.println("Here now");
		while (it.hasNext()) {
			Dots d = (Dots)it.next();

			x += Math.abs(d.r-r) + Math.abs(d.c-c);
		}

		x = x/dots.size();

		return x;
	}

	private int heuristic14(List<Dots> dots, int r, int c) {
		int x = 10000;
		Dots cur = null;
		Iterator it = dots.iterator();
		//System.out.println("Here now");
		while (it.hasNext()) {
			Dots d = (Dots)it.next();

			int val = Math.abs(d.r-r) + Math.abs(d.c-c);
			List<Dots> newDots = new ArrayList<Dots>(dots);
			val += heuristicRec(newDots, d.r, d.c);
			if (val < x) {
				x = val;
				cur = d;
			}
		}

		return x;
	}

	private int heuristicRec(List<Dots> dots, int r, int c) {
		int x = 10000;
		Dots cur = null;
		Iterator it = dots.iterator();
		//System.out.println("Here now");
		while (it.hasNext()) {
			Dots d = (Dots)it.next();

			int val = Math.abs(d.r-r) + Math.abs(d.c-c);
			if (val < x) {
				x = val;
				cur = d;
			}
		}

		dots.remove(cur);

		if (!dots.isEmpty()) {
			x += heuristicRec(dots, cur.r, cur.c);
		}

		return x;
	}

	private double euclidean(List<Dots> dots, int r, int c) {
		double x = 10000;
		Dots cur = null;
		Iterator it = dots.iterator();
		//System.out.println("Here now");
		while (it.hasNext()) {
			Dots d = (Dots)it.next();

			double val = Math.sqrt(Math.pow(d.r-r,2) + Math.pow(d.c-c,2));
			if (val < x) {
				x = val;
				cur = d;
			}
		}

		dots.remove(cur);

		if (!dots.isEmpty()) {
			x += heuristicRec(dots, cur.r, cur.c);
		}

		return x;
	}
}
