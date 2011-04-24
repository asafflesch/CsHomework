
import java.util.Iterator;
import java.util.NoSuchElementException;


public class LinkedList implements Iterable{
	private Link head;
	private Link tail;
	private final String HEAD_STRING="I am the head";
	private final String TAIL_STRING="I am the tail";
	/**
	 * Instantiates a new linked list.
	 * 
	 * @author <b>students</b>
	 */
	public LinkedList() {
            head = new Link(HEAD_STRING,null,null);
            tail = new Link(TAIL_STRING,null,null);
            head.setNext(tail);
            tail.setPrev(head);
	}
	
	/**
	 * Adds the given data as the first element in the list.
	 *
	 * @author <b>students</b>
	 * @param data the data
	 */
	public void addFirst(Object data){
	    Link newLink = new Link(data, null, null);
            Link oldNext = head.getNext();
            head.setNext(newLink);
            newLink.setPrev(head);
            newLink.setNext(oldNext);
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
               // Guaranteed to exist, otherwise the list would be empty
                Link newNext = head.getNext().getNext();
                head.setNext(newNext);
	        newNext.setPrev(head);
	}
	
	/**
	 * Adds the given data as the last element in the list.
	 *
	 * @author <b>students</b>
	 * @param data the data
	 */
	public void addLast(Object data){
	    Link newLink = new Link(data, null, null);
            Link oldPrev = tail.getPrev();
            tail.setPrev(newLink);
            newLink.setNext(tail);
            newLink.setNext(oldPrev);
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
               
               // Guaranteed to exist, otherwise the list would be empty
                Link newNext = tail.getPrev().getPrev();
                tail.setPrev(newNext);
                newNext.setNext(tail);
		
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

                /*Find element*/
                boolean found = false;
		/* The list is always guaranteed to have the dummy nodes of 
                head and tail. */
                Link curr = head.getNext();
                while (curr.getData()!=TAIL_STRING && !found)
                {
                    /* found the data, removing */
                    if (curr.getData().equals(data))
                    {
                        Link temp = curr.getPrev();
                        curr.getNext().setPrev(curr.getPrev());
                        temp.setNext(curr.getNext());

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
		return head.getNext().getData()==TAIL_STRING;
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
