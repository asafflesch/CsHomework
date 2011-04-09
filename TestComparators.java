/**
 * A Class for testing the {@link IntegerComparator} and {@link StringComparator} classes.
 */
public class TestComparators {
	
	/**
	 * Test string comparator.
	 */
	public static void testStringComparator(){
		System.out.print("Checking String comparator .. ");
		try{
			StringComparator stringComparator = new StringComparator();
			String[] strings1 = new String[]{"A","Sholomo Artzi","Shalom Hanoch", "Ks Choise"};
			String[] strings2 = new String[]{"B","Shlomi Shabat","Shalom Hanoch", "REM"};
			int[] expected = new int[]{-1,1,0,-1};

			for (int i=0;i<strings1.length;i++){
				if(!check(stringComparator, strings1[i], strings2[i], expected[i])){
					System.err.println("Problem comparing " + strings1[i] + " and " + strings2[i]);
					return;
				}
			}
			System.out.println("Done");
		}catch (Exception e) {
			System.out.println("Error !");
			e.printStackTrace();
		}
	}


	/**
	 * Test integer comparator.
	 */
	public static void testIntegerComparator(){
		System.out.print("Checking Integer comparator .. ");
		try{
			IntegerComparator stringComparator = new IntegerComparator();
			Integer[] strings1 = new Integer[]{10,20,30,-100};
			Integer[] strings2 = new Integer[]{10,30,-100,200};
			int[] expected = new int[]{0,-1,1,-1};

			for (int i=0;i<strings1.length;i++){
				if(!check(stringComparator, strings1[i], strings2[i], expected[i])){
					System.err.println("Problem comparing " + strings1[i] + " and " + strings2[i]);
					return;
				}
			}
			System.out.println("Done");
		}catch (Exception e) {
			System.out.println("Error !");
			e.printStackTrace();
		}
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {

		TestComparators.testStringComparator();

		TestComparators.testIntegerComparator();
	}

	/**
	 * Check two objects with a comparator and check if the result is as expected.
	 *
	 * @param comp the comparator
	 * @param o1 the o1
	 * @param o2 the o2
	 * @param expected the expected
	 * @return true, if successful
	 */
	public static boolean check(Comparator comp,Object o1,Object o2,int expected){
		int ans = comp.compare(o1, o2);
		if (expected == 0)
			return ans == 0;
		else if (expected > 0)
			return ans > 0;
			else
				return ans < 0;
	}
}
