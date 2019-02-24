
public abstract class CustomList<T>
{
	// Superclass for all of the list classes used in WordCounter
	
	protected Node<T> node = new Node<T>(); // Reference to base node
	
	protected Node<T> right = node.getLink();
	
	long comparisonCount; // Incremented each time a comparison between words is made
	long refChangeCount; // Incremented each time a reference variable is created, changed, or altered.
	
	long startTime = System.currentTimeMillis(); // Used for getting total elapsed time for the class
	
	
	abstract void insert(T cargo); 
	
	void subtract(T cargo)
	{
		/* Finds a node based on the cargo fed into the method.
		 * 
		 * The found node will then be decremented. Should it be decremented
		 * to 0 or less, it will remove the node instead.
		 */
		
		Node<T> checkNode = search(cargo);
		
		if (checkNode != null) checkNode.decrement();

		if (checkNode.getCount() <= 0)
		{		
			remove(cargo);
		}
		
	}
	
	void subtract2(T cargo)
	{
		/* Alternate take on the subtract method where
		 * a subtracted node will be bumped to the top of its respective list.
		 * 
		 * (Sans the skipList)
		 */
		
		Node<T> checkNode = search(cargo);
		
		if (checkNode != null) checkNode.decrement();
		
		remove(cargo);
		
		if (checkNode.getCount() != 0)
		{
			checkNode.setLink(node);
			node = checkNode;
		}
	
	}	
		
	void remove(T cargo)
	{	
		/* A search and destroy remove method.
		 * 
		 * Finds a node whose link contains the cargo in question and bypasses it in the list.
		 */
		Node<T> checkNode = node;
		
		boolean killLoop = false;
		
		if (checkNode.getCargo().equals(cargo)) // When the base node holds the cargo
		{
			if (checkNode.getLink() != null)
			{
			node = checkNode.getLink(); // Just move the base node reference over one
			return;
			}
			else // if this is the only node, just wipe it instead.
			{
				node.setCargo(null);
				node.setCount(0);
				return;
			}
		}
				
		while(!killLoop)
		{
			if (checkNode.getLink().getCargo().equals(cargo)) // If the next node is the cargo
			{
				if (checkNode.getLink().getLink() == null) // If the node after the next node is null, just rip the link out of checkNode
				{
					checkNode.setLink(null);
					return;
				}
				
				checkNode.setLink(checkNode.getLink().getLink()); // elsewise, just bypass the node, leaving it unreferenced
				return;
			}
			
			checkNode = checkNode.getLink();
			
			if (checkNode.getLink().equals(null)) killLoop = true; // Indicates that a phantom node is trying to be removed
		}		
	}
			
	public Node<T> search(T cargo)
	{
		/* Searches for a string in the list containing string cargo.
		 * 
		 * Once the method finds that string, it returns the node
		 * in the list containing it.
		 * 
		 * Should no node exist for that string, it will instead
		 * return null.
		 */
			
		Node<T> checkNode = node;			
		
		while (true) 
		{			
				
			if (checkNode.getCargo() == null)
			{
				System.out.println("Returning null @" + cargo);
				return null;
			}
			
			if (checkNode.getCargo().equals(cargo))	return checkNode;
			checkNode = checkNode.getLink();
			
			refChangeCount = refChangeCount + 1;
		}
				
	}
	
	boolean isEqualList(CustomList<T> list)
	{
		/* Compares all of the nodes in this list to another list's nodes
		 * 
		 * Returns a false if another list has a different count
		 * to a like word or lacks that word all together
		 */
		
		boolean killList = false;
		
		Node<T> checkNode = node;
		Node<T> foreignNode = new Node<T>(); // reference to a node on another list, Thus its "Foreign"
		
		while (!killList)
		{
			foreignNode = list.search(checkNode.getCargo());
			
			if (foreignNode == null) return false;
			if (foreignNode.getCount() != checkNode.getCount()) return false; // Dif. counts means dif. lists
			
			list.remove(foreignNode.getCargo()); // Snips out node to make future traveling easier
			
			if (checkNode.getLink() == null) killList = true;
			checkNode = checkNode.getLink();
		}
		
		return true;
	}
	
	void printData()
	{
		// Prints a set of data relating to the operation of the method and a blank line when called
		
		System.out.println("Statistics for " + getClass() + ": ");
		System.out.println("Total word count: " + getTotalWordCount());		
		System.out.println("Distinct word count: " + (getSize() + 1));
		System.out.println("Total word comparisons: " + getWordsChecked());
		System.out.println("Total reference var changes: " + getRefsChanged());
		System.out.println("Total time (ms): " + getElapsedTimeMs());
		
	}
			
	int getSize()
	{
		/* Gets the amount of nodes in the list and returns it.
		 * This is useful in cases where the list must be traversed.
		 * 
		 * NOT ZERO INDEXED
		 * 
		 * Also Handy for getting a count of distinct words
		 */
		
		int listSize = 0;
		if (node.getCargo() == null && node.getLink() == null) return 0; //If the base node doesn't have cargo, then the list size is 0.
		Node<T> newNode = node;
		while(newNode.getLink() != null) // No link = last node.
		{
			listSize++;
			newNode = newNode.getLink();
		}
		
		return listSize;
	}
	
	int getTotalWordCount()
	{
		/* Gets the total count of words by adding the 
		 * getCount() of each node together until the 
		 * end of the list.
		 */
		
		int wordCount = 0;
		Node<T> checkNode = node;
		boolean endOfList = false;
		
		if (getSize() == 0) return 0; // No list = no words
		
		while(!endOfList) // Traverse from node to node
		{
		wordCount = wordCount + checkNode.getCount();
		if (!checkNode.hasNextNode()) endOfList = true;
		checkNode = checkNode.getLink();
		}
		
		return wordCount;
	}
		
	Node<T> traverse(int location)
	{
		/* Traverses to a zero-indexed place in the list and returns it.
		 * 
		 * If the method tries to traverse to a node past whatever the length
		 * of the list may be, it simply outputs alerts the user through the
		 * console and returns the last node instead.
		 */
		int stepNumber = 0;
		refChangeCount++; // About to change a reference variable
		Node<T> newNode = node;
		while(stepNumber <= location)
		{
			if (newNode.getLink() == null)
			{
				System.out.println("Error: location in list not present. Returning last node in list.");
				return newNode;
			}		
			refChangeCount++; // About to change a reference variable
			newNode = newNode.getLink();
			stepNumber++;
		}		
		return newNode;
	}
	
	long getWordsChecked()
	{
		return comparisonCount;
	}
	
	long getRefsChanged()
	{
		return refChangeCount;
	}
	
	long getElapsedTimeMs()
	{
		// Returns total elapsed time in MS from instantiation of class to method being called
		long currentTime = System.currentTimeMillis();		
		return currentTime - startTime;
	}
		
	void topHundredNodes() // used for testing
	{
		Node<T> checkNode = node;
		int index = 0;
		System.out.println("top 100 nodes: ");
		
		if (node.getCargo() != null) while (index <= 100)
		{
			System.out.println("Node_" + index + ": Contains word '" + checkNode.getCargo() + "', with count " + checkNode.getCount());
			//System.out.println(checkNode.getCargo());
			//System.out.println(checkNode.getCount());
			if (!checkNode.hasNextNode()) break;
			checkNode = checkNode.getLink();
			index++;
		}
						
		System.out.println("");
	}
	
}
