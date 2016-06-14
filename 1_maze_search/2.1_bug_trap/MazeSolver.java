import java.io.*;
import static java.lang.System.*;
import java.lang.*;
import java.util.Arrays;
import java.util.*;

public class MazeSolver {
	public static void main(String[] args) {
		String mazeToSolve = args[0];
		String mazeFile = mazeToSolve + ".txt";
		String solnFile = mazeToSolve+"Solution.txt";
		
		// Read the maze in text file
		ReadMaze rm = new ReadMaze();
		rm = rm.readMaze(mazeFile);
		// Print out initial maze map
		/*System.out.println("Initial Maze:\n");
		for (int i=0; i< rm.map.length; i++) {
				for (int j=0; j< rm.map[0].length; j++) {
					System.out.print(rm.map[i][j]);	
				}
				System.out.println();
		}*/
		
		// Create the tree with start as the root
		SearchTree st = new SearchTree(rm.start);
		
		// Create a priority queue frontier for greedy and A*
	
        // This is for Astar
		Comparator<STreeNode> comparator = new STreeNodeComparatorAstar();
		
		PriorityQueue<STreeNode> pq = new PriorityQueue<STreeNode>(100, comparator);
		
		
		// Do selected search on the maze and fill the new tree
		boolean searchResult = st.Astar1(rm,st.root,pq);
		//boolean searchResult = st.Astar2(rm,st.root,pq);
		
		if (searchResult) {st.solutionPath(rm,st.goal.parent);}
		// Outputs
		// Print out solution maze map
		System.out.println();
		System.out.println("Search Result: "+searchResult);
		System.out.println("Maze with Solution path:\n");
		// Write the solution in text file and print on terminal
		try {
			FileWriter fr = new FileWriter(solnFile);
			PrintWriter pw = new PrintWriter(fr);
			for (int i=0; i< rm.map.length; i++) {
					for (int j=0; j< rm.map[0].length; j++) {
						pw.print(rm.map[i][j]);
						System.out.print(rm.map[i][j]);	
					}
					pw.println();
					System.out.println();
			}
			pw.println("Path cost: "+st.goal.cost);
			pw.println("Number of Nodes Expanded: "+st.size);
			pw.println("Maximum tree Depth searched: "+st.maxDepth);
			pw.println("Maximum size of the frontier: "+st.frontierMaxSize);
			
			System.out.println("Path cost: "+st.goal.cost);
			System.out.println("Number of Nodes Expanded: "+st.size);
			System.out.println("Maximum tree Depth searched: "+st.maxDepth);
			System.out.println("Maximum size of the frontier: "+st.frontierMaxSize);
			
			pw.close();
		} catch(IOException e) {
			out.println("ERROR!");
		}
		System.out.println("Done");
	}
}
