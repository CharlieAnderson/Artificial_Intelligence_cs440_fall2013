import java.io.*;
import static java.lang.System.*;
import java.lang.*;
import java.util.Arrays;
import java.util.*;

public class SearchTree {
	public STreeNode root;
	public STreeNode goal;
	public int size;
	public int frontierMaxSize = 0;
	
	public SearchTree(int[] s) {
		root = new STreeNode(s);
		size = 1;
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
	
	public int maxDepth(STreeNode n) {
		int depth = 0;
		if (n.aChild != null)
			depth = Math.max(depth, maxDepth(n.aChild));
		if (n.bChild != null)
			depth = Math.max(depth, maxDepth(n.bChild));
		if (n.cChild != null)
			depth = Math.max(depth, maxDepth(n.cChild));
		if (n.dChild != null)
			depth = Math.max(depth, maxDepth(n.dChild));
		if (n.eChild != null)
			depth = Math.max(depth, maxDepth(n.eChild));
		if (n.fChild != null)
			depth = Math.max(depth, maxDepth(n.fChild));
		if (n.gChild != null)
			depth = Math.max(depth, maxDepth(n.gChild));
		if (n.hChild != null)
			depth = Math.max(depth, maxDepth(n.hChild));
		return depth+1;
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
	
	// Depth First Search
	public boolean dfs(ReadMaze rm, STreeNode n, Deque dq) {
		int r = n.loc[0];
		int c = n.loc[1];
		// Check if goal
		if (rm.map[r][c] == '.') {
			this.goal = n;
			rm.map[r][c] = 'G';
			return true;
		}
		else {
			// a Child
			if (r >= 2 && c <= rm.map[0].length-2) {
				// Not visited and either empty or goal
				if (rm.visited[r-2][c+1] == 0 && (rm.map[r-2][c+1] == ' ' ||
					rm.map[r-2][c+1] == '.')) {
					rm.visited[r-2][c+1] = 1; // Mark this node as visited
					n.aChild = new STreeNode(r-2,c+1,n); // Add to Tree
					this.size++; // Increase size of tree
					dq.addFirst(n.aChild); // Add to frontier
					this.updateFrontierSize(dq);
					}
			}
			// b Child
			if (r >= 1 && c <= rm.map[0].length-3) {
				// Not visited and either empty or goal
				if (rm.visited[r-1][c+2] == 0 && (rm.map[r-1][c+2] == ' ' ||
					rm.map[r-1][c+2] == '.')) {
					rm.visited[r-1][c+2] = 1; // Mark this node as visited
					n.bChild = new STreeNode(r-1,c+2,n); // Add to Tree
					this.size++; // Increase size of tree
					dq.addFirst(n.bChild); // Add to frontier
					this.updateFrontierSize(dq);
					}
			}
			// c Child
			if (r <= rm.map.length-2 && c <= rm.map[0].length-3) {
				// Not visited and either empty or goal
				if (rm.visited[r+1][c+2] == 0 && (rm.map[r+1][c+2] == ' ' ||
					rm.map[r+1][c+2] == '.')) {
					rm.visited[r+1][c+2] = 1; // Mark this node as visited
					n.cChild = new STreeNode(r+1,c+2,n); // Add to Tree
					this.size++; // Increase size of tree
					dq.addFirst(n.cChild); // Add to frontier
					this.updateFrontierSize(dq);
					}
			}
			// d Child
			if (r <= rm.map.length-3 && c <= rm.map[0].length-2) {
				// Not visited and either empty or goal
				if (rm.visited[r+2][c+1] == 0 && (rm.map[r+2][c+1] == ' ' ||
					rm.map[r+2][c+1] == '.')) {
					rm.visited[r+2][c+1] = 1; // Mark this node as visited
					n.dChild = new STreeNode(r+2,c+1,n); // Add to Tree
					this.size++; // Increase size of tree
					dq.addFirst(n.dChild); // Add to frontier
					this.updateFrontierSize(dq);
					}
			}
			// e Child
			if (r <= rm.map.length-3 && c >= 1) {
				// Not visited and either empty or goal
				if (rm.visited[r+2][c-1] == 0 && (rm.map[r+2][c-1] == ' ' ||
					rm.map[r+2][c-1] == '.')) {
					rm.visited[r+2][c-1] = 1; // Mark this node as visited
					n.eChild = new STreeNode(r+2,c-1,n); // Add to Tree
					this.size++; // Increase size of tree
					dq.addFirst(n.eChild); // Add to frontier
					this.updateFrontierSize(dq);
					}
			}
			// f Child
			if (r <= rm.map.length-2 && c >= 2) {
				// Not visited and either empty or goal
				if (rm.visited[r+1][c-2] == 0 && (rm.map[r+1][c-2] == ' ' ||
					rm.map[r+1][c-2] == '.')) {
					rm.visited[r+1][c-2] = 1; // Mark this node as visited
					n.fChild = new STreeNode(r+1,c-2,n); // Add to Tree
					this.size++; // Increase size of tree
					dq.addFirst(n.fChild); // Add to frontier
					this.updateFrontierSize(dq);
					}
			}
			// g Child
			if (r >= 1 && c >= 2) {
				// Not visited and either empty or goal
				if (rm.visited[r-1][c-2] == 0 && (rm.map[r-1][c-2] == ' ' ||
					rm.map[r-1][c-2] == '.')) {
					rm.visited[r-1][c-2] = 1; // Mark this node as visited
					n.gChild = new STreeNode(r-1,c-2,n); // Add to Tree
					this.size++; // Increase size of tree
					dq.addFirst(n.gChild); // Add to frontier
					this.updateFrontierSize(dq);
					}
			}
			// h Child
			if (r >= 2 && c >= 1) {
				// Not visited and either empty or goal
				if (rm.visited[r-2][c-1] == 0 && (rm.map[r-2][c-1] == ' ' ||
					rm.map[r-2][c-1] == '.')) {
					rm.visited[r-2][c-1] = 1; // Mark this node as visited
					n.hChild = new STreeNode(r-2,c-1,n); // Add to Tree
					this.size++; // Increase size of tree
					dq.addFirst(n.hChild); // Add to frontier
					this.updateFrontierSize(dq);
					}
			}
			// Remove next node from the frontier
			boolean result = dfs(rm,(STreeNode)dq.removeFirst(),dq);
			return result;
		}
	}
	
	// Breadth First Search
	public boolean bfs(ReadMaze rm, STreeNode n, Deque dq) {
		int r = n.loc[0];
		int c = n.loc[1];
		// Check if goal
		if (rm.map[r][c] == '.') {
			this.goal = n;
			rm.map[r][c] = 'G';
			return true;
		}
		else {
			// a Child
			if (r >= 2 && c <= rm.map[0].length-2) {
				// Not visited and either empty or goal
				if (rm.visited[r-2][c+1] == 0 && (rm.map[r-2][c+1] == ' ' ||
					rm.map[r-2][c+1] == '.')) {
					rm.visited[r-2][c+1] = 1; // Mark this node as visited
					n.aChild = new STreeNode(r-2,c+1,n); // Add to Tree
					this.size++; // Increase size of tree
					dq.addLast(n.aChild); // Add to frontier
					this.updateFrontierSize(dq);
					}
			}
			// b Child
			if (r >= 1 && c <= rm.map[0].length-3) {
				// Not visited and either empty or goal
				if (rm.visited[r-1][c+2] == 0 && (rm.map[r-1][c+2] == ' ' ||
					rm.map[r-1][c+2] == '.')) {
					rm.visited[r-1][c+2] = 1; // Mark this node as visited
					n.bChild = new STreeNode(r-1,c+2,n); // Add to Tree
					this.size++; // Increase size of tree
					dq.addLast(n.bChild); // Add to frontier
					this.updateFrontierSize(dq);
					}
			}
			// c Child
			if (r <= rm.map.length-2 && c <= rm.map[0].length-3) {
				// Not visited and either empty or goal
				if (rm.visited[r+1][c+2] == 0 && (rm.map[r+1][c+2] == ' ' ||
					rm.map[r+1][c+2] == '.')) {
					rm.visited[r+1][c+2] = 1; // Mark this node as visited
					n.cChild = new STreeNode(r+1,c+2,n); // Add to Tree
					this.size++; // Increase size of tree
					dq.addLast(n.cChild); // Add to frontier
					this.updateFrontierSize(dq);
					}
			}
			// d Child
			if (r <= rm.map.length-3 && c <= rm.map[0].length-2) {
				// Not visited and either empty or goal
				if (rm.visited[r+2][c+1] == 0 && (rm.map[r+2][c+1] == ' ' ||
					rm.map[r+2][c+1] == '.')) {
					rm.visited[r+2][c+1] = 1; // Mark this node as visited
					n.dChild = new STreeNode(r+2,c+1,n); // Add to Tree
					this.size++; // Increase size of tree
					dq.addLast(n.dChild); // Add to frontier
					this.updateFrontierSize(dq);
					}
			}
			// e Child
			if (r <= rm.map.length-3 && c >= 1) {
				// Not visited and either empty or goal
				if (rm.visited[r+2][c-1] == 0 && (rm.map[r+2][c-1] == ' ' ||
					rm.map[r+2][c-1] == '.')) {
					rm.visited[r+2][c-1] = 1; // Mark this node as visited
					n.eChild = new STreeNode(r+2,c-1,n); // Add to Tree
					this.size++; // Increase size of tree
					dq.addLast(n.eChild); // Add to frontier
					this.updateFrontierSize(dq);
					}
			}
			// f Child
			if (r <= rm.map.length-2 && c >= 2) {
				// Not visited and either empty or goal
				if (rm.visited[r+1][c-2] == 0 && (rm.map[r+1][c-2] == ' ' ||
					rm.map[r+1][c-2] == '.')) {
					rm.visited[r+1][c-2] = 1; // Mark this node as visited
					n.fChild = new STreeNode(r+1,c-2,n); // Add to Tree
					this.size++; // Increase size of tree
					dq.addLast(n.fChild); // Add to frontier
					this.updateFrontierSize(dq);
					}
			}
			// g Child
			if (r >= 1 && c >= 2) {
				// Not visited and either empty or goal
				if (rm.visited[r-1][c-2] == 0 && (rm.map[r-1][c-2] == ' ' ||
					rm.map[r-1][c-2] == '.')) {
					rm.visited[r-1][c-2] = 1; // Mark this node as visited
					n.gChild = new STreeNode(r-1,c-2,n); // Add to Tree
					this.size++; // Increase size of tree
					dq.addLast(n.gChild); // Add to frontier
					this.updateFrontierSize(dq);
					}
			}
			// h Child
			if (r >= 2 && c >= 1) {
				// Not visited and either empty or goal
				if (rm.visited[r-2][c-1] == 0 && (rm.map[r-2][c-1] == ' ' ||
					rm.map[r-2][c-1] == '.')) {
					rm.visited[r-2][c-1] = 1; // Mark this node as visited
					n.hChild = new STreeNode(r-2,c-1,n); // Add to Tree
					this.size++; // Increase size of tree
					dq.addLast(n.hChild); // Add to frontier
					this.updateFrontierSize(dq);
					}
			}
			// Remove next node from the frontier
			boolean result = bfs(rm,(STreeNode)dq.removeFirst(),dq);
			return result;
		}
	}
	
	// Greedy best-first search
	public boolean greedyBest(ReadMaze rm, STreeNode n, PriorityQueue pq) {
		int r = n.loc[0];
		int c = n.loc[1];
		// Check if goal
		if (rm.map[r][c] == '.') {
			this.goal = n;
			rm.map[r][c] = 'G';
			return true;
		}
		else {
			// a Child
			if (r >= 2 && c <= rm.map[0].length-2) {
				// Not visited and either empty or goal
				if (rm.visited[r-2][c+1] == 0 && (rm.map[r-2][c+1] == ' ' ||
					rm.map[r-2][c+1] == '.')) {
					rm.visited[r-2][c+1] = 1; // Mark this node as visited
					n.aChild = new STreeNode(r-2,c+1,n,rm.goal); // Add to Tree
					this.size++; // Increase size of tree
					pq.add(n.aChild); // Add to frontier
					this.updateFrontierSize(pq);
					}
			}
			// b Child
			if (r >= 1 && c <= rm.map[0].length-3) {
				// Not visited and either empty or goal
				if (rm.visited[r-1][c+2] == 0 && (rm.map[r-1][c+2] == ' ' ||
					rm.map[r-1][c+2] == '.')) {
					rm.visited[r-1][c+2] = 1; // Mark this node as visited
					n.bChild = new STreeNode(r-1,c+2,n,rm.goal); // Add to Tree
					this.size++; // Increase size of tree
					pq.add(n.bChild); // Add to frontier
					this.updateFrontierSize(pq);
					}
			}
			// c Child
			if (r <= rm.map.length-2 && c <= rm.map[0].length-3) {
				// Not visited and either empty or goal
				if (rm.visited[r+1][c+2] == 0 && (rm.map[r+1][c+2] == ' ' ||
					rm.map[r+1][c+2] == '.')) {
					rm.visited[r+1][c+2] = 1; // Mark this node as visited
					n.cChild = new STreeNode(r+1,c+2,n,rm.goal); // Add to Tree
					this.size++; // Increase size of tree
					pq.add(n.cChild); // Add to frontier
					this.updateFrontierSize(pq);
					}
			}
			// d Child
			if (r <= rm.map.length-3 && c <= rm.map[0].length-2) {
				// Not visited and either empty or goal
				if (rm.visited[r+2][c+1] == 0 && (rm.map[r+2][c+1] == ' ' ||
					rm.map[r+2][c+1] == '.')) {
					rm.visited[r+2][c+1] = 1; // Mark this node as visited
					n.dChild = new STreeNode(r+2,c+1,n,rm.goal); // Add to Tree
					this.size++; // Increase size of tree
					pq.add(n.dChild); // Add to frontier
					this.updateFrontierSize(pq);
					}
			}
			// e Child
			if (r <= rm.map.length-3 && c >= 1) {
				// Not visited and either empty or goal
				if (rm.visited[r+2][c-1] == 0 && (rm.map[r+2][c-1] == ' ' ||
					rm.map[r+2][c-1] == '.')) {
					rm.visited[r+2][c-1] = 1; // Mark this node as visited
					n.eChild = new STreeNode(r+2,c-1,n,rm.goal); // Add to Tree
					this.size++; // Increase size of tree
					pq.add(n.eChild); // Add to frontier
					this.updateFrontierSize(pq);
					}
			}
			// f Child
			if (r <= rm.map.length-2 && c >= 2) {
				// Not visited and either empty or goal
				if (rm.visited[r+1][c-2] == 0 && (rm.map[r+1][c-2] == ' ' ||
					rm.map[r+1][c-2] == '.')) {
					rm.visited[r+1][c-2] = 1; // Mark this node as visited
					n.fChild = new STreeNode(r+1,c-2,n,rm.goal); // Add to Tree
					this.size++; // Increase size of tree
					pq.add(n.fChild); // Add to frontier
					this.updateFrontierSize(pq);
					}
			}
			// g Child
			if (r >= 1 && c >= 2) {
				// Not visited and either empty or goal
				if (rm.visited[r-1][c-2] == 0 && (rm.map[r-1][c-2] == ' ' ||
					rm.map[r-1][c-2] == '.')) {
					rm.visited[r-1][c-2] = 1; // Mark this node as visited
					n.gChild = new STreeNode(r-1,c-2,n,rm.goal); // Add to Tree
					this.size++; // Increase size of tree
					pq.add(n.gChild); // Add to frontier
					this.updateFrontierSize(pq);
					}
			}
			// h Child
			if (r >= 2 && c >= 1) {
				// Not visited and either empty or goal
				if (rm.visited[r-2][c-1] == 0 && (rm.map[r-2][c-1] == ' ' ||
					rm.map[r-2][c-1] == '.')) {
					rm.visited[r-2][c-1] = 1; // Mark this node as visited
					n.hChild = new STreeNode(r-2,c-1,n,rm.goal); // Add to Tree
					this.size++; // Increase size of tree
					pq.add(n.hChild); // Add to frontier
					this.updateFrontierSize(pq);
					}
			}
			// Remove next node from the frontier
			boolean result = greedyBest(rm,(STreeNode)pq.remove(),pq);
			return result;
		}
	}
	
	// A* search
	public boolean Astar(ReadMaze rm, STreeNode n, PriorityQueue pq) {
		int r = n.loc[0];
		int c = n.loc[1];
		// Check if goal
		if (rm.map[r][c] == '.') {
			this.goal = n;
			rm.map[r][c] = 'G';
			return true;
		}
		else {
			// a Child
			if (r >= 2 && c <= rm.map[0].length-2) {
				// Not visited and either empty or goal
				if (rm.visited[r-2][c+1] == 0 && (rm.map[r-2][c+1] == ' ' ||
					rm.map[r-2][c+1] == '.')) {
					rm.visited[r-2][c+1] = 1; // Mark this node as visited
					n.aChild = new STreeNode(r-2,c+1,n,rm.goal); // Add to Tree
					this.size++; // Increase size of tree
					pq.add(n.aChild); // Add to frontier
					this.updateFrontierSize(pq);
					}
			}
			// b Child
			if (r >= 1 && c <= rm.map[0].length-3) {
				// Not visited and either empty or goal
				if (rm.visited[r-1][c+2] == 0 && (rm.map[r-1][c+2] == ' ' ||
					rm.map[r-1][c+2] == '.')) {
					rm.visited[r-1][c+2] = 1; // Mark this node as visited
					n.bChild = new STreeNode(r-1,c+2,n,rm.goal); // Add to Tree
					this.size++; // Increase size of tree
					pq.add(n.bChild); // Add to frontier
					this.updateFrontierSize(pq);
					}
			}
			// c Child
			if (r <= rm.map.length-2 && c <= rm.map[0].length-3) {
				// Not visited and either empty or goal
				if (rm.visited[r+1][c+2] == 0 && (rm.map[r+1][c+2] == ' ' ||
					rm.map[r+1][c+2] == '.')) {
					rm.visited[r+1][c+2] = 1; // Mark this node as visited
					n.cChild = new STreeNode(r+1,c+2,n,rm.goal); // Add to Tree
					this.size++; // Increase size of tree
					pq.add(n.cChild); // Add to frontier
					this.updateFrontierSize(pq);
					}
			}
			// d Child
			if (r <= rm.map.length-3 && c <= rm.map[0].length-2) {
				// Not visited and either empty or goal
				if (rm.visited[r+2][c+1] == 0 && (rm.map[r+2][c+1] == ' ' ||
					rm.map[r+2][c+1] == '.')) {
					rm.visited[r+2][c+1] = 1; // Mark this node as visited
					n.dChild = new STreeNode(r+2,c+1,n,rm.goal); // Add to Tree
					this.size++; // Increase size of tree
					pq.add(n.dChild); // Add to frontier
					this.updateFrontierSize(pq);
					}
			}
			// e Child
			if (r <= rm.map.length-3 && c >= 1) {
				// Not visited and either empty or goal
				if (rm.visited[r+2][c-1] == 0 && (rm.map[r+2][c-1] == ' ' ||
					rm.map[r+2][c-1] == '.')) {
					rm.visited[r+2][c-1] = 1; // Mark this node as visited
					n.eChild = new STreeNode(r+2,c-1,n,rm.goal); // Add to Tree
					this.size++; // Increase size of tree
					pq.add(n.eChild); // Add to frontier
					this.updateFrontierSize(pq);
					}
			}
			// f Child
			if (r <= rm.map.length-2 && c >= 2) {
				// Not visited and either empty or goal
				if (rm.visited[r+1][c-2] == 0 && (rm.map[r+1][c-2] == ' ' ||
					rm.map[r+1][c-2] == '.')) {
					rm.visited[r+1][c-2] = 1; // Mark this node as visited
					n.fChild = new STreeNode(r+1,c-2,n,rm.goal); // Add to Tree
					this.size++; // Increase size of tree
					pq.add(n.fChild); // Add to frontier
					this.updateFrontierSize(pq);
					}
			}
			// g Child
			if (r >= 1 && c >= 2) {
				// Not visited and either empty or goal
				if (rm.visited[r-1][c-2] == 0 && (rm.map[r-1][c-2] == ' ' ||
					rm.map[r-1][c-2] == '.')) {
					rm.visited[r-1][c-2] = 1; // Mark this node as visited
					n.gChild = new STreeNode(r-1,c-2,n,rm.goal); // Add to Tree
					this.size++; // Increase size of tree
					pq.add(n.gChild); // Add to frontier
					this.updateFrontierSize(pq);
					}
			}
			// h Child
			if (r >= 2 && c >= 1) {
				// Not visited and either empty or goal
				if (rm.visited[r-2][c-1] == 0 && (rm.map[r-2][c-1] == ' ' ||
					rm.map[r-2][c-1] == '.')) {
					rm.visited[r-2][c-1] = 1; // Mark this node as visited
					n.hChild = new STreeNode(r-2,c-1,n,rm.goal); // Add to Tree
					this.size++; // Increase size of tree
					pq.add(n.hChild); // Add to frontier
					this.updateFrontierSize(pq);
					}
			}
			// Remove next node from the frontier
			boolean result = Astar(rm,(STreeNode)pq.remove(),pq);
			return result;
		}
	}
}
