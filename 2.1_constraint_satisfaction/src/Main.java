public class Main {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Constraints problem = new Constraints("problem1.txt");
		//System.out.println(problem.employees[10].meetings.toString());

		BTTree btt = new BTTree(problem);

		boolean result = btt.backTracking(btt.root);

		if (result) {
			btt.printSolution();
		}
		System.out.println("DONE");
	}
}