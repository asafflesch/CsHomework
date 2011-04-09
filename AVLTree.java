import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The Class that represents an AVL tree.
 */
public class AVLTree {
	
	/** The root. */
	protected AVLNode root;
	
	/** The comparator. */
	protected Comparator comp;

	/**
	 * Instantiates a new AVL tree.
	 *
	 * @param comp the {@link Comparator} to be used in the tree
	 */
	public AVLTree(Comparator comp) {
		this.root = null;
		this.comp = comp;
	}

	/**
	 * Gets the root of the tree.
	 *
	 * @return the root
	 */
	public AVLNode getRoot(){
		return this.root;
	}
	
	/**
	 * Adds a new Node into the tree.
	 *
	 * @param key the key of the new node
	 * @param data the data of the new node
	 */
	public void add(Object key,Object data){
		if (isEmpty()){
			this.root = new AVLNode(key,data,comp);
		}
		else{
			root = this.root.add(key,data);			
		}
	}

	/**
	 * Removes a node n from the tree where n.key is equal (by {@link Comparator}) to the given key.
	 *
	 * @param key the key
	 */
	public void remove(Object key){
		if (isEmpty()){
			return;	
		}
		else
			root = this.root.remove(key);

	}

	/**
	 * Finds a node n from the tree where n.key is equal (by {@link Comparator}) to the given key.
	 *
	 * @param key the key
	 * @return the data of the found node, null if node isn't found
	 */
	public Object find(Object key){
		if (isEmpty()){
			return null;
		}
		else
			return this.root.find(key);
	} 

	/**
	 * Finds a node n from the tree where n is the K'th element in the in-Order sequence of the tree.
	 *
	 * @param k a number between 1 and {@link #size()}
	 * @return the data of the found node, null if node isn't found
	 */
	public Object findKthElement(int k){
		if (isEmpty()){
			return null;
		}
		else
			return this.root.findKthElement(k);
	}

	/**
	 * Gets all the elements in the tree that are between the K'th element and the H'th element (in the in-Order sequence).
	 *
	 * @param k A number between 1 and {@link #size()}
	 * @param h A number between k and {@link #size()}
	 * @return the elements in the tree between the arguments
	 */
	public LinkedList getKthTillHth(int k,int h) {
		if (isEmpty())
			return new LinkedList();
		return root.getKthTillHth(k,h);
	}

	/**
	 * Print the tree in an in-order to a dot file format.
	 */
	public void toDotFile(String filename){
		if (root == null)
			return;
		StringBuilder sb = new StringBuilder();
		sb.append("digraph {\n");
		sb.append("{rank = same ; \""+root.getKey()+"\"};\n");
		root.inOrderToString(sb);
		sb.append("}");
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(filename));
			out.write(sb.toString());
			out.close();
		}catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Size of the tree.
	 *
	 * @return the int
	 */
	public int size(){
		if (isEmpty())
			return 0;
		return root.size();
	}

	/**
	 * Height of the tree.
	 *
	 * @return the int
	 */
	public int height(){
		if (isEmpty()){
			return 0;
		}
		return root.height();
	}
	
	/**
	 * Checks if the tree is empty.
	 *
	 * @return true, if is empty
	 */
	public boolean isEmpty(){
		return this.root == null;
	}
}


