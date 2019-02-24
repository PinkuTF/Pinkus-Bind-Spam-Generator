class Node<T>
{
	
	private Node<T> link;
	
	T cargo;
	
	int cargoCount = 1;
	
	void setCargo(T data)
	{
		cargo = data;
	}
	
	T getCargo()
	{
		return cargo;
	}
	
	
	void setLink(Node<T> newLink)
	{
		link = newLink;
	}
	
	
	Node<T> getLink()
	{
		return link;
	}
	
	void increment()
	{
		cargoCount++;
	}
	
	void decrement()
	{
		cargoCount--;
	}
	
	void setCount(int newCount)
	{
		cargoCount = newCount;
	}
	
	boolean hasNextNode()
	{		
		// Makes life easier when trying to see if at end of list
		if (getLink() == null) return false;
		else return true;
	}
	
	int getCount()
	{
		return cargoCount;
	}
}