import java.util.*;

public class Test3 {

    public static class Node {
        int value;
        Node leftChild;
        Node rightChild;

        public Node(int value) {
            this.value = value;
        }
    }

    public static class Parser {
        private String input;
        private int index;

        public Parser(String input) {
            this.input = input.replaceAll(" ", ""); // remove spaces
            this.index = 0;
        }

        public Node parse() {
            if (index >= input.length() || input.charAt(index) != '(') return null;
            index++; // skip '('

            // Check for empty child '*'
            if (input.charAt(index) == '*') {
                index++; // skip '*'
                if (index < input.length() && input.charAt(index) == ')') index++; // skip ')'
                return null;
            }

            // Parse value
            StringBuilder num = new StringBuilder();
            while (index < input.length() && Character.isDigit(input.charAt(index))) {
                num.append(input.charAt(index++));
            }

            Node node = new Node(Integer.parseInt(num.toString()));

            // Parse left and right subtrees
            node.leftChild = parse();
            node.rightChild = parse();

            if (index < input.length() && input.charAt(index) == ')') index++; // skip ')'

            return node;
        }

    }

    public static void preorder(Node node) {
        if (node == null) return;
        System.out.println(node.value);
        preorder(node.leftChild);
        preorder(node.rightChild);
    }

    public static void inorder(Node node) {
        if (node == null) return;
        inorder(node.leftChild);
        System.out.println(node.value);
        inorder(node.rightChild);
    }

    public static void levelOrder(Node root) {
        if (root == null) return;
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            System.out.println(current.value);
            if (current.leftChild != null) queue.add(current.leftChild);
            if (current.rightChild != null) queue.add(current.rightChild);
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a binary tree: ");
        String userInput = input.nextLine();

        Parser parser = new Parser(userInput);
        Node root = parser.parse();

        System.out.println("\nPre-order:");
        preorder(root);

        System.out.println("\nIn-order:");
        inorder(root);

        System.out.println("\nLevel-order:");
        levelOrder(root);
    }
}
