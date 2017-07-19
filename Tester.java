import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Tester {

	public static void main(String[] args) throws FileNotFoundException {
		
		/* Hello,
		 *
		 * I implemented this HW using a double BST tree structure, one for all people and one for each person's friends. 
		 * 
		 * A recursive algorithm is used to create a balanced tree is both cases. 
		 * A custom comparator is used to sort the person obejcts before insertion into the tree.
		 * 
		 * Operation.java contains most of the meat of the assignment. 
		 * 
		 * Much as I did on the last HW, I made a Query object for each query read from the file. 
		 * 
		 * All query info defaults to -1 or null. So if some input is not available, the output will be unavailable
		 * 
		 * Thanks!
		 */
		
		File queries = new File(args[1]);
		File inputPeople = new File(args[0]);
		
		Operation test = new Operation();
		
		test.getQueries(queries);
		test.getPeople(inputPeople);
		
		test.createTree(test.people, test.peopleTree);
		
		//test.peopleTree.inOrderPrint(test.peopleTree.root);
		
		//create individual friends BST for each person
		for (Person a: test.people) {
			test.getFriends(a);
		}
				
		// TODO Auto-generated method stub
		
		//handle inputted queries
		test.handleQueries();

	}
	
}
