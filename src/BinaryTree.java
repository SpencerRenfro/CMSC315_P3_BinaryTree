import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public final class BinaryTree {

    private final ArrayList<String> errorMessages = new ArrayList<>();
    private final ArrayList<Integer> treeArray = new ArrayList<>();

    //A constructor that accepts a string containing the preorder representation of a binary tree
    //and constructs a binary tree
    public BinaryTree(String userInput) throws InvalidTreeSyntaxException {
        if(this.isValidInput(userInput)){
            //parse the string and construct the binary tree
            //this is a placeholder for the actual implementation
            System.out.println("Valid input: " + userInput);

        } else {
            throw new InvalidTreeSyntaxException(String.join("\n", errorMessages));
        }
    }
    //constructor that accepts an array list of integers and constructs a balanced binary search
    //tree containing those values
    public BinaryTree(ArrayList<Integer> treeArray){

    }

    // check for valid input
    private boolean isValidInput(String userInput) throws InvalidTreeSyntaxException {
        boolean isValid = true;
        Stack<Character> stack = new Stack<>();
        // check for valid input
        String message = "";
        for(int i = 0; i < userInput.length(); i++){
            char c = userInput.charAt(i);
            char prevChar = i > 0 ? userInput.charAt(i - 1) : ' ';
            //checks for integers and if true adds them to the tree array
            if(Character.isDigit(c)){
                treeArray.add(Character.getNumericValue(c));
            }
            // checks for any invalid characters other than integers, spaces, parenthesis and asterisks
            if(!Character.isDigit(c) && c != ' ' && c != '(' && c != ')' && c != '*'){
                isValid = false;
                message = "Invalid syntax: Invalid character found";
                this.errorMessages.add(message);
            }
            if(c == '('){
                stack.push(c);
            } else if(c == ')'){
                if(stack.isEmpty()){
                    isValid = false;
                    message = "Invalid syntax: Missing Left Parenthesis";
                    this.errorMessages.add(message);
                } else if (prevChar == '(') {
                    isValid = false;
                    message = "Invalid syntax: Empty parentheses";
                    this.errorMessages.add(message);
                } else if(stack.pop() != '('){
                    isValid = false;
                    message = "Invalid syntax: Missing Right Parenthesis";
                    this.errorMessages.add(message);

                }
            }
        }
        // check for unmatched opening parenthesis
        if(!stack.isEmpty()){
            char[] unmatchedCharArray = new char[stack.size()];
            isValid = false;
            for(int i = 0; i < stack.size(); i++){
                unmatchedCharArray[i] = stack.pop();
                if(unmatchedCharArray[i] == '('){
                    message = "Invalid syntax: Missing Right Parenthesis";
                    this.errorMessages.add(message);

                } else if(unmatchedCharArray[i] == ')'){
                    message = "Invalid syntax: Missing Left Parenthesis";
                    this.errorMessages.add(message);
                }
            }

        }
        return isValid;
    }
    private void parseInput(String userInput) {
      String[] leftParenthesis = userInput.split("\\(");
      int nestedLevel = 0;
      for(int i = 0; i < userInput.length(); i++){
          char c = userInput.charAt(i);
          if(c == '('){
              nestedLevel++;
          }

      }
    }

    private ArrayList<Integer> getTreeArray() {
        return this.treeArray;
    }
    private void buildFromArray(ArrayList<Integer> values) {
        treeArray.clear();
        treeArray.addAll(values);
        System.out.println("Built tree from array: " + values);
    }

}
