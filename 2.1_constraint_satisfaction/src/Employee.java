import java.util.List;


public class Employee {

	public List<Meeting> meetings;
	public int name;
	
	public Employee(int name) {
		this.name = name;
	}
	
	public void setMeetings(List<Meeting> meetings) {
		this.meetings = meetings;
	}
	
	@Override
	public String toString() {
		return "" + this.name;
	}
}
