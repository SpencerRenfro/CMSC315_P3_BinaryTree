import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean continueInput = true;

        while (continueInput) {
            try {
                System.out.print("Enter a binary tree: ");
                String userInput = input.nextLine();

                System.out.println("Parsing tree: " + userInput);

                // Create a new instance of BinaryTree
                BinaryTree originalTree = new BinaryTree(userInput);

                System.out.println("Tree created successfully");

                // Print the indented tree
                originalTree.printIndentedTree();

                System.out.println("Tree printed successfully");

                // Check if it's a BST and if it's balanced
                boolean isBST = originalTree.isBinarySearchTree();
                boolean isBalanced = originalTree.isBalanced();

                // Categorize the tree
                if (!isBST) {
                    System.out.println("It is not a binary search tree");

                    // Create a balanced BST with the same values
                    BinaryTree balancedTree = new BinaryTree(originalTree.getValues());
                    balancedTree.printIndentedTree();

                    // Display heights
                    System.out.println("Original tree has height " + originalTree.getHeight());
                    System.out.println("Balanced tree has height " + balancedTree.getHeight());
                } else if (isBalanced) {
                    System.out.println("It is a balanced binary search tree");
                } else {
                    System.out.println("It is a binary search tree but it is not balanced");

                    // Create a balanced BST with the same values
                    BinaryTree balancedTree = new BinaryTree(originalTree.getValues());
                    balancedTree.printIndentedTree();

                    // Display heights
                    System.out.println("Original tree has height " + originalTree.getHeight());
                    System.out.println("Balanced tree has height " + balancedTree.getHeight());
                }
            } catch (InvalidTreeSyntaxException e) {
                System.out.println(e.getMessage());
            }

            // Ask if the user wants to continue
            System.out.print("More trees? Y or N: ");
            String response = input.nextLine().trim().toUpperCase();
            continueInput = response.equals("Y");
        }

        input.close();
    }
}