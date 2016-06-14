import java.io.*;
import static java.lang.System.*;
import java.lang.*;
import java.util.Arrays;
import java.util.*;

public class SearchTree {
	public STreeNode root;
	public STreeNode goal;
	public long size;
	public long maxDepth;
	public long frontierMaxSize = 0;
	
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
	
	public boolean solutionPath(ReadMaze rm, STreeNode n) {
		if (n == this.root) {
			return true;
		}
		else {
			int r = n.loc[0];
			int c = n.loc[1];
			rm.map[r][c] = '.';
			this.solutionPath(rm,n.parent);
		}
		return true;
	}
		
	//*********A* search with full repeated state detection*****************//
	public boolean Astar1(ReadMaze rm, STreeNode n, PriorityQueue pq) {
		pq.add(n);
		while(!pq.isEmpty()) {
			n = (STreeNode)pq.remove();
			int r = n.loc[0];
			int c = n.loc[1];
			// Check if goal
			if (rm.map[r][c] == '.') {
				this.goal = n;
				rm.map[r][c] = 'G';
				return true;
			}
			else {
				// Left Child
				if (c != 0) {
					// Not visited and either empty or goal
					if (rm.visited[r][c-1] == 0 && (rm.map[r][c-1] == ' ' ||
						rm.map[r][c-1] == '.')) {
						rm.visited[r][c-1] = 1; // Mark this node as visited
						n.leftChild = new STreeNode(r,c-1,n,rm.goal); // Add to Tree
						updateMaxDepth(n.leftChild.depth);
						this.size++; // Increase size of tree
						pq.add(n.leftChild); // Add to frontier
						this.updateFrontierSize(pq);
						}
				}
				// Top Child
				if (r != 0) {
					// Not visited and either empty or goal
					if (rm.visited[r-1][c] == 0 && (rm.map[r-1][c] == ' ' ||
						rm.map[r-1][c] == '.')) {
						rm.visited[r-1][c] = 1; // Mark this node as visited
						n.topChild = new STreeNode(r-1,c,n,rm.goal); // Add to Tree
						updateMaxDepth(n.topChild.depth);
						this.size++; // Increase size of tree
						pq.add(n.topChild); // Add to frontier
						this.updateFrontierSize(pq);
						}
				}
				// Right Child
				if (c != ((rm.map[0].length) - 1)) {
					// Not visited and either empty or goal
					if (rm.visited[r][c+1] == 0 && (rm.map[r][c+1] == ' ' ||
						rm.map[r][c+1] == '.')) {
						rm.visited[r][c+1] = 1; // Mark this node as visited
						n.rightChild = new STreeNode(r,c+1,n,rm.goal); // Add to Tree
						updateMaxDepth(n.rightChild.depth);
						this.size++; // Increase size of tree
						pq.add(n.rightChild); // Add to frontier
						this.updateFrontierSize(pq);
						}
				}
				// Bottom Child
				if (r != ((rm.map.length) - 1)) {
					// Not visited and either empty or goal
					if (rm.visited[r+1][c] == 0 && (rm.map[r+1][c] == ' ' ||
						rm.map[r+1][c] == '.')) {
						rm.visited[r+1][c] = 1; // Mark this node as visited
						n.bottomChild = new STreeNode(r+1,c,n,rm.goal); // Add to Tree
						updateMaxDepth(n.bottomChild.depth);
						this.size++; // Increase size of tree
						pq.add(n.bottomChild); // Add to frontier
						this.updateFrontierSize(pq);
						}
				}
			}
		}
		return false;
	}
	
	//***A* Search with repeated state detection inside the priority queue***//
	public boolean Astar2(ReadMaze rm, STreeNode n, PriorityQueue pq) {
		pq.add(n);
		while(!pq.isEmpty()) {
			n = (STreeNode)pq.remove();
			int r = n.loc[0];
			int c = n.loc[1];
			// Check if goal
			if (rm.map[r][c] == '.') {
				this.goal = n;
				rm.map[r][c] = 'G';
				return true;
			}
			else {
				// Left Child
				if (c != 0) {
					// either empty or goal
					if (rm.map[r][c-1] == ' ' || rm.map[r][c-1] == '.') {
						n.leftChild = new STreeNode(r,c-1,n,rm.goal); // Add to Tree
						updateMaxDepth(n.leftChild.depth);
						int var = n.leftChild.check(pq,"Astar");
						if (var == -1) {
							n.leftChild = null;
						}
						else if (var == 0) {
							pq.add(n.leftChild); // Add to frontier
							this.size++; // Increase size of tree
						}
						else
							this.size++; // Increase size of tree
						this.updateFrontierSize(pq);
					}
				}
				// Top Child
				if (r != 0) {
					// either empty or goal
					if (rm.map[r-1][c] == ' ' || rm.map[r-1][c] == '.') {
						n.topChild = new STreeNode(r-1,c,n,rm.goal); // Add to Tree
						updateMaxDepth(n.topChild.depth);
						int var = n.topChild.check(pq,"Astar");
						if (var == -1) {
							n.topChild = null;
						}
						else if (var == 0) {
							pq.add(n.topChild); // Add to frontier
							this.size++; // Increase size of tree
						}
						else
							this.size++; // Increase size of tree
						this.updateFrontierSize(pq);
					}
				}
				// Right Child
				if (c != ((rm.map[0].length) - 1)) {
					// either empty or goal
					if (rm.map[r][c+1] == ' ' || rm.map[r][c+1] == '.') {
						n.rightChild = new STreeNode(r,c+1,n,rm.goal); // Add to Tree
						updateMaxDepth(n.rightChild.depth);
						int var = n.rightChild.check(pq,"Astar");
						if (var == -1) {
							n.rightChild = null;
						}
						else if (var == 0) {
							pq.add(n.rightChild); // Add to frontier
							this.size++; // Increase size of tree
						}
						else
							this.size++; // Increase size of tree
						this.updateFrontierSize(pq);
					}
				}
				// Bottom Child
				if (r != ((rm.map.length) - 1)) {
					// either empty or goal
					if (rm.map[r+1][c] == ' ' || rm.map[r+1][c] == '.') {
						n.bottomChild = new STreeNode(r+1,c,n,rm.goal); // Add to Tree
						updateMaxDepth(n.bottomChild.depth);
						int var = n.bottomChild.check(pq,"Astar");
						if (var == -1) {
							n.bottomChild = null;
						}
						else if (var == 0) {
							pq.add(n.bottomChild); // Add to frontier
							this.size++; // Increase size of tree
						}
						else
							this.size++; // Increase size of tree
						this.updateFrontierSize(pq);
					}
				}
			}
		}
		return false;
	}
}
