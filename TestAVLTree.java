

import java.util.Random;

/**
 * A class for checking the AVL implementation
 */
public class TestAVLTree {

	/**
	 * Swap to cells in an array.
	 *
	 * @param arr the array
	 * @param i the i
	 * @param j the j
	 */
	private static void swap(int[] arr,int i,int j){
		final int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

	/**
	 * Shuffle an array.
	 *
	 * @param arr the array to be shuffled
	 */
	private static void shuffle(int[] arr){
		int top = arr.length - 1;
		Random rand = new Random();
		while (top >= 0){
			swap(arr,top,rand.nextInt(top+1));
			top--;
		}
	}

	/**
	 * Test search in an AVL tree. the search is done with a special {@link Comparator} that counts the number of times the compare method was activated.
	 * The method insert num elements to the tree then search each element and check if the number of compares is O(log num).
	 *
	 * @param num the number of elements to add to the tree
	 */
	public static void testSearchAmount(int num){
		System.out.println("Running testSearchAmount("+num+")");
		try{
			long time;
			AVLTree tree = new AVLTree(new IntegerComparatorCounter());

			int[] array = new int[num];
			System.out.print("Adding all numbers in range [0 .. " + (num-1) + "] .. ");
			for (int i=0;i<num;i++){
				array[i] = i;
			}
			shuffle(array);

			time = System.currentTimeMillis();
			for (int i=0;i<array.length;i++){
				tree.add(array[i],array[i]);
			}
			System.out.println("Done. (" + (System.currentTimeMillis() - time) + " milliseconds)");
			time = System.currentTimeMillis();
			System.out.print("Searching all numbers in range [0 .. " + (num-1) + "] .. ");
			for (int i=num-1;i>=0;i--){
				IntegerComparatorCounter.counter = 0;
				tree.find(i);
				int amount = IntegerComparatorCounter.counter;
				int max = 10 * (int)Math.ceil(Math.log(num) / Math.log(2));
				if (amount > max){
					System.err.println("\nProblem finding " + i + " too much comparisons");
					System.err.println("took " + amount);
					System.err.println("exptected O(log_2(" + num +"))=" + max + "(with a constant of 10 !)");
					return;
				}
			}
			System.out.println("Done. (" + (System.currentTimeMillis() - time) + " milliseconds)");	
		}catch (Throwable ex){
			System.err.println("Problem running SearchAmount with n=" + num);
			ex.printStackTrace();
			return;
		}

		System.out.println("Finished Testing number of comparisons\n---");
	}

	/**
	 * Test pred and succ of the tree. we add elements to the tree in a shuffled way and then
	 * check if we run on the tree from the root to both sides (pred and succ). the answer should be ordered as in-order
	 *
	 * @param limit the maximal element
	 * @param jump the jump (distance) between to numbers in the tree
	 * @param delete also check with deleting even keys
	 */
	public static void testPredAndSucc(int limit,int jump,boolean delete){
		System.out.println("Running testPredAndSucc(" + limit + "," + jump + "," + delete + ")");
		try{
			AVLTree tree = new AVLTree(new IntegerComparator());
			int[] array = new int [limit / jump];
			int[] shuffledArray = new int [limit / jump];
			for (int i=0;i<limit/jump;i ++){
				array[i] = i * jump;
				shuffledArray[i] = i * jump;
			}
			shuffle(shuffledArray);
			for (int i=0;i<limit/jump;i ++){
				tree.add(shuffledArray[i],"Bla Bla" + shuffledArray[i]);
			}

			if (delete){
				for (int i=0;i<array.length;i=i+2){
					tree.remove(array[i]);
				}
			}
			
			AVLNode current = tree.getRoot();
			//go back to start
			while (current.getPred() != null)
				current = current.getPred();

			int adv = delete ? 2 : 1; 
			int i = delete ? 1 : 0;
			while (current != null){
				if (((Integer)current.getKey()).intValue() != array[i]){
					System.err.println("Problem in the order. got " + current.getKey() + " instead of " + array[i]);
					return;
				}
				i= i + adv;
				current = current.getSucc();
			}
			

		}catch (Throwable ex){
			System.err.println("Problem running testPredAndSucc(" + limit + "," + jump + "," + delete + ")");
			ex.printStackTrace();
			return;
		}
		System.out.println("Done.\n----");
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		System.out.println("Testing size");
		TestAVLTree.testSize(10);
		TestAVLTree.testSize(50);
		System.out.println("Testing Search in tree");
		TestAVLTree.testSearchAmount(10);
		TestAVLTree.testSearchAmount(100);
		TestAVLTree.testSearchAmount(1000);
		TestAVLTree.testSearchAmount(1000000);
		System.out.println("Testing Pred and Succ in tree");
		TestAVLTree.testPredAndSucc(10,1,false);
		TestAVLTree.testPredAndSucc(100,2,false);
		TestAVLTree.testPredAndSucc(1000,4,false);
		TestAVLTree.testPredAndSucc(10,1,true);
		TestAVLTree.testPredAndSucc(100,2,true);
		TestAVLTree.testPredAndSucc(1000,4,true);
		System.out.println("Testing Remove from tree");
		TestAVLTree.testDelete(10);
		TestAVLTree.testDelete(100);
		TestAVLTree.testDelete(1000);

	}

	/**
	 * Test size of tree by adding size elements and check to see that the size of the root is OK.
	 *
	 * @param size the size
	 */
	private static void testSize(int size) {
		System.out.println("Running testSize("+size+")");
		try{
			AVLTree tree = new AVLTree(new StringComparator());
			String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
			Random rand = new Random();
			for (int i=0;i<size;i++){
				int len = rand.nextInt(50) + 20;
				String key = "";
				for (int x = 0;x<len;x++){
					key += str.charAt(rand.nextInt(str.length()));
				}
				tree.add(key, key);
			}
			if (tree.size() != size){
				System.err.println("Wrong size of tree - actual size: " +  tree.size() + "!");
			}
		}catch (Exception e) {
			System.err.println("Problem running testSize("+size+")");
			e.printStackTrace();
			return;
		}
		System.out.println("Finished\n---");
	}

	/**
	 * Test delete form a tree.
	 * 1. adding size elements
	 * 2. remove all elements with even key
	 * 3. check to see that the tree is still correct
	 *
	 * @param size the number of elements to add
	 */
	private static void testDelete(int size) {
		System.out.println("Running testDelete("+size+")");
		try{
			AVLTree tree = new AVLTree(new IntegerComparator());
			for (int i=0;i<size;i++){
				tree.add(i, new String("Bla Bla" + i));
			}

			for (int i=0;i<size;i=i+2){
				tree.remove(i);
			}

			for (int i=0;i<size;i++){
				Object ans = tree.find(i);
				if (i % 2 == 0 && ans != null){
					//deleted but still found
					System.err.println("Found object that was deleted. key was "+ i);
					return;
				}else if(i % 2 != 0 && ans == null){
					//not deleted and not found
					System.err.println("didn't find object that was inserted. key was " + i);
					return;
				}else if (i % 2 != 0 && !("Bla Bla"+i).equals(ans)){
					//didn't find the right object
					System.err.println("didn't find the object that we looked for ! " + ans + " instead of " + ("Bla Bla"+i));
					return;
				}
			}

		}catch (Exception e) {
			System.err.println("Problem running testDelete("+size+")");
			e.printStackTrace();
			return;
		}
		System.out.println("Finished\n---");
	}
}
