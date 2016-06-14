import java.io.*;
import static java.lang.System.*;
import java.lang.*;
import java.util.Arrays;
import java.util.*;

public class SearchTree {
	public STreeNode root;
	public STreeNode finalGoal;
	public long size;
	public long maxDepth;
	public long maxNodes = 10000000;
	public long frontierMaxSize = 0;
	//public List<STreeNode> allNodes = new ArrayList<STreeNode>();

	public SearchTree(int[] s) {
		root = new STreeNode(s);
		size = 1;
		maxDepth = 0;
	}

	public void updateFrontierSize(Deque dq) {
		if (dq.size() > frontierMaxSize) {
			frontierMaxSize = dq.size();
		}
	}

	public void updateFrontierSize(PriorityQueue pq) {
		if (pq.size() > frontierMaxSize) {
			frontierMaxSize = pq.size();
		}
	}

	public void updateMaxDepth(long d) {
		if (d > maxDepth) {
			maxDepth = d;
		}
	}

	public boolean solutionPath(ReadMaze rm) {
		STreeNode n = this.finalGoal;
		String alphabet = "123456789abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz123456789abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz";
		int i = 0;
		Iterator it = n.dotsEaten.iterator();
		while (it.hasNext()) {
			Dots d = (Dots)it.next();
			rm.map[d.r][d.c] = alphabet.charAt(i);
			i++;
		}
		return true;
	}

	public boolean checkNodeValidity(int r, int c, STreeNode p, ReadMaze rm) {
		if (rm.map[r][c] != '%') {
			Iterator it = p.nodesInBranch.iterator();
			// System.out.println("Checking: "+r+" , "+c+". This is parent: "+p.loc[0]+" , "+p.loc[1]);
			// System.out.println(p.nodesInBranch);
			while (it.hasNext()) {
				STreeNode n = (STreeNode)it.next();
				// System.out.println("location: "+n.loc[0]+" , "+n.loc[1]+". Parent loc: "+n.parent.loc[0]+" , "+n.parent.loc[1]);
				if ((n.loc[0] == r) && (n.loc[1] == c) && n.dotsEaten.size() == p.dotsEaten.size()) {
					Iterator it1 = n.dotsEaten.iterator();
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
							return true;
						}
					}
					// System.out.println("Already been to");
					return false;
				}
			}
			return true;
		}
		return false;
	}

	//**************************Breadth First Search*************************//
	public boolean bfs(ReadMaze rm, STreeNode n, Deque dq) {
		int r,c;
		dq.addLast(n);
		do {
			if (!checkNodes()) {
				return false;
			}
			n = (STreeNode)dq.removeFirst();
			r = n.loc[0];
			c = n.loc[1];
			// Check if goal
			if (rm.map[r][c] == '.') {
				boolean inList = false;
				Iterator it = n.dotsEaten.iterator();
				while (it.hasNext()) {
					Dots d = (Dots)it.next();
					if (d.r == r && d.c == c) {
						inList = true;
						break;
					}
				}
				// System.out.println("Dots Eaten: "+n.dotsEaten.size()+"At: "+n.loc[0]+" , "+n.loc[1]);
				if (!inList) {
					// System.out.println("added here");
					n.dotsEaten.add(new Dots(r,c));
					if (n.dotsEaten.size() == rm.dl.size()) {
						this.finalGoal = n;
						return true;
					}
				}
			}
			// Left Child
			if (c != 0) {
				if (checkNodeValidity(r,c-1,n,rm)) {
					n.leftChild = new STreeNode(r,c-1,n); // Add to Tree
					updateMaxDepth(n.leftChild.depth);
					this.size++; // Increase size of tree
					dq.addLast(n.leftChild); // Add to frontier
					n.leftChild.nodesInBranch.add(n.leftChild);
					this.updateFrontierSize(dq);
				}
			}
			// Top Child
			if (r != 0) {
				if (checkNodeValidity(r-1,c,n,rm)) {
					n.topChild = new STreeNode(r-1,c,n); // Add to Tree
					updateMaxDepth(n.topChild.depth);
					this.size++; // Increase size of tree
					dq.addLast(n.topChild); // Add to frontier
					n.topChild.nodesInBranch.add(n.topChild);
					this.updateFrontierSize(dq);
				}
			}
			// Right Child
			if (c != ((rm.map[0].length) - 1)) {
				if (checkNodeValidity(r,c+1,n,rm)) {
					n.rightChild = new STreeNode(r,c+1,n); // Add to Tree
					updateMaxDepth(n.rightChild.depth);
					this.size++; // Increase size of tree
					dq.addLast(n.rightChild); // Add to frontier
					n.rightChild.nodesInBranch.add(n.rightChild);
					this.updateFrontierSize(dq);
				}
			}
			//Bottom Child
			if (r != ((rm.map.length) - 1)) {
				if (checkNodeValidity(r+1,c,n,rm)) {
					n.bottomChild = new STreeNode(r+1,c,n); // Add to Tree
					updateMaxDepth(n.bottomChild.depth);
					this.size++; // Increase size of tree
					dq.addLast(n.bottomChild); // Add to frontier
					n.bottomChild.nodesInBranch.add(n.bottomChild);
					this.updateFrontierSize(dq);
				}
			}
		} while(!dq.isEmpty());
		return false;
	}

	// Depth first search
	public boolean dfs(ReadMaze rm, STreeNode n, Deque dq) {
		int r,c;
		dq.addLast(n);
		do {
			if (!checkNodes()) {
				return false;
			}
			n = (STreeNode)dq.removeFirst();
			r = n.loc[0];
			c = n.loc[1];
			// Check if goal
			if (rm.map[r][c] == '.') {
				boolean inList = false;
				Iterator it = n.dotsEaten.iterator();
				while (it.hasNext()) {
					Dots d = (Dots)it.next();
					if (d.r == r && d.c == c) {
						inList = true;
						break;
					}
				}
				// System.out.println("Dots Eaten: "+n.dotsEaten.size()+"At: "+n.loc[0]+" , "+n.loc[1]);
				if (!inList) {
					// System.out.println("added here");
					n.dotsEaten.add(new Dots(r,c));
					if (n.dotsEaten.size() == rm.dl.size()) {
						this.finalGoal = n;
						return true;
					}
				}
			}
			// Left Child
			if (c != 0) {
				if (checkNodeValidity(r,c-1,n,rm)) {
					n.leftChild = new STreeNode(r,c-1,n); // Add to Tree
					updateMaxDepth(n.leftChild.depth);
					this.size++; // Increase size of tree
					dq.addFirst(n.leftChild); // Add to frontier
					n.leftChild.nodesInBranch.add(n.leftChild);
					this.updateFrontierSize(dq);
				}
			}
			// Top Child
			if (r != 0) {
				if (checkNodeValidity(r-1,c,n,rm)) {
					n.topChild = new STreeNode(r-1,c,n); // Add to Tree
					updateMaxDepth(n.topChild.depth);
					this.size++; // Increase size of tree
					dq.addFirst(n.topChild); // Add to frontier
					n.topChild.nodesInBranch.add(n.topChild);
					this.updateFrontierSize(dq);
				}
			}
			// Right Child
			if (c != ((rm.map[0].length) - 1)) {
				if (checkNodeValidity(r,c+1,n,rm)) {
					n.rightChild = new STreeNode(r,c+1,n); // Add to Tree
					updateMaxDepth(n.rightChild.depth);
					this.size++; // Increase size of tree
					dq.addFirst(n.rightChild); // Add to frontier
					n.rightChild.nodesInBranch.add(n.rightChild);
					this.updateFrontierSize(dq);
				}
			}
			//Bottom Child
			if (r != ((rm.map.length) - 1)) {
				if (checkNodeValidity(r+1,c,n,rm)) {
					n.bottomChild = new STreeNode(r+1,c,n); // Add to Tree
					updateMaxDepth(n.bottomChild.depth);
					this.size++; // Increase size of tree
					dq.addFirst(n.bottomChild); // Add to frontier
					n.bottomChild.nodesInBranch.add(n.bottomChild);
					this.updateFrontierSize(dq);
				}
			}
		} while(!dq.isEmpty());
		return false;
	}

	//**************************Greedy And Astar Search******************************//
	public boolean greedyBestAndAstar(ReadMaze rm, STreeNode n, PriorityQueue pq) {
		int r,c;
		pq.add(n);
		do {
			if (!checkNodes()) {
				return false;
			}
			n = (STreeNode)pq.poll();
			r = n.loc[0];
			c = n.loc[1];
			// Check if goal
			if (rm.map[r][c] == '.') {
				boolean inList = false;
				Iterator it = n.dotsEaten.iterator();
				while (it.hasNext()) {
					Dots d = (Dots)it.next();
					if (d.r == r && d.c == c) {
						inList = true;
						break;
					}
				}
				// System.out.println("Dots Eaten: "+n.dotsEaten.size()+"At: "+n.loc[0]+" , "+n.loc[1]);
				if (!inList) {
					// System.out.println("added here");
					n.dotsEaten.add(new Dots(r,c));
					if (n.dotsEaten.size() == rm.dl.size()) {
						this.finalGoal = n;
						return true;
					}
				}
			}
			// Left Child
			if (c != 0) {
				if (checkNodeValidity(r,c-1,n,rm)) {
					n.leftChild = new STreeNode(r,c-1,n,rm); // Add to Tree
					updateMaxDepth(n.leftChild.depth);
					this.size++; // Increase size of tree
					pq.add(n.leftChild); // Add to frontier
					n.leftChild.nodesInBranch.add(n.leftChild);
					this.updateFrontierSize(pq);
				}
			}
			// Top Child
			if (r != 0) {
				if (checkNodeValidity(r-1,c,n,rm)) {
					n.topChild = new STreeNode(r-1,c,n,rm); // Add to Tree
					updateMaxDepth(n.topChild.depth);
					this.size++; // Increase size of tree
					pq.add(n.topChild); // Add to frontier
					n.topChild.nodesInBranch.add(n.topChild);
					this.updateFrontierSize(pq);
				}
			}
			// Right Child
			if (c != ((rm.map[0].length) - 1)) {
				if (checkNodeValidity(r,c+1,n,rm)) {
					n.rightChild = new STreeNode(r,c+1,n,rm); // Add to Tree
					updateMaxDepth(n.rightChild.depth);
					this.size++; // Increase size of tree
					pq.add(n.rightChild); // Add to frontier
					n.rightChild.nodesInBranch.add(n.rightChild);
					this.updateFrontierSize(pq);
				}
			}
			//Bottom Child
			if (r != ((rm.map.length) - 1)) {
				if (checkNodeValidity(r+1,c,n,rm)) {
					n.bottomChild = new STreeNode(r+1,c,n,rm); // Add to Tree
					updateMaxDepth(n.bottomChild.depth);
					this.size++; // Increase size of tree
					pq.add(n.bottomChild); // Add to frontier
					n.bottomChild.nodesInBranch.add(n.bottomChild);
					this.updateFrontierSize(pq);
				}
			}
		} while(!pq.isEmpty());
		return false;
	}

	private boolean checkNodes() {
		if (this.size % 100000 == 0) {
			System.out.println("At " + this.size + " nodes");
		}
		if (this.size >= this.maxNodes) {
			System.out.println("Reached the maximum number of nodes: " + this.maxNodes);
			return false;
		}
		return true;
	}
}