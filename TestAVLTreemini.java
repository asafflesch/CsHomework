public class TestAVLTreemini
{
public static void main(String[] args)
{
	AVLTree tree = new AVLTree(new IntegerComparator());
	tree.add(3,3);
	tree.add(2,2);
	tree.add(1,1);
	tree.add(4,4);
	tree.add(5,5);
	tree.add(6,6);
	tree.add(7,7);
	tree.add(16,16);
	tree.add(15,15);
	tree.add(14,14);


	tree.toDotFile("mini1.dot");

        // doing some removes, just to see
        tree.remove(16);

	tree.toDotFile("mini2.dot");

        // doing some removes, just to see
        tree.remove(6);

	tree.toDotFile("mini3.dot");

        // doing some removes, just to see
        tree.remove(4);

	tree.toDotFile("mini4.dot");

	tree.add(4,4);
	tree.add(6,6);
	tree.add(16,16);

	tree.toDotFile("mini5.dot");
}
}
