import java.util.Scanner;
import java.util.ArrayList;

public class SimpleTest {
    public static void main(String[] args) {
        try {
            // Test with a sample input
            String input = "(53 (28 (11 * *) (41 * *)) (83 (67 * *) *))";
            System.out.println("Testing with input: " + input);

            SimpleBinaryTree tree = new SimpleBinaryTree(input);
            System.out.println("Tree created successfully");

            System.out.println("\nIndented tree:");
            tree.printIndentedTree();

            boolean isBST = tree.isBinarySearchTree();
            boolean isBalanced = tree.isBalanced();

            System.out.println("\nTree analysis:");
            System.out.println("Is BST: " + isBST);
            System.out.println("Is Balanced: " + isBalanced);

            if (isBST && isBalanced) {
                System.out.println("It is a balanced binary search tree");
            } else if (isBST) {
                System.out.println("It is a binary search tree but it is not balanced");

                // Create a balanced BST with the same values
                SimpleBinaryTree balancedTree = new SimpleBinaryTree(tree.getValues());
                System.out.println("\nBalanced tree:");
                balancedTree.printIndentedTree();

                System.out.println("Original tree has height " + tree.getHeight());
                System.out.println("Balanced tree has height " + balancedTree.getHeight());
            } else {
                System.out.println("It is not a binary search tree");

                // Create a balanced BST with the same values
                SimpleBinaryTree balancedTree = new SimpleBinaryTree(tree.getValues());
                System.out.println("\nBalanced tree:");
                balancedTree.printIndentedTree();

                System.out.println("Original tree has height " + tree.getHeight());
                System.out.println("Balanced tree has height " + balancedTree.getHeight());
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
