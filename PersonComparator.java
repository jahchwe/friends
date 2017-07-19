import java.util.Comparator;

public class PersonComparator implements Comparator<Person> {
	
	//custom personComparator used to sort persons for balanced tree insertion
	
	public int compare(Person a, Person b) {
		
		if (a.ssn > b.ssn) {
			return 1;
		}
		
		else if (a.ssn == b.ssn) {
			return 0;
		}
		
		else {
			return -1;
		}
		
	}
	
	

}
