
import java.util.Iterator;
import java.util.NoSuchElementException;


public class LinkedList implements Iterable{
	// Head and tail here are the same if we have a single item, and point to each other
	// if there are two items - i.e, there are no 'dummy' nodes. It's all internal, so 
	// we can always make sure of the integrity of both
	private Link head;
	private Link tail;
	/**
	 * Instantiates a new linked list.
	 * 
	 * @author <b>Asaf Flescher, Dana Katz-Buchstav</b>
	 */
	public LinkedList() {
	}
	
	/**
	 * Adds the given data as the first element in the list.
	 *
	 * @author <b>Asaf Flescher, Dana Katz-Buchstav</b>
	 * @param data the data
	 */
	public void addFirst(Object data){
		if (head == null)
		{
			// Empty list
			head = new Link(data, null, null);
			tail = head;
		}
		else
		{
			if (head.getNext() == null)
			{
				// List with only one entry
				tail = new Link(head.getData(), null, head);
				head.setNext(tail);
				head.setData(data);
			}
			else
			{
				// List with at least two entries
				Link oldNext = head.getNext();
				Link newLink = new Link(head.getData(), oldNext, head);
				head.setNext(newLink);
				oldNext.setPrev(newLink);
				head.setData(data);
			}
		}

	}
	
	/**
	 * Removes the first element in the list.
	 *  
	 * @author <b>Asaf Flescher, Dana Katz-Buchstav</b>
	 */
	public void removeFirst(){
		if (isEmpty())
                {
			throw new NoSuchElementException();
                }
		// If the head's next is null, the new list is just empty
		if (head.getNext() != null) 
		{
			head = head.getNext();
			head.setPrev(null);
		}
		else
		{
			head = null;
			tail = null;
		}
	}
	
	/**
	 * Adds the given data as the last element in the list.
	 *
	 * @author <b>Asaf Flescher, Dana Katz-Buchstav</b>
	 * @param data the data
	 */
	public void addLast(Object data){
		if (tail == null)
		{
			// Empty list
			tail = new Link(data, null, null);
			head = tail;
		}
		else
		{
			if (tail.getPrev() == null)
			{
				// List with only one entry
				head = new Link(tail.getData(), tail, null);
				tail.setPrev(head);
				tail.setData(data);
			}
			else
			{
				// List with at least two entries
				Link oldPrev = tail.getPrev();
				Link newPrev = new Link(tail.getData(), tail, oldPrev);
				oldPrev.setNext(newPrev);
				tail.setPrev(newPrev);
				tail.setData(data);
			}
		}
	}
	
	/**
	 * Removes the last element in the list.
	 * 
	 *  @author <b>Asaf Flescher, Dana Katz-Buchstav</b>
	 */
	public void removeLast(){
		if (isEmpty())
                {
			throw new NoSuchElementException();
                }
               	// If tail's prev is null, the new list is just empty
		if (tail.getPrev() != null)
		{
			tail = tail.getPrev();
			tail.setNext(null);
		}
		else
		{
			tail = null;
			head = null;
		}
	}
	
	/**
	 * Removes the first occurrence of data in the list (if exist). this method is using {@link Object#equals(Object)}.
	 *
	 * @author <b>Asaf Flescher, Dana Katz-Buchstav</b>
	 * @param data the data
	 */
	public void remove(Object data){
		if (isEmpty())
			throw new NoSuchElementException();

                // Find element
                boolean found = false;

                Link curr = head;
                while ((curr != null) && !found)
                {
                    // found the data, removing 
                    if (curr.getData().equals(data))
                    {
			if (curr.getNext() == null)
			{
				removeLast();
			}
			else if (curr.getPrev() == null)
			{
				removeFirst();
			}
			else
			{
				// Hooking up the prev and next nodes to 'skip' this node which is being deleted
				Link nextNode = curr.getNext();
				curr.getPrev().setNext(nextNode);
				nextNode.setPrev(curr.getPrev());
			}
                        found = true;
                    }
                    else curr = curr.getNext();
                }
	}
	
	/**
	 * Gets the first element data field.
	 *
	 * @author <b>Asaf Flescher, Dana Katz-Buchstav</b>
	 * @return the first element data
	 */
	public Object getFirst(){
		if (isEmpty()) return null;
		
		return head.getData();
	}
	
	/**
	 * Gets the last element data field.
	 *
	 * @author <b>Asaf Flescher, Dana Katz-Buchstav</b>
	 * @return the last element data
	 */
	public Object getLast(){
		if (isEmpty()) return null;
		
		return tail.getData();
	}
	
	/**
	 * Checks if the list is empty.
	 *
	 * @author <b>Asaf Flescher, Dana Katz-Buchstav</b>
	 * @return true, if is empty
	 */
	public boolean isEmpty(){
		// A list is empty if the head is null(or the tail is null, they're functionally the same for that purpose)
		return (head == null);
	}

	/* (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	public Iterator iterator() {
		return new ListIterator(head);
	}
	
	public String toString(){
		if (isEmpty())
			return "[]";
		
		Iterator it = iterator();
		StringBuilder sb = new StringBuilder();
		while (it.hasNext()){			
			sb.append(',');
			sb.append(it.next().toString());
		}
		sb.append(']');
		sb.setCharAt(0, '[');
		return sb.toString();
	}
}
