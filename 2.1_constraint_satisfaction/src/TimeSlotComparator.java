import java.util.Comparator;

public class TimeSlotComparator implements Comparator<TimeSlot> {
	public int compare(TimeSlot a, TimeSlot b) {
		// This block does variable selection in sequential order
//    	if (a.name < b.name) {
//	        return -1;
//	    }
//	    if (a.name > b.name) {
//	        return 1;
//	    }
	    // This block does variable selection in order of most conflicting time slot. Most constraining variable
	    if (a.conflictingMeetings.size() > b.conflictingMeetings.size()) {
	        return -1;
	    }
	    if (a.conflictingMeetings.size() < b.conflictingMeetings.size()) {
	        return 1;
	    }
	    return 0;
    }
}
