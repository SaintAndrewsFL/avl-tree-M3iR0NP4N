class AVLNode extends Node {
    int height;

    public AVLNode(int item) {
        super(item);
        height = 1; // new node is initially added at leaf
    }
}

class AVLTree extends BinarySearchTree {
    // Get height of a node
    private int height(AVLNode node) {
        return (node == null) ? 0 : node.height;
    }

    // Calculate balance factor of node
    private int getBalance(AVLNode node) {
        return (node == null) ? 0 : height((AVLNode) node.left) - height((AVLNode) node.right);
    }

    // Update node height using heights of its children
    private void updateHeight(AVLNode node) {
        if (node != null) {
            node.height = Math.max(height((AVLNode) node.left), height((AVLNode) node.right)) + 1;
        }
    }

    // Right rotate subtree rooted with y
    private AVLNode rotateRight(AVLNode y) {
        AVLNode x = (AVLNode) y.left;
        AVLNode T2 = (AVLNode) x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights
        updateHeight(y);
        updateHeight(x);

        // Return new root
        return x;
    }

    // Left rotate subtree rooted with x
    private AVLNode rotateLeft(AVLNode x) {
        AVLNode y = (AVLNode) x.right;
        AVLNode T2 = (AVLNode) y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        // Update heights
        updateHeight(x);
        updateHeight(y);

        // Return new root
        return y;
    }

    // Insert a key into the AVL tree
    @Override
    public void insert(int key) {
        root = insertRec((AVLNode) root, key);
    }

    // Recursive function to insert a key and balance the tree
    private AVLNode insertRec(AVLNode node, int key) {
        // Perform the normal BST insertion
        if (node == null) {
            return new AVLNode(key);
        }
        if (key < node.key) {
            node.left = insertRec((AVLNode) node.left, key);
        } else if (key > node.key) {
            node.right = insertRec((AVLNode) node.right, key);
        } else {
            // Duplicate keys not allowed
            return node;
        }

        // Update height of this ancestor node
        updateHeight(node);

        // Get the balance factor to check whether this node became unbalanced
        int balance = getBalance(node);

        // Left Left Case
        if (balance > 1 && key < ((AVLNode) node.left).key) {
            return rotateRight(node);
        }
        // Right Right Case
        if (balance < -1 && key > ((AVLNode) node.right).key) {
            return rotateLeft(node);
        }
        // Left Right Case
        if (balance > 1 && key > ((AVLNode) node.left).key) {
            node.left = rotateLeft((AVLNode) node.left);
            return rotateRight(node);
        }
        // Right Left Case
        if (balance < -1 && key < ((AVLNode) node.right).key) {
            node.right = rotateRight((AVLNode) node.right);
            return rotateLeft(node);
        }

        // Return the (unchanged) node pointer
        return node;
    }

    // Delete a key from the AVL tree
    @Override
    public void delete(int key) {
        root = deleteRec((AVLNode) root, key);
    }

    // Recursive function to delete a key and balance the tree
    private AVLNode deleteRec(AVLNode node, int key) {
        // STEP 1: Perform standard BST delete
        if (node == null)
            return node;

        if (key < node.key) {
            node.left = deleteRec((AVLNode) node.left, key);
        } else if (key > node.key) {
            node.right = deleteRec((AVLNode) node.right, key);
        } else {
            // Node with only one child or no child
            if (node.left == null || node.right == null) {
                AVLNode temp = (node.left != null) ? (AVLNode) node.left : (AVLNode) node.right;
                // No child case
                if (temp == null) {
                    node = null;
                } else { // One child case
                    node = temp;
                }
            } else {
                // Node with two children: Get the inorder successor (smallest in the right subtree)
                AVLNode temp = minValueNode((AVLNode) node.right);
                node.key = temp.key;
                // Delete the inorder successor
                node.right = deleteRec((AVLNode) node.right, temp.key);
            }
        }

        // If the tree had only one node then return
        if (node == null)
            return node;

        // STEP 2: Update height of the current node
        updateHeight(node);

        // STEP 3: Get the balance factor of this node
        int balance = getBalance(node);

        // If node becomes unbalanced, then there are 4 cases

        // Left Left Case
        if (balance > 1 && getBalance((AVLNode) node.left) >= 0)
            return rotateRight(node);

        // Left Right Case
        if (balance > 1 && getBalance((AVLNode) node.left) < 0) {
            node.left = rotateLeft((AVLNode) node.left);
            return rotateRight(node);
        }

        // Right Right Case
        if (balance < -1 && getBalance((AVLNode) node.right) <= 0)
            return rotateLeft(node);

        // Right Left Case
        if (balance < -1 && getBalance((AVLNode) node.right) > 0) {
            node.right = rotateRight((AVLNode) node.right);
            return rotateLeft(node);
        }

        return node;
    }

    // Utility function to find the node with the minimum key value
    private AVLNode minValueNode(AVLNode node) {
        AVLNode current = node;
        while (current.left != null) {
            current = (AVLNode) current.left;
        }
        return current;
    }
}
