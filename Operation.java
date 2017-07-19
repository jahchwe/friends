import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Operation {
	
	ArrayList<Query> queries;
	
	ArrayList<Person> people;
	
	BST<Person> peopleTree;
	
	public Operation() {
		queries = new ArrayList<Query>();
		people = new ArrayList<Person>();
		peopleTree = new BST<Person>();
	}
	
	public void getChildren(int ssn) {
		
		ArrayList<Person> ans = new ArrayList<Person>();
		
		for (Person a: people) {
			if (a.fssn == ssn || a.mssn == ssn) {
				ans.add(a);
			}
		}
		
		if (ans.isEmpty()) {
			System.out.print("Unavailable");
		}
		
		else {
			for (Person b: ans) {
				System.out.print(b.fname + " " + b.lname + " ");
			}
		}
		
		System.out.println();
		
	}
	
	public void getName(int ssn) {
		
		//create empty person with input ssn
		Person search = new Person(ssn);
		//search for data
		Person ans = peopleTree.find(search);
		//return data
		System.out.println(ans.fname + " " + ans.lname); 
		
	}
	
	public void getMom(int ssn) {
		
		Person search = new Person(ssn);
		Person ans = peopleTree.find(search);
		//find mom
		
		Person search1 = new Person(ans.mssn);
		Person mom = peopleTree.find(search1);
		
		System.out.println(mom.fname + " " + mom.lname);
	}
	
	public void getDad(int ssn) {
		
		Person search = new Person(ssn);
		Person ans = peopleTree.find(search);
		//find dad
		
		Person search1 = new Person(ans.fssn);
		Person dad = peopleTree.find(search1);
		
		System.out.println(dad.fname + " " + dad.lname);
	}
	
	public void getHalfSib(int ssn) {
		
		ArrayList<Person> sibs = new ArrayList<Person>();
		
		Person search = new Person(ssn);
		Person ans = peopleTree.find(search);
		
		int fssn = ans.fssn; 
		int mssn = ans.mssn; 
		
		for (Person a: people) {
			if ( (a.fssn == fssn && a.mssn != mssn) || (a.fssn != fssn && a.mssn == mssn) ) {
				sibs.add(a);
			}
		}
		
		if (sibs.isEmpty()) {
			System.out.print("Unavailable");
		}
		
		else {
			for (Person b: sibs) {
				System.out.print(b.fname + " " + b.lname + " ");
			}
		}
		
		System.out.println();
	}
	
	public void getFullSib(int ssn) {
		
		ArrayList<Person> sibs = new ArrayList<Person>();
		
		Person search = new Person(ssn);
		Person ans = peopleTree.find(search);
		
		int fssn = ans.fssn; 
		int mssn = ans.mssn; 
		
		for (Person a: people) {
			
			//skip the inputted ssn
			if (a.ssn == ssn) {
				continue;
			}
			
			//if same parents, add
			if ( (a.fssn == fssn && a.mssn == mssn) ) {
				sibs.add(a);
			}
		}
	
		if (sibs.isEmpty()) {
			System.out.print("Unavailable");
		}
		
		else {	
			for (Person b: sibs) {
				System.out.print(b.fname + " " + b.lname + " ");
			}
		}
		System.out.println();
	}
	
	//all persons who consider ssn to be their friend
	public void inverseFriend(int ssn) {
		
		Person search = new Person(ssn);
		
		ArrayList<Person> ans = new ArrayList<Person>();
		
		//for all people, if ssn is in friend tree, add a; 
		for (Person a: people) {
			Person result = a.friends.find(search);
			if (result != null) {
				ans.add(a);
			}	
		}
		
		if (ans.isEmpty()) {
			System.out.print("Unavailable");
		}
		
		else {	
			for (Person b: ans) {
				System.out.print(b.fname + " " + b.lname + " ");
			}
		}
		System.out.println();
	}
	
	public ArrayList<Person> mutualFriends(int ssn) {
		
		Person search = new Person(ssn);
		
		Person found = peopleTree.find(search);
		
		ArrayList<Person> ans = new ArrayList<Person>();
		
		ArrayList<Person> friends = found.friends.getContent(found.friends.root);
		
		//for all friends, search individual tree for ssn. If found, add a; 
		for (Person a: friends) {	
			Person result = a.friends.find(found);
			if (result != null) {
				ans.add(a);
			}
		}
		
		if (ans.isEmpty()) {
			return null;
		}
		
		return ans;
		
	}
	
	public void mostMutuals() {
		
		int sizeMax = 0;
		Person ans = null; 
		
		for (Person a: people) {
			
			ArrayList<Person> friends = mutualFriends(a.ssn);
			if (friends.size() > sizeMax) {
				sizeMax = friends.size();
				ans = a;
			}
		}
		
		System.out.print(ans.fname + " " + ans.lname);
		
	}
	
	
	
	//from string of friends, create BST
	public void getFriends(Person input) {
		
		String[] friendSSN = input.friendSSN.split(",");
		
		//ssn retrieval of friends working
		
		ArrayList<Person> friends = new ArrayList<Person>();
		
		for (int i = 0; i < friendSSN.length; i++) {	
			Person search = new Person(Integer.parseInt(friendSSN[i]));
			Person found = peopleTree.find(search);
			friends.add(found);
		}
		
		createTree(friends, input.friends);
		
	}
	
	public void createTree(ArrayList<Person> input, BST<Person> tree) {
		
		//use custom comparator to sort
		input.sort(new PersonComparator());
		
		balanceInsert(input, tree, 0, input.size());
			
	}
	
	//recursive insertion algo. uses same logic of binary search
	public void balanceInsert(ArrayList<Person> input, BST<Person> tree, int low, int high) {
		
		if (low == high) {
			return;
		}
		
		int mid = ((high-low) / 2) + low; 
		
		Person insert = input.get(mid);
		
		tree.insert(insert);
		
		balanceInsert(input, tree, low, mid);
		balanceInsert(input, tree, mid+1, high);
		
	}
	
	public void handleQueries() {
		
		for (Query q: queries) {
			
			switch (q.queryCode) {
			
			//NameOf
			case 1: 
				System.out.print(q.name+ " " + q.SSN + ": ");
				getName(q.SSN);
				break;
			
			case 2: 
				System.out.print(q.name+ " " + q.SSN + ": ");
				getDad(q.SSN);
				break;
				
			case 3: 
				System.out.print(q.name +" " + q.SSN + ": ");
				getMom(q.SSN);
				break;
			case 4: 
				System.out.print(q.name+ " " + q.SSN + ": ");
				getHalfSib(q.SSN);
				break;
				
			case 5: 
				System.out.print(q.name+ " " + q.SSN + ": ");
				getFullSib(q.SSN);
				break;
				
			case 6: 
				System.out.print(q.name+ " " + q.SSN + ": ");
				getChildren(q.SSN);
				break;
				
			case 7: 
				System.out.print(q.name +  " " +q.SSN + ": ");
				inverseFriend(q.SSN);
				break;
				
			case 8: 
				System.out.print(q.name+ " " + q.SSN + ": ");
				ArrayList<Person> ans = mutualFriends(q.SSN);
				
				if (ans == null) {
					System.out.print("Unavailable");
				}
				else {	
					for (Person a: ans) {
						System.out.print(a.fname + " " + a.lname + " ");
					}
				}
				System.out.println();
				break;
				
			case 9: 
				System.out.print(q.name+ ": ");
				mostMutuals();
				break;
			}
		}
	}
	
	public void getQueries(File input) throws FileNotFoundException {
		
		//scan info and tokenize
		
		ArrayList<String> queryContent = new ArrayList<String>();
		
		Scanner querscan = new Scanner(input);
		
		//tokenize by space
		while (querscan.hasNextLine()) {
			
			String line = querscan.nextLine(); 
			
			String[] split = line.split(" ");
			
			for (int i = 0; i < split.length; i++) {
				
				queryContent.add(split[i]);
	
			}
		}
		
		querscan.close();
		
		//create query objects from tokens
		for (int i = 0; i < queryContent.size(); i ++) {
			
			Query a = new Query();
			
			if (queryContent.get(i).equals("NAME-OF")) {
				a.name = "NAME-OF";
				a.SSN = Integer.parseInt(queryContent.get(i + 1));
				i++;
				a.queryCode = 1;
			}
			
			else if (queryContent.get(i).equals("FATHER-OF")) {
				a.name = "FATHER-OF";
				a.SSN = Integer.parseInt(queryContent.get(i + 1));
				i++;
				a.queryCode = 2; 
			}
			
			else if (queryContent.get(i).equals("MOTHER-OF")) {
				a.name = "MOTHER-OF";
				a.SSN = Integer.parseInt(queryContent.get(i + 1));
				i++;
				a.queryCode = 3; 
			}
			
			else if (queryContent.get(i).equals("HALF-SIBLINGS-OF")) {
				a.name = "HALF-SIBLINGS-OF";
				a.SSN = Integer.parseInt(queryContent.get(i + 1));
				i++;
				a.queryCode = 4; 
			}
			
			else if (queryContent.get(i).equals("FULL-SIBLINGS-OF")) {
				a.name = "FULL-SIBLINGS-OF";
				a.queryCode = 5; 
				a.SSN = Integer.parseInt(queryContent.get(i + 1));
				i++;
			}
			
			else if (queryContent.get(i).equals("CHILDREN-OF")) {
				a.name = "CHILDREN-OF";
				a.queryCode = 6;
				a.SSN = Integer.parseInt(queryContent.get(i + 1));
				i++;
			}
			
			else if (queryContent.get(i).equals("INVERSE-FRIENDS-OF")) {
				a.name = "INVERSE-FRIENDS-OF";
				a.queryCode = 7;
				a.SSN = Integer.parseInt(queryContent.get(i + 1));
				i++;
			}
			
			else if (queryContent.get(i).equals("MUTUAL-FRIENDS-OF")) {
				a.name = "MUTUAL-FRIENDS-OF";
				a.queryCode = 8;
				a.SSN = Integer.parseInt(queryContent.get(i + 1));
				i++;
			}
			
			else if (queryContent.get(i).equals("WHO-HAS-MOST-MUTUAL-FRIENDS")) {
				a.name = "WHO-HAS-MOST-MUTUAL-FRIENDS";
				a.queryCode = 9;
			}
			
			queries.add(a);	
		}
	}
	
	public void getPeople(File input) throws FileNotFoundException {
		
		Scanner peopleScan = new Scanner(input);
		
		ArrayList<String> fileContent = new ArrayList<String>();
		
		//scrape data from file using ": " as delimiter
		while (peopleScan.hasNextLine()) {
			
			String line = peopleScan.nextLine();
			
			if (line.isEmpty()) {
				continue; 
			}
			
			String[] split =  line.split(": ");
			
			fileContent.add(split[1]);
		}
		
		peopleScan.close();
		
		//create new People objects from collected data
		
		for (int i = 0; i < fileContent.size(); i+= 6) {
			
			Person a = new Person();
			
			a.fname = fileContent.get(i);
			a.lname = fileContent.get(i+1);
			a.ssn = Integer.parseInt(fileContent.get(i+2));
			a.fssn = Integer.parseInt(fileContent.get(i+3));
			a.mssn = Integer.parseInt(fileContent.get(i+4));
			a.friendSSN = fileContent.get(i+5);
			
			people.add(a);
			
		}
		
		
		
	}
	
	
}
