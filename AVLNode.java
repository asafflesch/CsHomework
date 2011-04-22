

/**
 * AVL Node in the {@link AVLTree}.
 */
public class AVLNode {
	/** The size of the tree. */
	private int size;
	
	/** The height of the tree. */
	private int height;
	
	/** The key of the current node. */
	private Object key;
	
	/** The data of the current node. */
	private Object data;
	
	/** The {@link Comparator} used by the node. */
	private Comparator comp;
	
	/** All the nodes pointed by the current node. */
	private AVLNode left,right,parent,succ,pred;

	/**
	 * Instantiates a new AVL node.
	 *
	 * @param key the key of the node
	 * @param data the data that the node should keep
	 * @param comp the comparator to be used in the tree
	 */
	public AVLNode(Object key, Object data, Comparator comp) {
		this(key,data,comp,null);
	}

	/**
	 * Instantiates a new AVL node.
	 *
	 * @param key the key of the node
	 * @param data the data that the node should keep
	 * @param comp the comparator to be used in the tree
	 * @param parent the parent of the created node
	 */
	public AVLNode(Object key, Object data, Comparator comp, AVLNode parent) {
		this.data = data;
		this.key = key;
		this.comp = comp;
		this.parent = parent;
		
		this.left = null;
		this.right = null;
		this.succ = null;
		this.pred = null;
		
		this.size = 1;
		this.height = 0;
	}

	/**
	 * Adds the given data to the tree.
	 *
	 * @param key the key
	 * @param data the data
	 * @return the root of the tree after insertion and rotations
	 * @author <b>students</b>
	 */
	public AVLNode add(Object key,Object data) {

		return null;
	}

	/**
	 * Removes a Node which key is equal (by {@link Comparator}) to the given argument.
	 *
	 * @param key the key
	 * @return the root after deletion and rotations
	 * @author <b>students</b>
	 */
	public AVLNode remove(Object key) {
		return null;	
	}

	/**
	 * Gets all the elements between the K'th and the H'th element in the tree.
	 *
	 * @param k A number between 1 and @link {@link #size()}
	 * @param h A number between k and @link {@link #size()}
	 * @return elements between k and h
	 * @author <b>students</b>
	 */
	public LinkedList getKthTillHth(int k, int h) {

            LinkedList ret = new LinkedList();

            // Get Kth element
            AVLNode curr = findKthNode(k);

            // Adding elements to array
            for (int i = k; i < h && curr != null; ++i)
            {
                ret.addLast(curr.data);
                curr = curr.succ;
            }


            return ret;
	}
	

	/**
	 * Finds a Node which key is equal (by {@link Comparator}) to the given argument.
	 *
	 * @param key the key of the node
	 * @return the data of the found Node, returns null if node isn't found
	 * @author <b>students</b>
	 */
	public Object find(Object key) {
		return null;
	}

	/**
	 * Find K'th element in the tree.
	 *
	 * @param k is a number between 1 and {@link #size()}
	 * @return the object
	 * @author <b>students</b>
	 */
	public Object findKthElement(int k){

            AVLNode elem = findKthNode(k);

            Object ret = null;
            if (elem != null)
                ret = elem.data;

            return ret;
	}

        private AVLNode findKthNode(int k){

            AVLNode ret = null;


            // Checking the left subtree
            if (left == null)
            {
                // If there are no elements to the left, this is the
                // first element in this tree
                if (k == 1)
                {
                    ret = this;
                }
                else
                {
                    if (right != null)
                        ret = right.findKthNode(k - 1);
                }
            }
            else
            {
                if (left.size() >= k) {
                    ret = left.findKthNode(k);
                }
                else if (left.size() == k - 1)
                {
                    ret = this;
                }
                else if (left.size() < k - 1)
                {
                    if (right != null)
                        ret = right.findKthNode(k - (left.size() + 1));
                }
            }

            return ret;
        }
	
	/**
	 * Return the size of the tree.
	 *
	 * @return the size of the tree.
	 */
	public int size(){
		return this.size;
	}

	
	/**
	 * Return the height of the tree.
	 *
	 * @return the height of the tree.
	 */
	public int height(){
		return this.height;
	}

	/**
	 * Gets the key of the current node.
	 *
	 * @return the key
	 */
	public Object getKey() {
		return this.key;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "(" + this.key + " " + this.data + ")";
	}

	/**
	 * Load the current tree (starting with the current node) into a {@link StringBuilder} in a in-order way.
	 *
	 * @param sb the {@link StringBuilder}
	 */
	public void inOrderToString(StringBuilder sb){
		if (this.left != null){	
			this.left.inOrderToString(sb);
			sb.append(this.key + " -> " + left.key + ";\n");
		}else
		{
			sb.append("nulll"+this.key+" [shape=point];\n");
			sb.append(this.key + " -> nulll" + this.key + ";\n");
		}

		sb.append(this.key + "[label=\""+this.data+",s:"+this.size()+",h:"+this.height()+"\"];\n");
		if (this.parent != null)
			sb.append(this.key + " -> " + parent.key + "[style=dashed];\n");

		if (this.right != null){
			this.right.inOrderToString(sb);
			sb.append(this.key + " -> " + right.key + ";\n");
		}else
		{
			sb.append("nullr"+this.key+" [shape=point];\n");
			sb.append(this.key + " -> nullr" + this.key + ";\n");
		}
	}

	/**
	 * Gets the predecessor of the current node.
	 *
	 * @return the predecessor
	 */
	public AVLNode getPred() {
		return pred;
	}
	
	/**
	 * Gets the successor of the current node.
	 *
	 * @return the successor
	 */
	public AVLNode getSucc(){
		return succ;
	}
}
