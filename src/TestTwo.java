import java.util.Scanner;

public class TestTwo {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter a string");

        String inputString = input.nextLine();


        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < inputString.length(); i++){
            Character c = inputString.charAt(i);
            if(Character.isDigit(c)){
                stringBuilder.append(c);
            }
            if(c == '*'){
                System.out.println("asterisk found");
            }
        }
        System.out.println("integers in the string: " + stringBuilder.length());
    }
}
