

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
	 * @author <b>Asaf Flescher, Dana Katz-Buchstav</b>
	 */
	public AVLNode add(Object key,Object data) {
		int compResult = comp.compare(key, this.key);
		boolean newLevelAdded = false;
                AVLNode nodeToUse = null;
                if (compResult <= 0)
		{
			nodeToUse = left;	
		}
		else
		{
			nodeToUse = right;
		}
		// If the tree is empty
		if (nodeToUse == null)
		{
			nodeToUse = new AVLNode(key, data, this.comp, this);	
			// Put the new subtree back in the node
	                if (compResult <= 0)
			{
				left = nodeToUse;
			}
			else
			{
				right = nodeToUse;
			}
			// If left and right are null, then we're adding a new 'level' to the tree and need to update the height of everything in the tree
			if ((left == null) && (right == null))
			{
				newLevelAdded = true;
			}
		}
		else
		{
			nodeToUse.add(key, data);
		}



		if (newLevelAdded)
		{
			height = 1;
			boolean needToChange = true;
			AVLNode currParent = parent;
			// Run until we either hit the root or the change stops reverbating - i.e, the added node doesn't change the height of the level
			while ((currParent != null) && (needToChange))
			{
				int maxHeight = currParent.getMaxHeight() + 1;
				if (maxHeight > currParent.height)
				{
					currParent.height = maxHeight;
					currParent = currParent.parent;
				}	
				else
				{
					needToChange = false;
				}
			}
		}

		// Adjusting the size - we added a node, so it goes up by 1.
		size = size + 1;

		// Now we check if the new tree is still balanced
		int balance = balanceFactor();

		// Four cases for balancing
		if (balance == -2)
		{
			int rightBalance = right.balanceFactor();
			if (rightBalance <= 0)
			{
				// RRC - one left rotation needed

				// left rotation 

				if (parent != null)
				{
					if (isRightChild())
					{
						parent.right = right;
					}
					else
					{
						parent.left = right;
					}
				}
				AVLNode tempNode = right.left;
				right.parent = parent;

				parent = right;
				right.left = this;

				if (tempNode != null)
				{
					tempNode.parent = this;
				}
				right = tempNode;
			}
			else
			{
				// RLC - first rotate the subtree to the right, then rotate to the left

				// right rotation of subtree

				// The added node causing the imbalance
				AVLNode unBalancingNode = right.left;

				// Making the current right node point to it's new left - the 
				// right node of the unbalancing node
				right.left = unBalancingNode.right;
				if (unBalancingNode.right != null)
				{
					unBalancingNode.right.parent = right;
				}

				// That done, the unbalancing node can designate the current right node as it's new 
				// right
				unBalancingNode.right = right;
				// Move the right node one level down
				right.parent = unBalancingNode;

				// Attach the unbalancingNode to the right
				right = unBalancingNode;
				unBalancingNode.parent = this;

				// Adjust for the new height
				right.adjustHeight();
				right.adjustSize();
				if (right.right != null)
				{
					right.right.adjustHeight();
					right.right.adjustSize();
				}

				// left rotation
				
				if (parent != null)
				{
					if (isRightChild())
					{
						parent.right = right;
					}
					else
					{
						parent.left = right;
					}
				}
				AVLNode tempNode = right.left;

				right.parent = parent;
				parent = right;

				right.left= this;
				if (tempNode != null)
				{
					tempNode.parent = this;
				}
				right = tempNode;
			}
		}
		else if (balance == 2)
		{
			int leftBalance = left.balanceFactor();
			if (leftBalance <= 0)
			{
				// LRC - first rotate the subtree to the left then rotate right

				// left rotation of subtree

				// The node causing the imbalance
				AVLNode unBalancingNode = left.right;

				// Changing the current left node to point at it's new right - 
				// the left of the unbalancing node
				left.right = unBalancingNode.left;
				if (unBalancingNode.left != null)
				{
					unBalancingNode.left.parent = left.right;
				}

				// That done, the unbalancing node can designate it's new left - 
				// the current left node
				unBalancingNode.left = left;
				// Moving the left node one level down
				left.parent = unBalancingNode;

				// Attach the unbalancing node to the left
				left = unBalancingNode;
				unBalancingNode.parent = this;

				left.adjustHeight();
				left.adjustSize();
				if (left.left != null)
				{
					left.left.adjustHeight();
					left.left.adjustSize();
				}

				// right rotation
				
				if (parent != null)
				{
					if (isRightChild())
					{
						parent.right = left;
					}
					else
					{
						parent.left = left;
					}
				}
				AVLNode tempNode = left.right;
				left.parent = parent;
				parent = left;
				left.right = this;
				if (tempNode != null)
				{
					tempNode.parent = this;
				}
				left = tempNode;
				
			}
			else
			{
				// LLC - one right rotation needed
				
				// right rotation
				
				if (parent != null)
				{
					if (isRightChild())
					{
						parent.right = left;
					}
					else
					{
						parent.left = left;
					}
				}
				AVLNode tempNode = left.right;

				left.parent = parent;
				parent = left;

				left.right = this;

				if (tempNode != null)
				{
					tempNode.parent = this; 	
				}
				left = tempNode;
			}
		}
		// Any changes require a re-balancing of the height and size of the subtree - which, at most, changed the level and size of two nodes
		adjustSize();
		adjustHeight();
		if (parent != null)
		{
			parent.adjustSize();
			parent.adjustHeight();
		}

		// Getting the tree root to return
		AVLNode currParent = parent;
		AVLNode root = this;
		while (currParent != null)
		{
			root = currParent;
			currParent = currParent.parent;
		}

		return root;
	}
	private int balanceFactor()
	{
		int leftFactor = -1;	
		int rightFactor = -1;
		if (left != null)
			leftFactor = left.height;
		if (right != null)
			rightFactor = right.height;
		return leftFactor - rightFactor;
	}

	private int getMaxHeight()
	{
		int leftHeight = (left != null) ? left.height : 0;
		int rightHeight = (right != null) ? right.height : 0;
		return Math.max(leftHeight, rightHeight);
	}
	private void adjustSize()
	{
		int leftSize = (left != null) ? left.size : 0;
		int rightSize = (right != null) ? right.size : 0;
		size = leftSize + rightSize + 1;
	}
	private void adjustHeight()
	{
		height = getMaxHeight();
		// Adding the level this node gets for having ANY children at all
		if ((left != null) || (right != null))
		{
			height = height + 1;
		}
	}

	private boolean isRightChild()
	{
		boolean isRight = false;
		if (parent.right != null)
		{
			if (comp.compare(key, parent.right.getKey()) == 0)
			{
				isRight = true;
			}
		}
		return isRight;
	}

	/**
	 * Removes a Node which key is equal (by {@link Comparator}) to the given argument.
	 *
	 * @param key the key
	 * @return the root after deletion and rotations
	 * @author <b>Asaf Flescher, Dana Katz-Buchstav</b>
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
	 * @author <b>Asaf Flescher, Dana Katz-Buchstav</b>
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
	 * @author <b>Asaf Flescher, Dana Katz-Buchstav</b>
	 */
	public Object find(Object key) {
		return null;
	}

	/**
	 * Find K'th element in the tree.
	 *
	 * @param k is a number between 1 and {@link #size()}
	 * @return the object
	 * @author <b>Asaf Flescher, Dana Katz-Buchstav</b>
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

	/**
	 * Gets the data of the current node.
	 *
	 * @return the data
	 */
	public Object getData() {
		return this.data;
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
