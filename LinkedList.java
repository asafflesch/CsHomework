
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
	
	}
	
	/**
	 * Removes the first element in the list.
	 *  
	 * @author <b>students</b>
	 */
	public void removeFirst(){
		if (isEmpty())
			throw new NoSuchElementException();
		
		
	}
	
	/**
	 * Adds the given data as the last element in the list.
	 *
	 * @author <b>students</b>
	 * @param data the data
	 */
	public void addLast(Object data){
		
	}
	
	/**
	 * Removes the last element in the list.
	 * 
	 *  @author <b>students</b>
	 */
	public void removeLast(){
		if (isEmpty())
			throw new NoSuchElementException();
		
		
	}
	
	/**
	 * Removes the first occurrence of data in the list (if exist). this method is using {@link Object#equals(Object)}.
	 *
	 * @author <b>students</b>
	 * @param data the data
	 */
	public void remove(Object data){
		
	}
	
	/**
	 * Gets the first element data field.
	 *
	 * @author <b>students</b>
	 * @return the first element data
	 */
	public Object getFirst(){
		if (isEmpty())
			throw new NoSuchElementException();
		
		return null;
	}
	
	/**
	 * Gets the last element data field.
	 *
	 * @author <b>students</b>
	 * @return the last element data
	 */
	public Object getLast(){
		if (isEmpty())
			throw new NoSuchElementException();
		
		return null;
	}
	
	/**
	 * Checks if the list is empty.
	 *
	 * @author <b>students</b>
	 * @return true, if is empty
	 */
	public boolean isEmpty(){
		return false;
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
