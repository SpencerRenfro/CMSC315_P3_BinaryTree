import java.util.ArrayList;
import java.util.Stack;
import java.util.Collections;

public final class BinaryTree {

    // inner class for the tree nodes, lef and right child are null by default
    public static class TreeNode {
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
    private final TreeNode root;


    /*
    A constructor that accepts a string containing the preorder representation of a binary tree from the user,
    removes all whitespace, and calls internal method to check for valid input,
    and if valid, calls another internal method to parse the string and construct the binary tree.
    */
    public BinaryTree(String userInput) throws InvalidTreeSyntaxException {
        // remove all whitespace
        userInput = userInput.replaceAll("\\s+", "");
        //System.out.println("Your input with spaces removed:" + userInput);

        if (this.isValidInput(userInput)) {
            this.root = buildTreeFromPreorder(userInput);
        } else {
            throw new InvalidTreeSyntaxException(String.join("\n", errorMessages));
        }

    }

    //this constructor accepts an array list of integers, sorts the values, constructs a balanced binary search tree

    public BinaryTree(ArrayList<Integer> values) {
        // adds values from get
        this.treeValues.addAll(values);
        //sort the values
        Collections.sort(this.treeValues);
        this.root = buildBalancedBST(this.treeValues, 0, this.treeValues.size() - 1);
    }

    //Builds a balanced binary search tree from an array of integers
    private TreeNode buildBalancedBST(ArrayList<Integer> sortedValues, int start, int end) {
       if(start > end) {
           return null;
       }
       // get the middle element, put it as the root, then recursively build the left and right subtrees
        int mid = (start + end) / 2;
       TreeNode node = new TreeNode(sortedValues.get(mid));
       node.left = buildBalancedBST(sortedValues, start, mid -1);
       node.right = buildBalancedBST(sortedValues, mid +1, end);
       return node;// returns the root of each subtree
    }
    // Builds a binary tree from the preorder user input
    private TreeNode buildTreeFromPreorder (String userInput){
        /*
         create a wrapper class to hold the current index, used for index tracking during recursion
         this cannot be a primitive type because that would be passed by value and not by reference
         and would not be updated during recursion
        */
        int[] index = {0};
        return parsePreorder(userInput, index);
    }

    private TreeNode parsePreorder (String preorder,int[] index){

        // check for end of string
        if (index[0] >= preorder.length()) return null;
        // check for asterisk
        if (preorder.charAt(index[0]) == '*') {
            index[0]++;
            return null;
        }
        if (preorder.charAt(index[0]) == '(') {
            index[0]++;
        }
        // check for asterisk right after the opening parenthesis
        if (index[0] < preorder.length() && preorder.charAt(index[0]) == '*') {
            index[0]++;
            return null;
        }

        //check for closing )
        if (index[0] < preorder.length() && preorder.charAt(index[0]) == ')') {
            index[0]++;
            return null;
        }

        // parse the value
        StringBuilder stringValue = new StringBuilder();
        while (index[0] < preorder.length() && Character.isDigit(preorder.charAt(index[0]))) {
            stringValue.append(preorder.charAt(index[0]));
            index[0]++;
        }

        int value = Integer.parseInt(stringValue.toString());
        TreeNode node = new TreeNode(value);
        node.left = parsePreorder(preorder, index);
        node.right = parsePreorder(preorder, index);

        // skip closing parenthesis
        if (index[0] < preorder.length() && preorder.charAt(index[0]) == ')') {
            index[0]++;
        }
        return node;
    }



    /*
    Recursive method to check if the tree is a binary search tree,
    this takes in the root node and the min and max values for the current subtree,
    the default values for min and max are Integer.MIN_VALUE and Integer.MAX_VALUE,
    then the max parameter is updated to the value of the current node,
    and the min parameter is updated to the value of the current node's parent node.

     */

    public boolean isBinarySearchTree() {
        return isBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private boolean isBST(TreeNode node, int min, int max) {
        // empty tree
        if(node == null) return true;

        // check if the current node's value is within the valid range
        if(node.value <= min || node.value >= max) return false;

        // recursively check the left and right subtrees
        return isBST(node.left, min, node.value) && isBST(node.right, node.value, max);
    }




    // check for valid input
    private boolean isValidInput(String userInput) {
        boolean isValid = true;
        Stack<Character> stack = new Stack<>();
        StringBuilder buffer = new StringBuilder();
        treeValues.clear();
        String message = "";
        for (int i = 0; i < userInput.length(); i++) {
            char c = userInput.charAt(i);
            char prevChar = i > 0 ? userInput.charAt(i - 1) : ' ';


            // checks for any invalid characters other than integers, spaces, parenthesis and asterisks
            if (!Character.isDigit(c) && c != ' ' && c != '(' && c != ')' && c != '*') {
                isValid = false;
                message = "Invalid syntax: Data is not an Integer";
                this.errorMessages.add(message);

            }

            //process integers
            if(Character.isDigit(c)) {
                buffer.append(c);
                // If the next character is not a digit or it's the last character, add the number to the list
                if(i == userInput.length() -1 || !Character.isDigit(userInput.charAt(i + 1))) {
                    treeValues.add(Integer.parseInt(buffer.toString()));
                    buffer.setLength(0);
                }
            }

            // processes parenthesis
            if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                if (stack.isEmpty()) {
                    isValid = false;
                    message = "Invalid syntax: Missing Left Parenthesis";
                    this.errorMessages.add(message);
                } else if (prevChar == '(') {
                    isValid = false;
                    message = "Invalid syntax: Empty parentheses";
                    this.errorMessages.add(message);
                } else if (stack.pop() != '(') {
                    isValid = false;
                    message = "Invalid syntax: Missing Right Parenthesis";
                    this.errorMessages.add(message);

                }
            }
        }
        // check for unmatched opening parenthesis after parsing the string
        if (!stack.isEmpty()) {
            char[] unmatchedCharArray = new char[stack.size()];
            isValid = false;
            for (int i = 0; i < stack.size(); i++) {
                unmatchedCharArray[i] = stack.pop();
                if (unmatchedCharArray[i] == '(') {
                    message = "Invalid syntax: Missing Right Parenthesis";
                    this.errorMessages.add(message);

                } else if (unmatchedCharArray[i] == ')') {
                    message = "Invalid syntax: Missing Left Parenthesis";
                    this.errorMessages.add(message);
                }
            }

        }

        // checks for extra characters at the end
        if (!userInput.endsWith(")")) {
            isValid = false;
            errorMessages.add("Invalid syntax: Extra characters at the end");
        }

        //ADD CHECK FOR INCOMPLETE TREE

        return isValid;
    }

    // public method that prints the indented tree
    public void printIndentedTree () {
        printIndentedTree(this.root, 0);
    }

    public void printIndentedTree (TreeNode node,int level){
        if (node == null) return;

        for (int i = 0; i < level; i++) {
            System.out.print("\t");
        }
        System.out.println(node.value);
        printIndentedTree(node.left, level + 1);
        printIndentedTree(node.right, level + 1);

    }

    public boolean isBalanced(TreeNode node) {
        if(node == null) return true;

        int leftHeight = getHeight(node.left);
        int rightHeight = getHeight(node.right);

        if(Math.abs(leftHeight - rightHeight) > 1) return false;

        return isBalanced(node.left) && isBalanced(node.right);
    }

    public int getHeight(TreeNode node) {
        if(node == null) return 0;
        return 1 + Math.max(getHeight(node.left), getHeight(node.right));

    }

    public ArrayList<Integer> getTreeValues() {
        ArrayList<Integer> values = new ArrayList<>();
        inOrderTraversal(this.root, values);
        return values;
    }

    /*
    private method that performs an in-order traversal of the tree and adds the values to a
    local array list passed as a parameter called in getTreeValues.
    This recursively calls itself to traverse the tree left to right.
    */
    private void inOrderTraversal(TreeNode node, ArrayList<Integer> values) {
        if(node == null) return;
        inOrderTraversal(node.left, values);
        values.add(node.value);
        inOrderTraversal(node.right, values);

    }

    public TreeNode getRoot() {
        return this.root;

    }

}
