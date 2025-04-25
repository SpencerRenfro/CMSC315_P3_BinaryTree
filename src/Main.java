import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws InvalidTreeSyntaxException {
        Scanner input = new Scanner(System.in);
        boolean continueInput = true;

        while (continueInput) {
            try {
                System.out.print("Enter a binary tree: ");
                String userInput = input.nextLine();

                //Create a new instance of BinaryTree, this automatically checks for valid input
                // and throws an exception if the input is invalid

                BinaryTree binaryTree = new BinaryTree(userInput);
                // if no errors are thrown from construction, then the input is valid
                System.out.println("Tree created successfully");

                // print indented tree
                binaryTree.printIndentedTree();

                // checks  if binaryTree is a binary tree
                // checks  if it is balanced
                // checks  if BSt is true and balanced != true, make a new binary search tree that is balanced
                boolean isBST = binaryTree.isBinarySearchTree();
                boolean isBalanced = binaryTree.isBalanced(binaryTree.getRoot());
                if(!isBST){
                    System.out.println("It is not a binary search tree");
                } else if(isBalanced){
                    System.out.println("It is a balanced binary search tree");

                } else {
                    System.out.println("It is a binary search tree but it is not balanced");
                    BinaryTree balancedTree = new BinaryTree(binaryTree.getTreeValues());
                    balancedTree.printIndentedTree();
                    System.out.println("Original tree has height " + binaryTree.getHeight(binaryTree.getRoot()));
                    System.out.println("Balanced tree has height " + balancedTree.getHeight(balancedTree.getRoot()));
                }

            } catch(InvalidTreeSyntaxException e) {
                System.out.println(e.getMessage());
            }

            // Prompt the user if they want more trees
            System.out.print("More trees? Y or N: ");
            String response = input.nextLine().trim().toUpperCase();
            continueInput = response.equals("Y");
        }
        input.close();
    }
}