/**
 * Integer Comparator. compare {@link Integer} values - using auto-boxing is advised 
 */
public class IntegerComparator implements Comparator {
	public int compare(Object o1, Object o2) {

                int ret = 0, i1 = ((Integer)o1).intValue(),
                        i2 = ((Integer)o2).intValue();

                if (i1 > i2) ret = 1;
                else if (i1 < i2) ret = -1;

		return ret;
	}
}
