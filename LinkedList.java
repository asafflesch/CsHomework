
import java.util.Iterator;
import java.util.NoSuchElementException;


public class LinkedList implements Iterable{
	private Link head;
	private Link tail;
	/**
	 * Instantiates a new linked list.
	 * 
	 * @author <b>students</b>
	 */
	public LinkedList() {
	}
	
	/**
	 * Adds the given data as the first element in the list.
	 *
	 * @author <b>students</b>
	 * @param data the data
	 */
	public void addFirst(Object data){
		if (head == null)
		{
			if (tail != null)
			{
				throw new RuntimeException("Sanity check failed - if head is null, tail should be too");
			}
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
	 * @author <b>students</b>
	 */
	public void removeFirst(){
		if (isEmpty())
                {
			throw new NoSuchElementException();
                }
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
	 * @author <b>students</b>
	 * @param data the data
	 */
	public void addLast(Object data){
		if (tail == null)
		{
			if (head != null)
			{
				throw new RuntimeException("Sanity check failed - if tail is null, head should be too");
			}
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
	 *  @author <b>students</b>
	 */
	public void removeLast(){
		if (isEmpty())
                {
			throw new NoSuchElementException();
                }
               
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
	 * @author <b>students</b>
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
				Link nextNode = curr.getNext();
				curr.getPrev().setNext(nextNode);
			}
                        found = true;
                    }
                    else curr = curr.getNext();
                }
	}
	
	/**
	 * Gets the first element data field.
	 *
	 * @author <b>students</b>
	 * @return the first element data
	 */
	public Object getFirst(){
		if (isEmpty()) return null;
		
		return head.getData();
	}
	
	/**
	 * Gets the last element data field.
	 *
	 * @author <b>students</b>
	 * @return the last element data
	 */
	public Object getLast(){
		if (isEmpty()) return null;
		
		return tail.getData();
	}
	
	/**
	 * Checks if the list is empty.
	 *
	 * @author <b>students</b>
	 * @return true, if is empty
	 */
	public boolean isEmpty(){
		// A list is empty if the head is null
		if (((head == null) && (tail != null)) || ((head != null) &&  (tail == null)))
		{
			throw new RuntimeException("Sanity check failed - head and tail must be either null or not-null together");
		}
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
