import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class TimeSlot {
	
	public int name;
	public List<Meeting> meetingsAssigned;
	public Set<Meeting> conflictingMeetings;
	
	public TimeSlot(int n, List<Meeting> meetings) {
		this.name = n;
		this.meetingsAssigned = meetings;
		this.conflictingMeetings = new HashSet<Meeting>();
		for (Meeting m : this.meetingsAssigned) {
			this.conflictingMeetings.addAll(m.conflictingMeetings);
		}
	}

}
