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
	tree.toDotFile("mini.dot");
}
}
