import java.util.*; 

public class BTTreeNode {
	public List<List<Meeting>> timeSlots;
	public PriorityQueue<Meeting> meetings;
	public BTTreeNode parent;
	
	public BTTreeNode() {
		this.timeSlots = new ArrayList<List<Meeting>>();
		this.meetings = new PriorityQueue<Meeting>();
	}
	
	public BTTreeNode(PriorityQueue<Meeting> m, BTTreeNode p) {
		this.timeSlots = new ArrayList<List<Meeting>>();
		for (List<Meeting> lm : p.timeSlots) {
			this.timeSlots.add(new ArrayList<Meeting>(lm));
		}
		this.meetings = new PriorityQueue<Meeting>(m);
		this.parent = p;
	}
}