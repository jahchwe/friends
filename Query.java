
public class Query {
	
	String name = null; 
	
	int queryCode = -1; 
	int SSN = -1; 
	
	public void displayInfo() {
		
		System.out.printf("%-15s%-15s%-15s", name+": ", SSN, queryCode);
		System.out.println();
	}

}
