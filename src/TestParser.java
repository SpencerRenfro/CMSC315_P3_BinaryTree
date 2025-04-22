import java.util.ArrayList;

public class TestParser {
    public static void main(String[] args) {
        try {
            String input = "(53 (28 (11 * *) (41 * *)) (83 (67 * *) *))";
            System.out.println("Testing with input: " + input);

            BinaryTree tree = new BinaryTree(input);
            System.out.println("Tree created successfully");

            tree.printIndentedTree();
            System.out.println("Tree printed successfully");

            boolean isBST = tree.isBinarySearchTree();
            boolean isBalanced = tree.isBalanced();

            System.out.println("Is BST: " + isBST);
            System.out.println("Is Balanced: " + isBalanced);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
