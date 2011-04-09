/**
 * The Class Represent a link in a linked list.
 */
public class Link {
	
	/** The next link in the list. */
	private Link next;
	
	/** The previous link in the list. */
	private Link prev;
	
	/** The contained data. */
	private Object data;
	
	
	/**
	 * Instantiates a new link.
	 *
	 * @param data the data
	 * @param next the next link for the current link
	 * @param prev the previous link for the current link
	 */
	public Link(Object data,Link next,Link prev){
		this.data = data;
		this.next = next;
		this.prev = prev;
	}
	
	/**
	 * Gets the next link.
	 *
	 * @return the next link
	 */
	public Link getNext() {
		return next;
	}
	
	/**
	 * Sets the next link.
	 *
	 * @param next the new next link
	 */
	public void setNext(Link next) {
		this.next = next;
	}
	
	/**
	 * Gets the data .
	 *
	 * @return the data
	 */
	public Object getData() {
		return data;
	}
	
	/**
	 * Sets the data.
	 *
	 * @param data the new data
	 */
	public void setData(Object data) {
		this.data = data;
	}
	
	/**
	 * Gets the previous link.
	 *
	 * @return the previous link
	 */
	public Link getPrev() {
		return prev;
	}
	
	/**
	 * Sets the previous link.
	 *
	 * @param prev the new previous link
	 */
	public void setPrev(Link prev) {
		this.prev = prev;
	}
	
	
}
