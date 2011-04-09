import java.util.Iterator;
import java.util.NoSuchElementException;


public class ListIterator implements Iterator{
	private Link curr;
	
	public ListIterator(Link first) {
		curr = first;
	}

	@Override
	public boolean hasNext() {
		return curr!=null;
	}

	@Override
	public Object next() {
		if (!hasNext()){
			throw new NoSuchElementException();
		}
		
		Object ans = curr.getData();
		curr = curr.getNext();
		return ans;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
