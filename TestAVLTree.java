

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
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
					tree.toDotFile("MightWork.dot");
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
				System.err.print(current.getKey().toString() + "->");
				current = current.getSucc();
			}
			System.err.println();
			

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
		
		TestAVLTree.testKth(10,1,false);
		TestAVLTree.testKth(100,2,false);
		TestAVLTree.testKth(1000,4,false);
		TestAVLTree.testKth(10,1,true);
		TestAVLTree.testKth(100,2,true);
		TestAVLTree.testKth(1000,4,true);
		TestAVLTree.testKthSpecific(100,false);
		TestAVLTree.testKthSpecific(1000,false);
		TestAVLTree.testKthSpecific(10000,false);
		TestAVLTree.testKthSpecific(100,true);
		TestAVLTree.testKthSpecific(1000,true);
		TestAVLTree.testKthSpecific(10000,true);
	}
	
	
	private static void testKth(int limit,int jump,boolean delete)
	{
		System.out.println("Running testKth(" + limit + "," + jump + "," + delete + ")");
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
				tree.add(shuffledArray[i],shuffledArray[i]);
			}

			if (delete){
				for (int i=0;i<array.length;i=i+2){
					int oldSize = tree.size();
					tree.toDotFile("Before.dot");
					tree.remove(array[i]);
					if (tree.size() != oldSize - 1)
					{
						tree.toDotFile("After.dot");
					}
				}
			}
			int size = tree.size();
			int k = size / 3;
			int h = 2 * size / 3;
			System.out.println("Got size,k,h " +size + " " + k + " " +h);
			LinkedList lst = tree.getKthTillHth(k, h);
			Iterator it = lst.iterator();
			int adv = delete ? 2 : 1; 
			int i = (delete ? 1 : 0) + (k-1) * adv;
			while (it.hasNext()){
				Object cur = it.next();
				//System.out.println("Got " +cur);
				if (((Integer)cur).intValue() != array[i]){
					System.out.print(((Integer)cur).intValue() + "(B " + array[i] + ")->");
					//System.err.println("Problem in the order. got " + cur + " instead of " + array[i]);
				//	return;
				}
				else
				{
					System.out.print(((Integer)cur).intValue() + "->");
				}
				i= i + adv;
				
			}
			

		}catch (Throwable ex){
			System.err.println("Problem running testKth(" + limit + "," + jump + "," + delete + ")");
			ex.printStackTrace();
			return;
		}
		System.out.println("Done.\n----");
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
				System.err.println("Wrong size of tree !");
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
	
	
	private static void testKthSpecificReportError(AVLTree tree, String folder)
	{
		tree.toDotFile(folder+"Tree.dot");
		Iterator it = tree.getKthTillHth(1, tree.size()).iterator();
		String s = "";
		if (it.hasNext()) {s = it.next().toString();}
		while(it.hasNext())
		{
			s+= " => " +it.next().toString();
		}
		BufferedWriter out;
		try {
			out = new BufferedWriter(new FileWriter(folder+"Treelist.dot"));
			out.write(s);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	private static void testKthSpecific(int size, boolean delete)
	{
		System.out.println("Running testKthSpecific("+size+"," + delete+ ")");
		AVLTree tree = new AVLTree(new IntegerComparator());
		//int[] data = {934,975,144,582,113,259,8,514,183,720,208,34,791,816,703,248,24,221,44,666,962,429,69,285,972,154,410,935,783,328,65,629,81,939,705,358,277,861,146,278,13,615,223,578,912,793,108,437,846,250,823,416,913,932,190,534,381,347,822,795,506,704,540,635,699,382,168,545,763,28,380,356,623,59,652,641,995,999,136,717,620,453,279,997,40,450,853,505,79,877,830,565,747,253,685,261,924,515,722,993,204,627,82,184,211,412,88,132,561,480,427,701,228,774,119,478,64,157,199,771,952,163,161,244,581,180,111,560,905,643,713,174,757,959,404,496,399,786,309,57,953,988,360,682,960,95,650,936,503,142,883,289,283,267,368,769,83,446,302,989,812,218,314,875,469,299,945,162,630,967,23,411,265,189,938,574,951,33,521,857,542,725,511,308,194,963,266,529,319,225,207,814,419,778,914,526,986,426,312,179,181,767,658,49,41,544,806,614,92,744,868,320,170,463,100,78,452,522,212,662,237,645,955,205,585,927,553,634,447,394,844,577,548,271,661,451,346,941,756,134,12,525,693,931,867,151,475,375,655,901,678,892,881,497,185,865,843,354,397,435,974,956,216,510,996,109,594,782,519,297,337,68,564,217,45,325,804,348,836,90,75,349,563,98,428,340,640,147,670,900,983,125,135,327,520,874,613,809,307,498,728,608,872,15,379,433,367,675,230,32,5,940,897,700,110,990,811,0,80,683,680,331,887,612,551,54,948,855,750,691,790,388,591,785,166,460,448,981,255,950,329,263,516,487,579,148,599,523,649,272,958,969,657,333,834,820,341,344,373,103,336,27,922,405,715,420,293,114,362,911,797,173,462,476,813,300,11,977,350,539,55,825,442,784,916,415,625,689,7,17,536,906,240,398,30,193,471,385,907,241,554,85,430,847,286,409,424,227,770,494,370,711,636,294,47,115,472,387,25,123,432,923,882,762,656,87,569,718,219,973,243,238,71,408,738,291,979,992,552,796,303,880,22,127,588,925,860,885,67,698,140,893,470,224,827,124,944,513,158,220,598,213,376,558,200,743,821,971,573,62,937,392,94,292,425,710,842,4,258,709,527,229,178,143,210,226,298,20,517,708,886,731,37,461,759,84,474,101,571,288,262,86,949,610,833,724,36,600,745,423,401,249,547,468,493,192,322,130,121,984,481,879,849,313,777,107,764,149,53,316,593,116,311,727,377,942,29,648,275,323,74,438,621,473,766,195,335,459,268,364,443,407,928,72,891,982,445,466,841,499,651,406,845,663,642,105,805,296,858,667,723,570,260,236,330,104,318,160,677,63,403,702,97,282,831,664,191,159,232,761,413,854,659,920,753,117,994,818,714,35,19,903,866,500,737,755,592,535,624,930,541,946,628,802,156,732,611,131,606,153,697,273,274,557,676,2,729,457,175,609,817,391,660,89,152,954,137,976,566,339,908,918,284,39,138,646,400,509,758,256,850,632,605,490,31,575,735,214,9,489,384,365,617,257,863,317,991,898,665,654,42,964,712,372,129,361,281,915,21,326,794,141,549,807,832,828,479,383,70,502,6,706,895,169,93,751,467,353,894,870,602,389,366,607,668,488,673,878,363,77,305,550,787,644,165,188,252,304,386,538,58,202,52,465,690,332,716,596,145,919,619,486,264,760,454,856,970,464,562,524,66,801,528,418,622,334,440,746,862,417,439,572,533,669,776,301,483,800,741,859,172,568,532,518,768,692,835,556,396,458,546,76,449,414,740,910,965,681,788,61,586,926,16,543,803,352,687,48,864,50,359,245,441,51,96,378,507,26,118,122,209,112,321,306,3,171,269,580,674,957,531,60,694,421,342,482,351,390,290,772,508,197,422,742,616,819,537,890,985,899,46,604,239,781,966,402,576,270,198,873,10,374,203,512,235,251,231,206,369,637,215,431,876,393,477,196,852,590,779,246,601,933,733,222,736,943,909,824,765,684,280,851,647,633,589,102,167,484,38,177,434,597,310,884,455,120,247,43,1,315,869,798,829,987,961,653,980,838,754,492,357,808,826,587,106,555,233,848,749,201,133,839,840,242,56,638,485,696,295,904,773,155,595,917,679,603,491,276,871,583,559,686,128,504,186,815,888,921,671,355,495,896,929,618,734,501,14,978,584,371,444,631,436,126,775,639,395,739,91,287,254,324,947,730,688,752,780,139,837,721,799,99,176,707,672,73,182,789,345,792,719,626,968,810,164,998,902,748,456,338,18,187,150,343,567,695,530,889,726,234};
		int[] data = new int[size];
		for (int i=0; i <data.length; ++i)
		{
			data[i] = i;
		}
		shuffle(data);
		
		
		
		for (int i=0; i< size; ++i) {
			tree.add(data[i], data[i]);	
		}
		
		if (tree.size() != size)
		{
			System.err.println("Tree size is " + tree.size() + " after addition but should be " + size);
			testKthSpecificReportError(tree, "z:\\DSTestData\\");
			return;
		}
		
		if (delete)
		{
			int deleted = 0;
			// remove half of them
			for (int i=0; i<size; ++i) {
				tree.remove(data[i]);
				int temp = data[i];
				data[i] = -1;
				++deleted;
				if (tree.size() != (data.length-deleted))
				{
					System.err.println("Tree size is " + tree.size() + " after deletion of " + temp + " but should be " +(data.length-deleted));
					testKthSpecificReportError(tree, "z:\\DSTestData\\");
					return;
				}
			}
			
		}
		// Check there's all in there
		Iterator it = tree.getKthTillHth(1, tree.size()).iterator();
		while(it.hasNext())
		{
			int curNum = (int)(Integer) it.next();
			boolean foundIt = false;
			for (int i=0; i<data.length; ++i) 
			{ 
				if (data[i] == curNum)
				{
					foundIt = true; 
					data[i] = -1;
					break;
				}
			}
			if (!foundIt)
			{
				System.err.println("Number " + curNum + " in tree's list but was deleted (or never inserted)!");
				testKthSpecificReportError(tree, "z:\\DSTestData\\");
				return;
			}
			Object obj = tree.find(curNum);
			if (obj == null || ((Integer)obj).intValue() != curNum)
			{
				System.err.println("Number " + curNum + " in tree's list but not in actual tree!");
				return;
			}		
		}
		
		for (int i=0; i<size; ++i)
		{
			if (data[i] != -1)
			{
				System.err.println("Number " + data[i] + " was inserted but not returned by tree's list!");
				return;
			}
		}
		System.out.println("Done.\n----");
	}
}
