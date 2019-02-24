
public class SimpleList<T> extends CustomList<T> 
{
	Node<T> travelNode = node;
	void insert(T cargo)
	/* Inserts a node into the back of the list.
	 */
	{	
		if (node.getCargo() == null) // first node needs cargo
		{			
		node.setCargo(cargo);
		return;
		}	
		
		boolean endOfList = false;
		
		Node<T> newNode = new Node<T>();
		
		newNode.setCargo(cargo);
		
		while (!endOfList)
		{
			if (travelNode.hasNextNode())
			{
				travelNode = travelNode.getLink();
			}
			else
			{
				travelNode.setLink(newNode);
				travelNode = newNode;
				endOfList = true;
			}
			
		}
		
	}

}
