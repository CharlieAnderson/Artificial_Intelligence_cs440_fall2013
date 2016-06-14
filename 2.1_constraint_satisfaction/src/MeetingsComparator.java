import java.util.Comparator;

public class MeetingsComparator implements Comparator<Meeting> {
    public int compare(Meeting a, Meeting b) {
    	// This block is used for selecting the meetings in sequential order. This would let us know if a problem is unsolvable.
//    	if (a.name < b.name) {
//	        return -1;
//	    }
//	    if (a.name > b.name) {
//	        return 1;
//	    }
    	// This block is used for selecting the meeting with most conflicting meetings. Most constraining value
//    	if (a.conflictingMeetings.size() > b.conflictingMeetings.size()) {
//	        return -1;
//	    }
//	    if (a.conflictingMeetings.size() < b.conflictingMeetings.size()) {
//	        return 1;
//	    }
	    // This block is used for selecting the meeting with most employees attending it. Most constraining value
//    	if (a.employees.size() > b.employees.size()) {
//	        return -1;
//	    }
//	    if (a.employees.size() < b.employees.size()) {
//	        return 1;
//	    }
    	// This block is used for selecting the meeting with least conflicting meetings. Least constraining value
//    	if (a.conflictingMeetings.size() < b.conflictingMeetings.size()) {
//	        return -1;
//	    }
//	    if (a.conflictingMeetings.size() > b.conflictingMeetings.size()) {
//	        return 1;
//	    }
	    // This block is used for selecting the meeting with least employees attending it. Least constraining value
    	if (a.employees.size() < b.employees.size()) {
	        return -1;
	    }
	    if (a.employees.size() > b.employees.size()) {
	        return 1;
	    }
	    return 0;
    }
}