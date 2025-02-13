public class Main {
    public static void main(String[] args) {
        AVLTree avl = new AVLTree();
        avl.insert(10);
        avl.insert(20);
        avl.insert(30);
        avl.insert(40);
        avl.insert(50);
        avl.insert(25);

        System.out.println("Inorder traversal of the constructed AVL tree is:");
        avl.inorder();

        avl.delete(40);
        System.out.println("Inorder traversal after deletion of 40:");
        avl.inorder();
    }
}