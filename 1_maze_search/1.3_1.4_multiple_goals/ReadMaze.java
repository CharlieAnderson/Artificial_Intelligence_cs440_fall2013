import java.io.*;
import static java.lang.System.*;
import java.lang.*;
import java.util.*;

public class ReadMaze {
	public char[][] map = new char[1][1];
	public int[] start = new int[2];
	public List<Dots> dl = new ArrayList<Dots>();
	
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
			
			for (i=0; i< rows; i++) {
				for (int j=0; j< cols; j++) {
					map[i][j] = c[i][j];
					if (c[i][j] == '.') {
						Dots d = new Dots();
						d.r = i;
						d.c = j;
						dl.add(d);
						
					}
					if (c[i][j] == 'P') {
						start[0] = i;
						start[1] = j;
					}
				}
			}
			c = new char[1][1];
			System.out.println("Start is at: " + Arrays.toString(start));
		} catch(IOException e) {
			out.println("File not found");
		}
		return this;
	}
}
