import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class SimpleBinaryTree {
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

    private TreeNode root;
    private ArrayList<Integer> values = new ArrayList<>();

    /**
     * Constructor that accepts a string containing the preorder representation of a binary tree
     * @param preorderStr the preorder representation of a binary tree
     * @throws Exception if the input has invalid syntax
     */
    public SimpleBinaryTree(String preorderStr) throws Exception {
        System.out.println("Parsing tree: " + preorderStr);

        if (preorderStr == null || preorderStr.trim().isEmpty()) {
            throw new Exception("Invalid syntax: Incomplete Tree");
        }

        try {
            // Parse the preorder string and build the tree
            int[] index = {0};
            root = buildTree(preorderStr, index);

            // Collect all values in the tree
            collectValues(root, values);

            System.out.println("Tree built successfully with " + values.size() + " nodes");
        } catch (Exception e) {
            throw new Exception("Invalid tree syntax: " + e.getMessage());
        }
    }

    /**
     * Constructor that accepts an array list of integers and constructs a balanced binary search tree
     * @param values the values to include in the tree
     */
    public SimpleBinaryTree(ArrayList<Integer> values) {
        this.values = new ArrayList<>(values);
        Collections.sort(this.values);
        root = buildBalancedBST(this.values, 0, this.values.size() - 1);
    }

    /**
     * Recursively builds a tree from a preorder string
     */
    private TreeNode buildTree(String s, int[] index) {
        // Create a simple recursive descent parser for the preorder representation
        // Format: (value left_subtree right_subtree) or *

        // Skip whitespace
        while (index[0] < s.length() && Character.isWhitespace(s.charAt(index[0]))) {
            index[0]++;
        }

        if (index[0] >= s.length()) {
            return null;
        }

        // Handle null node
        if (s.charAt(index[0]) == '*') {
            index[0]++; // Skip '*'
            return null;
        }

        // Handle node with value
        if (s.charAt(index[0]) == '(') {
            index[0]++; // Skip '('

            // Skip whitespace
            while (index[0] < s.length() && Character.isWhitespace(s.charAt(index[0]))) {
                index[0]++;
            }

            // Parse the value
            StringBuilder valueStr = new StringBuilder();
            while (index[0] < s.length() && Character.isDigit(s.charAt(index[0]))) {
                valueStr.append(s.charAt(index[0]));
                index[0]++;
            }

            if (valueStr.length() == 0) {
                throw new IllegalArgumentException("Expected a number at position " + index[0]);
            }

            int value = Integer.parseInt(valueStr.toString());
            TreeNode node = new TreeNode(value);

            // Skip whitespace
            while (index[0] < s.length() && Character.isWhitespace(s.charAt(index[0]))) {
                index[0]++;
            }

            // Parse left child
            node.left = buildTree(s, index);

            // Skip whitespace
            while (index[0] < s.length() && Character.isWhitespace(s.charAt(index[0]))) {
                index[0]++;
            }

            // Parse right child
            node.right = buildTree(s, index);

            // Skip whitespace
            while (index[0] < s.length() && Character.isWhitespace(s.charAt(index[0]))) {
                index[0]++;
            }

            // Check for closing parenthesis
            if (index[0] >= s.length() || s.charAt(index[0]) != ')') {
                throw new IllegalArgumentException("Expected ')' at position " + index[0]);
            }

            index[0]++; // Skip ')'
            return node;
        }

        throw new IllegalArgumentException("Unexpected character at position " + index[0] + ": " + s.charAt(index[0]));
    }

    /**
     * Builds a balanced BST from a sorted array list of integers
     */
    private TreeNode buildBalancedBST(ArrayList<Integer> sortedValues, int start, int end) {
        if (start > end) {
            return null;
        }

        int mid = (start + end) / 2;
        TreeNode node = new TreeNode(sortedValues.get(mid));

        node.left = buildBalancedBST(sortedValues, start, mid - 1);
        node.right = buildBalancedBST(sortedValues, mid + 1, end);

        return node;
    }

    /**
     * Collects all values in the tree into an array list
     */
    private void collectValues(TreeNode node, ArrayList<Integer> values) {
        if (node == null) {
            return;
        }

        collectValues(node.left, values);
        values.add(node.value);
        collectValues(node.right, values);
    }

    /**
     * Outputs the binary tree in indented form
     */
    public void printIndentedTree() {
        printIndentedTree(root, 0);
    }

    /**
     * Helper method to print the tree with indentation
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
     */
    public boolean isBinarySearchTree() {
        return isBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /**
     * Helper method to check if a tree is a BST
     */
    private boolean isBST(TreeNode node, int min, int max) {
        if (node == null) {
            return true;
        }

        if (node.value <= min || node.value >= max) {
            return false;
        }

        return isBST(node.left, min, node.value) && isBST(node.right, node.value, max);
    }

    /**
     * Returns whether the tree is balanced
     */
    public boolean isBalanced() {
        return isBalancedHelper(root) != -1;
    }

    /**
     * Helper method to check if a tree is balanced
     */
    private int isBalancedHelper(TreeNode node) {
        if (node == null) {
            return 0;
        }

        int leftHeight = isBalancedHelper(node.left);
        if (leftHeight == -1) {
            return -1;
        }

        int rightHeight = isBalancedHelper(node.right);
        if (rightHeight == -1) {
            return -1;
        }

        if (Math.abs(leftHeight - rightHeight) > 1) {
            return -1;
        }

        return Math.max(leftHeight, rightHeight) + 1;
    }

    /**
     * Returns the height of the tree
     */
    public int getHeight() {
        return getHeight(root);
    }

    /**
     * Helper method to get the height of a tree
     */
    private int getHeight(TreeNode node) {
        if (node == null) {
            return 0;
        }

        return Math.max(getHeight(node.left), getHeight(node.right)) + 1;
    }

    /**
     * Returns an array list of the values in the tree
     */
    public ArrayList<Integer> getValues() {
        return new ArrayList<>(values);
    }
}
