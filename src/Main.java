import java.util.Scanner;
import java.util.ArrayList;

public class Main {


    public static void main(String[] args) throws InvalidTreeSyntaxException {


        Scanner input = new Scanner(System.in);
        boolean isValidTreeSyntax = false;
        char continueInput = ' ';
        ArrayList<Integer> extractedIntegersFromUserInput = new ArrayList<>();


        // Prompt the user for input until a valid tree syntax is provided

        while (!isValidTreeSyntax || continueInput != 'n') {
            System.out.print("Enter a binary tree: ");
            String userInput = input.nextLine();
            String[] splitIntegers = userInput.split("[^0-9]+");


            //Create a new instance of BinaryTree, this automatically checks for valid input
            // and throws an exception if the input is invalid

            BinaryTree binaryTree = new BinaryTree(userInput);
            isValidTreeSyntax = true;
            // if no errors are thrown from construction, then the input is valid
            // if the input is valid, then we can extract the integers from the user input
            for(String integers: splitIntegers){
                if(!integers.isEmpty()){
                    extractedIntegersFromUserInput.add(Integer.parseInt(integers));
                }
            }
            System.out.println("Extracted integers from user input: " + extractedIntegersFromUserInput);
            //The extracted integers are passed to a different binary tree constructor

            System.out.println("This will be more tree output");
            System.out.println("Do you want to enter another binary tree? (y/n)");
            continueInput = input.nextLine().charAt(0);


        }

    }
}