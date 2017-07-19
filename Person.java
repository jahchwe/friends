
public class Person implements Comparable<Person> {
	
	int ssn; 
	String fname; 
	String lname; 
	
	int fssn;
	int mssn; 
	
	String friendSSN;
	
	BST<Person> friends = new BST<Person>(); 
	
	public Person(int ssn, String fname, String lname, int fssn, int mssn, String friendSSN) {
	
		this.ssn = ssn; 
		this.fname = fname; 
		this.lname = lname; 
		this.friendSSN = friendSSN; 
		
		this.fssn = fssn; 
		this.mssn = mssn; 
		
	}
	
	public Person(int i) {
		
		ssn = i; 
	}
	
	public Person() {
		
	}
	
	public String toString() {
		
		String a = fname + " " + lname + ": " + ssn;
		return a; 
	}
	
	public int getSSN() {
		return this.ssn; 
	}
	
	public String getFname() {
		return this.fname; 
	}
	
	public String getLname() {
		return this.lname;
	}
	
	public int getFather() {
		return fssn;
	}
	
	public int getMother() {
		return mssn; 
	}
	
	//compareTo compares SSN for tree construction and query handling
	public int compareTo(Person input) {
		
		if (this.ssn > input.ssn) {
			return 1;
		}
		
		else if (this.ssn == input.ssn) {
			return 0;
		}
		
		else {
			return -1;
		}
		
	}
	
	

}
