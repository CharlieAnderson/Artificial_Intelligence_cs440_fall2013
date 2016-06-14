import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Meeting {
	
	public int name;
	public List<Employee> employees;
	public Set<Meeting> conflictingMeetings;
	
	public Meeting(int name) {
		this.name = name;
		this.employees = new ArrayList<Employee>();
		this.conflictingMeetings = new HashSet<Meeting>();
	}
	
	public boolean addEmployee(Employee emp) {
		for (Employee e : employees) {
			if (e.name == emp.name) {
				return false;
			}
		}
		
		employees.add(emp);
		return true;
	}
	
	public void createSetOfConflictingMeetings() {
		for (Employee emp : this.employees) {
			this.conflictingMeetings.addAll(emp.meetings);
		}
		//System.out.println(conflictingMeetings);
	}
	
	@Override
	public String toString() {
		return "" + this.name;
	}

}
