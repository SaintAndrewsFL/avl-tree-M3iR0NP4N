class AVLNode extends Node {
    int height;

    public AVLNode(int item) {
        super(item);
        height = 1;
    }
}

class AVLTree extends BinarySearchTree {
    // Get height of node
    private int height(AVLNode node) {
        if (node == null) return 0;
        return node.height;
    }

    // Get balance factor
    private int getBalance(AVLNode node) {
        if (node == null) return 0;
        return height((AVLNode)node.left) - height((AVLNode)node.right);
    }

    // Update height
    private void updateHeight(AVLNode node) {
        if (node == null) return;
        node.height = Math.max(height((AVLNode)node.left), height((AVLNode)node.right)) + 1;
    }

    // Rotate right
    private AVLNode rotateRight(AVLNode y) {
        // TODO: Implement right rotation
        return null;
    }

    // Rotate left
    private AVLNode rotateLeft(AVLNode x) {
        // TODO: Implement left rotation
        return null;
    }

    // Insert
    @Override
    public void insert(int key) {
        root = insertRec((AVLNode)root, key);
    }

    private AVLNode insertRec(AVLNode node, int key) {
        // TODO: Implement AVL insert with balancing
        return null;
    }

    // Delete
    @Override
    public void delete(int key) {
        root = deleteRec((AVLNode)root, key);
    }

    private AVLNode deleteRec(AVLNode node, int key) {
        // TODO: Implement AVL delete with balancing
        return null;
    }
}