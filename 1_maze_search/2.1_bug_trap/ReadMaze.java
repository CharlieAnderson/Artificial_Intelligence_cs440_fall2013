import java.io.*;
import static java.lang.System.*;
import java.lang.*;
import java.util.Arrays;

public class ReadMaze {
	public char[][] map = new char[1][1];
	public int[] goal = new int[2];
	public int[] start = new int[2];
	public int[][] visited = new int[1][1];
	
	public ReadMaze() {}
	
	public ReadMaze(ReadMaze rm) {
		map = rm.map;
		goal = rm.goal;
		start = rm.start;
		visited = rm.visited;
	}
	
	public ReadMaze readMaze(String f) {
		try {
			FileReader fr = new FileReader(new File(f));
			BufferedReader br = new BufferedReader(fr);
			
			String str;
			char[][] c = new char[200][200];
			int i = 0;

			while ((str = br.readLine()) != null) {
				c[i] = str.toCharArray();
				i++;
			}
			
			int rows = i;
			int cols = c[0].length;
			br.close();
			
			map = new char[rows][cols];
			visited = new int[rows][cols];
			
			for (i=0; i< rows; i++) {
				for (int j=0; j< cols; j++) {
					map[i][j] = c[i][j];
					visited[i][j] = 0;
					if (c[i][j] == '.') {
						goal[0] = i;
						goal[1] = j;
					}
					if (c[i][j] == 'P') {
						start[0] = i;
						start[1] = j;
						visited[i][j] = 1;
					}
				}
			}
			c = new char[1][1];
			System.out.println("Start is at: " + Arrays.toString(start));
			System.out.println("Goal is at: " + Arrays.toString(goal));
		} catch(IOException e) {
			out.println("File not found");
		}
		return this;
	}
}
