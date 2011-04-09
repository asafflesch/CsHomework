
/**
 * A Class for checking {@link LinkedList}.
 */
public class TestLinkedList {

	/**
	 * Test add to list.
	 */
	public static void testAddToList(){
		try{
			LinkedList list = new LinkedList();
			String[] str = new String[]{"My", "list","Works","without","any","problems"};

			for (int i=0;i<str.length;i++){
				list.addLast(str[i]);
			}
			System.out.println("Output should be: [My,list,Works,without,any,problems]");
			System.out.println(list.toString());
		}catch (Exception e) {
			System.err.println("Problem in method AddToList");
			e.printStackTrace();
		}
	}

	/**
	 * Test add to list2.
	 */
	public static void testAddToList2(){
		try{
			LinkedList list = new LinkedList();

			list.addLast("My");
			list.addLast("list");
			list.addLast("works");
			list.addLast("with");
			list.removeLast();
			list.addLast("without");
			list.addLast("any");
			list.addLast("problems");

			System.out.println("Output should be: [My,list,Works,without,any,problems]");
			System.out.println(list.toString());
		}catch (Exception e) {
			System.err.println("Problem in method AddToList2");
			e.printStackTrace();
		}
	}

	/**
	 * Test integers list with remove and addition.
	 */
	public static void testIntegersList(){
		try{
		LinkedList list = new LinkedList();
		final int num = 20;

		for (int i=0;i<num;i++){
			list.addLast(i);
		}


		for (int i=0;i<num;i=i+2){
			list.remove(i);
		}

		//list should be only numbers between 1 to 19 (no even numbers)
		while (!list.isEmpty()){
			Integer data = (Integer)list.getFirst();
			if (data.intValue() % 2 == 0){
				System.err.println("Found even number in the list !");
				return;
			}
			list.removeFirst();
		}
		}catch (Exception e) {
			System.err.println("Problem in method testIntegersList");
			e.printStackTrace();
		}
	}


	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		testAddToList();
		testAddToList2();
		testIntegersList();
	}
}
