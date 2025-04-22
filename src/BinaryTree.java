import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public final class BinaryTree {

    // Inner class for tree nodes
    private static class TreeNode {
        int value;
        TreeNode left;
        TreeNode right;

        TreeNode(int value) {
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    private final ArrayList<String> errorMessages = new ArrayList<>();
    private final ArrayList<Integer> treeValues = new ArrayList<>();
    private String userInput;
    private TreeNode root;

    /**
     * Constructor that accepts a string containing the preorder representation of a binary tree
     * and constructs a binary tree
     * @param userInput the preorder representation of a binary tree
     * @throws InvalidTreeSyntaxException if the input has invalid syntax
     */
    public BinaryTree(String userInput) throws InvalidTreeSyntaxException {
        // remove all whitespace
        userInput = userInput.replaceAll("\\s+", "");
        this.userInput = userInput;

        if (this.isValidInput(userInput)) {
            // Parse the string and construct the binary tree
            this.root = buildTreeFromPreorder(userInput);
        } else {
            throw new InvalidTreeSyntaxException(String.join("\n", errorMessages));
        }
    }

    /**
     * Constructor that accepts an array list of integers and constructs a balanced binary search tree
     * @param values the values to include in the tree
     */
    public BinaryTree(ArrayList<Integer> values) {
        this.treeValues.addAll(values);
        // Sort the values for BST construction
        Collections.sort(this.treeValues);
        // Build a balanced BST from the sorted values
        this.root = buildBalancedBST(this.treeValues, 0, this.treeValues.size() - 1);
    }

    /**
     * Builds a balanced BST from a sorted array list of integers
     * @param sortedValues the sorted values
     * @param start the start index
     * @param end the end index
     * @return the root of the balanced BST
     */
    private TreeNode buildBalancedBST(ArrayList<Integer> sortedValues, int start, int end) {
        // Base case
        if (start > end) {
            return null;
        }

        // Get the middle element and make it the root
        int mid = (start + end) / 2;
        TreeNode node = new TreeNode(sortedValues.get(mid));

        // Recursively construct the left and right subtrees
        node.left = buildBalancedBST(sortedValues, start, mid - 1);
        node.right = buildBalancedBST(sortedValues, mid + 1, end);

        return node;
    }

    /**
     * Builds a binary tree from its preorder representation
     * @param preorder the preorder representation of the tree
     * @return the root of the constructed tree
     */
    private TreeNode buildTreeFromPreorder(String preorder) {
        // Create a wrapper class to hold the current index
        int[] index = {0};
        return parsePreorder(preorder, index);
    }

    /**
     * Helper method to parse the preorder representation
     * @param preorder the preorder representation
     * @param index the current index in the string
     * @return the root of the parsed tree
     */
    private TreeNode parsePreorder(String preorder, int[] index) {
        // Skip whitespace
        while (index[0] < preorder.length() && Character.isWhitespace(preorder.charAt(index[0]))) {
            index[0]++;
        }

        // Check if we've reached the end of the string
        if (index[0] >= preorder.length()) {
            return null;
        }

        // Check for asterisk (null node)
        if (preorder.charAt(index[0]) == '*') {
            index[0]++; // Skip the asterisk
            return null;
        }

        // Check for opening parenthesis
        if (preorder.charAt(index[0]) == '(') {
            index[0]++; // Skip the opening parenthesis

            // Skip whitespace
            while (index[0] < preorder.length() && Character.isWhitespace(preorder.charAt(index[0]))) {
                index[0]++;
            }

            // Check for asterisk (null node) right after opening parenthesis
            if (index[0] < preorder.length() && preorder.charAt(index[0]) == '*') {
                index[0]++; // Skip the asterisk

                // Skip whitespace
                while (index[0] < preorder.length() && Character.isWhitespace(preorder.charAt(index[0]))) {
                    index[0]++;
                }

                // Skip the closing parenthesis
                if (index[0] < preorder.length() && preorder.charAt(index[0]) == ')') {
                    index[0]++;
                }

                return null;
            }

            // Parse the value
            StringBuilder valueStr = new StringBuilder();
            while (index[0] < preorder.length() && Character.isDigit(preorder.charAt(index[0]))) {
                valueStr.append(preorder.charAt(index[0]));
                index[0]++;
            }

            if (valueStr.length() == 0) {
                // No value found, this is unexpected
                System.out.println("Error: Expected a value at position " + index[0]);
                return null;
            }

            int value = Integer.parseInt(valueStr.toString());
            TreeNode node = new TreeNode(value);

            // Skip whitespace
            while (index[0] < preorder.length() && Character.isWhitespace(preorder.charAt(index[0]))) {
                index[0]++;
            }

            // Parse left child
            node.left = parsePreorder(preorder, index);

            // Skip whitespace
            while (index[0] < preorder.length() && Character.isWhitespace(preorder.charAt(index[0]))) {
                index[0]++;
            }

            // Parse right child
            node.right = parsePreorder(preorder, index);

            // Skip whitespace
            while (index[0] < preorder.length() && Character.isWhitespace(preorder.charAt(index[0]))) {
                index[0]++;
            }

            // Skip the closing parenthesis
            if (index[0] < preorder.length() && preorder.charAt(index[0]) == ')') {
                index[0]++;
            } else {
                System.out.println("Error: Expected closing parenthesis at position " + index[0]);
            }

            return node;
        }

        // If we get here, we have a value without parentheses
        StringBuilder valueStr = new StringBuilder();
        while (index[0] < preorder.length() && Character.isDigit(preorder.charAt(index[0]))) {
            valueStr.append(preorder.charAt(index[0]));
            index[0]++;
        }

        if (valueStr.length() > 0) {
            return new TreeNode(Integer.parseInt(valueStr.toString()));
        }

        return null;
    }

    /**
     * Outputs the binary tree in indented form
     */
    public void printIndentedTree() {
        printIndentedTree(root, 0);
    }

    /**
     * Helper method to print the tree with indentation
     * @param node the current node
     * @param level the current level of indentation
     */
    private void printIndentedTree(TreeNode node, int level) {
        if (node == null) {
            return;
        }

        // Print the current node with proper indentation
        for (int i = 0; i < level; i++) {
            System.out.print("   ");
        }
        System.out.println(node.value);

        // Recursively print left and right subtrees
        printIndentedTree(node.left, level + 1);
        printIndentedTree(node.right, level + 1);
    }

    /**
     * Returns whether the tree is a binary search tree
     * @return true if the tree is a BST, false otherwise
     */
    public boolean isBinarySearchTree() {
        return isBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /**
     * Helper method to check if a tree is a BST
     * @param node the current node
     * @param min the minimum allowed value
     * @param max the maximum allowed value
     * @return true if the tree rooted at node is a BST, false otherwise
     */
    private boolean isBST(TreeNode node, int min, int max) {
        // An empty tree is a BST
        if (node == null) {
            return true;
        }

        // Check if the current node's value is within the allowed range
        if (node.value <= min || node.value >= max) {
            return false;
        }

        // Recursively check left and right subtrees
        return isBST(node.left, min, node.value) && isBST(node.right, node.value, max);
    }

    /**
     * Returns whether the tree is balanced
     * @return true if the tree is balanced, false otherwise
     */
    public boolean isBalanced() {
        return isBalancedHelper(root) != -1;
    }

    /**
     * Helper method to check if a tree is balanced
     * @param node the current node
     * @return the height of the tree if it's balanced, -1 otherwise
     */
    private int isBalancedHelper(TreeNode node) {
        // An empty tree is balanced
        if (node == null) {
            return 0;
        }

        // Check left subtree
        int leftHeight = isBalancedHelper(node.left);
        if (leftHeight == -1) {
            return -1;
        }

        // Check right subtree
        int rightHeight = isBalancedHelper(node.right);
        if (rightHeight == -1) {
            return -1;
        }

        // Check if the current node is balanced
        if (Math.abs(leftHeight - rightHeight) > 1) {
            return -1;
        }

        // Return the height of the tree
        return Math.max(leftHeight, rightHeight) + 1;
    }

    /**
     * Returns the height of the tree
     * @return the height of the tree
     */
    public int getHeight() {
        return getHeight(root);
    }

    /**
     * Helper method to get the height of a tree
     * @param node the current node
     * @return the height of the tree rooted at node
     */
    private int getHeight(TreeNode node) {
        if (node == null) {
            return 0;
        }

        return Math.max(getHeight(node.left), getHeight(node.right)) + 1;
    }

    /**
     * Returns an array list of the values in the tree (in-order traversal)
     * @return an array list of the values in the tree
     */
    public ArrayList<Integer> getValues() {
        ArrayList<Integer> values = new ArrayList<>();
        inOrderTraversal(root, values);
        return values;
    }

    /**
     * Helper method for in-order traversal
     * @param node the current node
     * @param values the list to store the values
     */
    private void inOrderTraversal(TreeNode node, ArrayList<Integer> values) {
        if (node == null) {
            return;
        }

        inOrderTraversal(node.left, values);
        values.add(node.value);
        inOrderTraversal(node.right, values);
    }

    /**
     * Checks if the input string has valid syntax
     * @param userInput the input string
     * @return true if the input has valid syntax, false otherwise
     */
    private boolean isValidInput(String userInput) {
        boolean isValid = true;
        Stack<Character> stack = new Stack<>();
        StringBuilder numberBuffer = new StringBuilder();
        treeValues.clear();

        for (int i = 0; i < userInput.length(); i++) {
            char c = userInput.charAt(i);
            char prevChar = i > 0 ? userInput.charAt(i - 1) : ' ';

            // Check for invalid characters
            if (!Character.isDigit(c) && c != ' ' && c != '(' && c != ')' && c != '*') {
                isValid = false;
                errorMessages.add("Invalid syntax: Data is not an Integer");
            }

            // Process digits
            if (Character.isDigit(c)) {
                numberBuffer.append(c);
                if (i == userInput.length() - 1 || !Character.isDigit(userInput.charAt(i + 1))) {
                    treeValues.add(Integer.parseInt(numberBuffer.toString()));
                    numberBuffer.setLength(0);
                }
            }

            // Process parentheses
            if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                if (stack.isEmpty()) {
                    isValid = false;
                    errorMessages.add("Invalid syntax: Missing Left Parenthesis");
                } else if (prevChar == '(') {
                    isValid = false;
                    errorMessages.add("Invalid syntax: Empty parentheses");
                } else if (stack.pop() != '(') {
                    isValid = false;
                    errorMessages.add("Invalid syntax: Missing Right Parenthesis");
                }
            }
        }

        // Check for unmatched opening parentheses
        if (!stack.isEmpty()) {
            isValid = false;
            while (!stack.isEmpty()) {
                if (stack.pop() == '(') {
                    errorMessages.add("Invalid syntax: Missing Right Parenthesis");
                }
            }
        }

        // Check for extra characters at the end
        if (!userInput.endsWith(")")) {
            isValid = false;
            errorMessages.add("Invalid syntax: Extra characters at the end");
        }

        // Check for incomplete tree
        if (userInput.isEmpty() || !userInput.startsWith("(")) {
            isValid = false;
            errorMessages.add("Invalid syntax: Incomplete Tree");
        }

        return isValid;
    }
}
