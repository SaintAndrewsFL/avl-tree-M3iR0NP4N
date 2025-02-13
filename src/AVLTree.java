class AVLNode extends Node {
    int height;

    public AVLNode(int item) {
        super(item);
        height = 1;
    }
}

class AVLTree extends BinarySearchTree {
    private int height(AVLNode node) {
        return (node == null) ? 0 : node.height;
    }

    private int getBalance(AVLNode node) {
        return (node == null) ? 0 : height((AVLNode) node.left) - height((AVLNode) node.right);
    }

    private void updateHeight(AVLNode node) {
        if (node != null) node.height = Math.max(height((AVLNode) node.left), height((AVLNode) node.right)) + 1;
    }

    private AVLNode rotateRight(AVLNode y) {
        AVLNode x = (AVLNode) y.left;
        AVLNode T2 = (AVLNode) x.right;
        x.right = y;
        y.left = T2;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    private AVLNode rotateLeft(AVLNode x) {
        AVLNode y = (AVLNode) x.right;
        AVLNode T2 = (AVLNode) y.left;
        y.left = x;
        x.right = T2;
        updateHeight(x);
        updateHeight(y);
        return y;
    }

    @Override
    public void insert(int key) root = insertRec((AVLNode) root, key);

    private AVLNode insertRec(AVLNode node, int key) {
        if (node == null) return new AVLNode(key);
        if (key < node.key) node.left = insertRec((AVLNode) node.left, key);
        else if (key > node.key) node.right = insertRec((AVLNode) node.right, key);
        else return node;
        updateHeight(node);
        int balance = getBalance(node);
        if (balance > 1 && key < ((AVLNode) node.left).key) return rotateRight(node);
        if (balance < -1 && key > ((AVLNode) node.right).key) return rotateLeft(node);
        if (balance > 1 && key > ((AVLNode) node.left).key) {
            node.left = rotateLeft((AVLNode) node.left);
            return rotateRight(node);
        }
        if (balance < -1 && key < ((AVLNode) node.right).key) {
            node.right = rotateRight((AVLNode) node.right);
            return rotateLeft(node);
        }
        return node;
    }

    @Override
    public void delete(int key) root = deleteRec((AVLNode) root, key);

    private AVLNode deleteRec(AVLNode node, int key) {
        if (node == null) return node;
        if (key < node.key) node.left = deleteRec((AVLNode) node.left, key);
        else if (key > node.key) node.right = deleteRec((AVLNode) node.right, key);
        else {
            if (node.left == null || node.right == null) {
                AVLNode temp = (node.left != null) ? (AVLNode) node.left : (AVLNode) node.right;
                if (temp == null) node = null;
                else node = temp;
            } else {
                AVLNode temp = minValueNode((AVLNode) node.right);
                node.key = temp.key;
                node.right = deleteRec((AVLNode) node.right, temp.key);
            }
        }
        if (node == null) return node;
        updateHeight(node);
        int balance = getBalance(node);
        if (balance > 1 && getBalance((AVLNode) node.left) >= 0)
            return rotateRight(node);
        if (balance > 1 && getBalance((AVLNode) node.left) < 0) {
            node.left = rotateLeft((AVLNode) node.left);
            return rotateRight(node);
        }
        if (balance < -1 && getBalance((AVLNode) node.right) <= 0) return rotateLeft(node);
        if (balance < -1 && getBalance((AVLNode) node.right) > 0) {
            node.right = rotateRight((AVLNode) node.right);
            return rotateLeft(node);
        }
        return node;
    }

    private AVLNode minValueNode(AVLNode node) {
        AVLNode current = node;
        while (current.left != null) current = (AVLNode) current.left;
        return current;
    }
}
