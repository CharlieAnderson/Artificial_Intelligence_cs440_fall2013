import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Constraints {
	
	public int numOfMeetings;
	public int numOfEmployees;
	public int numOfTimeSlots;
	public Employee[] employees;
	public PriorityQueue<Meeting> meetings;
	public Meeting[] listOfMeetings;
	public int[][] traveltimes;
	
	public Constraints(String filePath) {
		try {
			File aFile = new File(filePath);
			BufferedReader input = new BufferedReader(new FileReader(aFile));
			Comparator<Meeting> comparator = new MeetingsComparator();
			meetings = new PriorityQueue<Meeting>(30,comparator);
			
			try {
				Pattern pMeetings = Pattern.compile("Number of meetings: (\\d*)");
				Pattern pEmployees = Pattern.compile("Number of employees: (\\d*)");
				Pattern pTimeSlots = Pattern.compile("Number of time slots: (\\d*)");
				Pattern pCheckMeetings = Pattern.compile("(\\d+): (.*)");
				Pattern pNumberMeetings = Pattern.compile("\\d+");
				Matcher matcher;
				String line = null;
				int y = 0;
				boolean checkingMeetings = false;
				boolean checkingTravel = false;
				while((line = input.readLine()) != null) {
					if (y == 0) {
						matcher = pMeetings.matcher(line);
						if (matcher.find()) {
							numOfMeetings = Integer.parseInt(matcher.group(1));
							traveltimes = new int[numOfMeetings+1][numOfMeetings+1];
							listOfMeetings = new Meeting[numOfMeetings+1];
							for (int i = 1; i <= numOfMeetings; i++) {
								Meeting tmpMeet = new Meeting(i);
								meetings.add(tmpMeet);
								listOfMeetings[i] = tmpMeet;
							}
						}
					}
					else if (y == 1) {
						matcher = pEmployees.matcher(line);
						if (matcher.find()) {
							numOfEmployees = Integer.parseInt(matcher.group(1));
							employees = new Employee[numOfEmployees+1];
						}
					}
					else if (y == 2) {
						matcher = pTimeSlots.matcher(line);
						if (matcher.find()) {
							numOfTimeSlots = Integer.parseInt(matcher.group(1));
						}
					}
					
					if (line.contains("Meetings each employee must attend")) {
						checkingMeetings = true;
					}
					if (line.contains("Travel time between meetings")) {
						checkingMeetings = false;
						checkingTravel = true;
					}
					
					if (checkingMeetings) {
						matcher = pCheckMeetings.matcher(line);
						if (matcher.find()) {
							int employeeName = Integer.parseInt(matcher.group(1));
							Employee emp = new Employee(employeeName);
							employees[employeeName] = emp;
							Matcher m = pNumberMeetings.matcher(matcher.group(2));
							List<Meeting> tempMeetings = new ArrayList<Meeting>();
							while (m.find()) {
								int meetingName = Integer.parseInt(m.group());
								Meeting tmpMeeting = listOfMeetings[meetingName];
								tempMeetings.add(tmpMeeting);
								tmpMeeting.addEmployee(emp);
							}
							emp.setMeetings(tempMeetings);
						}
					}
					
					if (checkingTravel) {
						matcher = pCheckMeetings.matcher(line);
						if (matcher.find()) {
							int meeting = Integer.parseInt(matcher.group(1));
							Matcher m = pNumberMeetings.matcher(matcher.group(2));
							int k = 1;
							while (m.find()) {
								traveltimes[meeting][k] = Integer.parseInt(m.group());
								k++;
							}
						}
					}
					
					if (y < 3) {
						y++;
					}
				}
				for (Meeting meet : this.meetings) {
					meet.createSetOfConflictingMeetings();
				}
			}
			finally {
				input.close();
			}
		}
		catch (Exception ex){
			ex.printStackTrace();
	    }
	}
}
