

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

	// Recursively adds an object to the tree
	private AVLNode recursiveAdd(Object key, Object data)
	{
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
		// This is where the node actually gets added
		if (nodeToUse == null)
		{
			nodeToUse = new AVLNode(key, data, this.comp, this);	

			// If left and right are null, then we're adding a new 'level' to the tree and need to update the height of everything in the tree
			if ((left == null) && (right == null))
			{
				newLevelAdded = true;
			}

			// Put the new node in the tree itself
	                if (compResult <= 0)
			{
				left = nodeToUse;

                                // Setting inorder parameters
                                nodeToUse.succ = this;
				nodeToUse.pred = pred;
				pred = nodeToUse;
                                
				if (nodeToUse.pred != null)
				{
					nodeToUse.pred.succ = nodeToUse;
				}
			}
			else
			{
				right = nodeToUse;

                                // Setting inorder parameters
                                nodeToUse.pred = this;
				nodeToUse.succ = succ;
				succ = nodeToUse;

				if (nodeToUse.succ != null)
				{
					nodeToUse.succ.pred = nodeToUse;
				}
				
			}
                        nodeToUse.parent = this;
		}
		else
		{
			nodeToUse = nodeToUse.recursiveAdd(key, data);
			if (compResult <= 0)
			{
				left = nodeToUse;
			}
			else
			{
				right = nodeToUse;
			}
		}

		if (newLevelAdded)
		{
			height = 1;
			reCalcHeight(parent);
		}

		// Adjusting the size 
		return balanceTree();
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
		AVLNode root = recursiveAdd(key, data);
		while (root.parent != null)
		{
			root = root.parent;
		}
		return root;
	}

	// Recalculates startNode's height and recalculates and changes the heights of it's ancestors until no further change is needed 
	private void reCalcHeight(AVLNode startNode)
	{
		boolean needToChangeHeight = true;
		AVLNode currParent = startNode;
		// Run until we either hit the root or the change stops reverbating - i.e, the added node doesn't change the height of the level
		while ((currParent != null) && (needToChangeHeight))
		{
			int oldHeight = currParent.height;
			currParent.adjustHeight();
			if (oldHeight != currParent.height)
			{
				currParent.height = oldHeight;
				currParent = currParent.parent;
			}	
			else
			{
				needToChangeHeight = false;
			}
		}
	}

	// Checks the tree rooted in this node for balance and rotates appropriately. 
	// If we're doing things right - and we are - balancing will never require
	// more two rotations. Returns the new root of the tree(which is, at most, two
	// levels down)
        private AVLNode balanceTree()
        {
		AVLNode ret = this;
		adjustSize();
		adjustHeight();
                //Check if the tree is still balanced
		int balance = balanceFactor();

		// Four cases for balancing
		if (balance == -2)
		{
			int rightBalance = right.balanceFactor();
			if (rightBalance <= 0)
			{
				// RRC - one left rotation needed

				// left rotation

				rotateLeft(this);
			}
			else
			{
				// RLC - first rotate the subtree to the right, then rotate to the left

				// right rotation of subtree
				rotateRight(right);
				// left rotation

				rotateLeft(this);
			}
			ret = parent;
		}
		else if (balance == 2)
		{
			int leftBalance = left.balanceFactor();
			if (leftBalance <= 0)
			{
				// LRC - first rotate the subtree to the left then rotate right

				// left rotation of subtree
				rotateLeft(left);

				// right rotation

				rotateRight(this);
			}
			else
			{
				// LLC - one right rotation needed

				// right rotation

				rotateRight(this);
			}
			ret = parent;
		}
		return ret;
        }

	// Calculates the balance of the tree rooted in this node
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

	// Rotates the tree rooted in 'root' to the right - i.e, makes root the right child of it's left child
	private void rotateRight(AVLNode root)
	{
		if (root.left != null)
		{
			AVLNode newRoot = root.left;
			AVLNode dataToTransfer = newRoot.right;
			root.left = dataToTransfer;
			if (dataToTransfer != null)
			{
				dataToTransfer.parent = root;
			}
			
			newRoot.right = root;
			newRoot.parent = root.parent;
			if (root.parent != null)
			{
				if (root.isRightChild())
				{
					root.parent.right = newRoot;
				}
				else
				{
					root.parent.left = newRoot;
				}
			}
			root.parent = newRoot;

			root.adjustHeight();
			root.adjustSize();
			newRoot.adjustSize();
			newRoot.adjustHeight();
		}
	}

	// Rotates the tree rooted in 'root' to the left - i.e, makes root the left child of it's right child
	private void rotateLeft(AVLNode root)
	{
		if (root.right != null)
		{
			AVLNode newRoot = root.right;
			AVLNode dataToTransfer = newRoot.left;
			root.right = dataToTransfer;
			if (dataToTransfer != null)
			{
				dataToTransfer.parent = root;
			}

			newRoot.left = root;
			newRoot.parent = root.parent;
			if (root.parent != null)
			{
				if (root.isRightChild())
				{
					root.parent.right = newRoot;
				}
				else
				{
					root.parent.left = newRoot;
				}
			}
			root.parent = newRoot;
			root.adjustHeight();
			root.adjustSize();
			newRoot.adjustSize();
			newRoot.adjustHeight();
		}
	}

	// The following two functions are essentially free actions that can only correct a node - therefore they may be overused

	// Recalculates the size of the tree rooted in this node
	private void adjustSize()
	{
		int leftSize = (left != null) ? left.size : 0;
		int rightSize = (right != null) ? right.size : 0;
		size = leftSize + rightSize + 1;
	}
	// Recalculates the size of the tree rooted in this node
	private void adjustHeight()
	{
		int leftHeight = (left != null) ? left.height : 0;
		int rightHeight = (right != null) ? right.height : 0;
		height = Math.max(leftHeight, rightHeight);
		// Adding the level this node gets for having ANY children at all
		if ((left != null) || (right != null))
		{
			height = height + 1;
		}
	}

	// Returns if the node is a right child or not - onus is on the caller to make sure
	// it's not calling it with a null parent. If it is called as such, it is entirely appropriate
	// for it to throw an exception
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


	// Recursively removes a node from the tree and returns the new root of it, balanced
	private AVLNode removeRecursive(Object key)
	{
		// Default value to return is the unchanged node
		AVLNode ret = this;
		int compareResult = comp.compare(key, this.key);
                switch (compareResult)
                {
                    // Removing this item
                    case 0:
                        // Simple removal
                        if (left == null || right == null)
                        {
                        	// If item is a leaf
	                        if (left == null && right == null)
        	                {
					// The new root of the tree is null - there's nothing beneath the deleted node
				    	ret = null;
                        	}
				else
				{
					// The new root of the tree is it's single child
					ret = (left != null) ? left : right;
					ret.parent = parent;
				}
                        	// keeping inorder in order
				if (pred != null)
				{
                	       	   	pred.succ = succ;
				}

				if (succ != null)
				{
                	        	succ.pred = pred;
				}
                        }
                        else
                        {
                            // The new root of the tree is it's predecessor
			    AVLNode oldPred = pred;
			    // Guaranteed to be a simple removal - predecessor by definition has no right subtree
			    AVLNode newTree = this.left.removeRecursive(pred.key);


			    // Hooking up the new left child
			    oldPred.left = newTree;
			    if (newTree != null)
			    {
				    newTree.parent = oldPred;
			    }

			    // Hooking up the parent to point to it's new child. This is neccesary because we balance
			    // the tree on every recursive call, so the tree already has to be properly hooked up, instead
			    // of waiting for the hooking up to occur one call up.
			    if (parent != null)
			    {
				    if (isRightChild())
				    {
					    parent.right = oldPred;
				    }
				    else
				    {
					    parent.left = oldPred;
				    }
			    }

			    // Setting the inorder parameters
			    oldPred.succ = succ;
			    if (succ != null)
			    {
				    succ.pred = oldPred;
			    }
			    if (oldPred.pred != null)
			    {
				    oldPred.pred.succ = oldPred;
			    }

			    // Hooking up the right child
			    oldPred.right = right;
			    if (right != null)
			    {
				    right.parent = oldPred;
			    }

			    // Hooking up the new tree to it's parent
			    oldPred.parent = parent;
			    ret = oldPred;
                        }
                        break;
                    case -1:
                        if (left != null)
			{
			    // The key is smaller than the current node, look left
                            left = left.removeRecursive(key);
			}
                        break;
                    case 1:
                        if (right != null)
			{
			    // The key is larger than the current node, look right
                            right = right.removeRecursive(key);
			}
                        break;
                }

		if (ret != null)
		{
			// We balance the tree on every call - balancing costs very little, and it's
			// best to balance as close to the source of the disruption - node removal - as possible
			ret = ret.balanceTree();
		}
		return ret;
	}
	/**
	 * Removes a Node which key is equal (by {@link Comparator}) to the given argument.
	 *
	 * @param key the key
	 * @return the root after deletion and rotations
	 * @author <b>Asaf Flescher, Dana Katz-Buchstav</b>
	 */
	public AVLNode remove(Object key) {
                AVLNode ret = removeRecursive(key);
		// If there's anything to return - i.e, we didn't delete the sole remaining node - then we
		// balance each parent before returning the new root
		if (ret != null)
		{
			while (ret.parent != null)
			{
				ret = ret.parent;
				ret = ret.balanceTree();
			}
		}
		return ret;
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

            // Adding elements from k to h to list
	    for (int i = k; i <= h && curr != null; ++i)
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
		int result = comp.compare(key, this.key);
		// Standard BST find

		if (result ==0)
		{
			return this.data;
		}
		else if (result < 0)
		{
			if (left != null)
			{
				return left.find(key);
			}
			else
			{
				return null;
			}
		}
		else
		{
			if (right != null)
			{
				return right.find(key);
			}
			else
			{
				return null;
			}
		}
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
                // If there are no elements to the left, then the root is the
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
			try
			{
				this.left.inOrderToString(sb);
			}
			catch(StackOverflowError err)
			{
				System.out.println("Error in trying to write left subtree of " + this.key + " " + this.left.key);
			}
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
			try
			{
				this.right.inOrderToString(sb);
			}
			catch(StackOverflowError err)
			{
				System.out.println("Error in trying to write right subtree of " + this.key);
			}
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
