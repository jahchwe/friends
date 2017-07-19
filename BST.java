import java.util.ArrayList;

public class BST<T extends Comparable<T>> {
	
	PersonNode root = null; 
	
	public class PersonNode {
		
		//data 
		T info = null; 
		
		//left and right nodes 
		PersonNode right = null;
		PersonNode left = null; 
		
		//bst of friends
		BST<Person> friends = new BST<Person>();
		
		public PersonNode(T info) {
			this.info = info;
		}
		
		public PersonNode() {		
		}
	}
	
	public BST() {
		root = null;
	}
	
	//tester for correct balanced binary tree creation
	public void inOrderPrint(PersonNode root) {
		
		if (root == null) {
			return;
		}
	
		inOrderPrint(root.left);
		System.out.println(root.info.toString());
		inOrderPrint(root.right);
		
	}
	
	//recursive method to get all content of a tree
	public ArrayList<T> getContent(PersonNode root) {
		
		ArrayList<T> ans = new ArrayList<T>();
		getContentHelp(ans, root);
		return ans; 
		
	}
	
	//helper method. Actual recursive part. uses inorder tree traversal
	public void getContentHelp(ArrayList<T> input, PersonNode root) {
		if (root == null) {
			return;
		}
		getContentHelp(input, root.left);
		if (root != null) {
			input.add(root.info);
		}
		getContentHelp(input, root.right);
	}
	
	//use to find person
	public T find(T input) {
		
		PersonNode current = root; 
		
		while (true) {
			
			if (current == null) {
				return null;
			}
			
			if (current.info.compareTo(input) == 0) {
				return current.info;
			}
			
			else if (current.info.compareTo(input) > 0) {
				current = current.left;
			}
			
			else if (current.info.compareTo(input) < 0) {
				current = current.right;
			}	
		}
	}
	
	public void insert(T newNode) {
		
		PersonNode input = new PersonNode(newNode);
		
		//if tree is empty
		if (root == null) {
			root = input;
			return;
		}
		else {
		
		PersonNode current = root;
		PersonNode parent = root;
	
			while (true) {
				
				parent = current; 
						
				//if attempting to insert a duplicate, just return, do not insert
				if (current.info.compareTo(input.info) == 0) {
					return;
				}
				//if input val is less than key, go left
				else if (current.info.compareTo(input.info) > 0) {
					current = current.left;
					//if current has reached a null value, insert new input as a leaf
					if (current == null) {
						parent.left = input;
						return;
					}
				}
				//if input val is greater than key, go right
				else if (current.info.compareTo(input.info) < 0) {
					current = current.right;
					//insert as right if current has reached the end of tree
					if (current == null) {
						parent.right = input;
						return;
					}
				}		
			}
		}
	}
	
	
	
}
