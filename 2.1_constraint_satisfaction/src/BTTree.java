import java.util.*;

public class BTTree {
	public BTTreeNode root = new BTTreeNode();
	public BTTreeNode solution = new BTTreeNode();
	private Constraints c;
	public int noOfAssignments = 0;
	
	public BTTree(Constraints c) {
		//System.out.println("t = "+t+" , m = "+m);
		this.root.timeSlots = new ArrayList<List<Meeting>>();
		//create timeSlots of a specific size
		for (int i = 0; i <= c.numOfTimeSlots; i++) {
			this.root.timeSlots.add(new ArrayList<Meeting>());
		}
		this.root.meetings = c.meetings;
		
		this.c = c;
	}
	
	public boolean checkAssignment(Meeting m, int loc, List<List<Meeting>> ts) {
		//System.out.println("Meeting: "+ m.name);
		
		//System.out.println("Conflicting meetings: " + setOfMeetings);
		
		//System.out.println("Meetings: " +ts.get(loc) + " at timeslot: " +loc);
		for (int i = (loc-2); i <= loc+2; i++) {
			if (i <= 0 || i >= ts.size()) {
				continue;
			}
			for (Meeting mt : ts.get(i)) {
				//System.out.println("Given meeting: "+m);
				//System.out.println(setOfMeetings);
				//System.out.println("Current checked meeting: "+mt);
				//System.out.println("Contains: " + setOfMeetings.contains(mt));
				//System.out.println("Travel time: "+ c.traveltimes[m.name][mt.name] +" > Distance: "+Math.abs(loc-i));
				//System.out.println("Traveltime clause: " + (c.traveltimes[m.name][mt.name] > Math.abs(loc-i)));
				if (m.conflictingMeetings.contains(mt) && (c.traveltimes[m.name][mt.name] >= Math.abs(loc-i))) {
					//System.out.println("Cannot be in this");
					return false;
				}
			}
		}
		return true;
	}

	public boolean backTracking(BTTreeNode n) {
		if (noOfAssignments > 0 && noOfAssignments % 1000000 == 0) {
			System.out.println("Nodes expanded: " + noOfAssignments);
		}
		//System.out.println("Meetings to assign: " + n.meetings);
		if (n.meetings.isEmpty()) {
			this.solution = n;
			return true;
		}
		Meeting m = n.meetings.poll();
		//System.out.println("Trying meeting : " + m.name);
		//System.out.println(m);
		
		// In order to do random variable selection, don't use comparator but shuffle the queue.
		Comparator<TimeSlot> comparator = new TimeSlotComparator();
		PriorityQueue<TimeSlot> slots = new PriorityQueue<TimeSlot>(n.timeSlots.size(), comparator);
		
		for (int i = 1; i < n.timeSlots.size(); i++) {
			slots.add(new TimeSlot(i, new ArrayList<Meeting>(n.timeSlots.get(i))));
		}
		
		while (!slots.isEmpty()) {
			int i = slots.poll().name;
			if (checkAssignment(m, i, n.timeSlots)) {
				BTTreeNode child = new BTTreeNode(n.meetings,n);
				List<Meeting> temp = child.timeSlots.get(i);
				temp.add(m);
				this.noOfAssignments++;
				if (backTracking(child)) {
					return true;
				}
			}
		}
		//System.out.println("Meetings to assign: " + n.meetings);
		return false;
	}
	
	public void printSolution() {
		System.out.println("Meetings Arranged");
		System.out.println("Number of attempted variable assignments: "+this.noOfAssignments);
		int[] meetings = new int[c.numOfMeetings+1];
		for (int i=1; i < this.solution.timeSlots.size(); i++) {
			if (this.solution.timeSlots.get(i) != null) {
				for (Meeting m : this.solution.timeSlots.get(i)) {
					meetings[m.name] = i;
				}
			}
		}
		for (int k = 1; k < meetings.length; k++) {
			System.out.println("Meeting " + k + " is scheduled at timeslot: " + meetings[k]);
		}
	}
}
