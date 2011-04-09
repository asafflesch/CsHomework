
/**
 * The Class IntegerComparatorCounter is used only for checking. used in {@link TestAVLTree} DO NOT CHANGE IT
 */
public class IntegerComparatorCounter extends IntegerComparator {

	/** The counter. */
	public static int counter = 0;
	
	/* (non-Javadoc)
	 * @see solution.IntegerComparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Object o1, Object o2) {
		counter++;
		return super.compare(o1, o2);
	}

}
