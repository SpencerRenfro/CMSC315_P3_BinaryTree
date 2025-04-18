import java.util.*;

public class Test {

    public static class Node {
        public int level;
        public int value;
        public boolean hasLeftChild;
        public boolean hasRightChild;
        public boolean isLeaf;
        public boolean isRoot;
        public boolean isBalanced;

        public Node(int level, int value, int asteriskCount) {
            this.level = level;
            this.value = value;
            this.isRoot = (level == 1);

            switch (asteriskCount) {
                case 0 -> {
                    hasLeftChild = true;
                    hasRightChild = true;
                    isLeaf = false;
                }
                case 1 -> {
                    hasLeftChild = false;
                    hasRightChild = true;
                    isLeaf = false;
                }
                case 2 -> {
                    hasLeftChild = false;
                    hasRightChild = false;
                    isLeaf = true;
                }
                default -> throw new IllegalArgumentException("Invalid asterisk count: " + asteriskCount);
            }

            isBalanced = hasLeftChild && hasRightChild;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "level=" + level +
                    ", value=" + value +
                    ", isLeaf=" + isLeaf +
                    ", hasLeftChild=" + hasLeftChild +
                    ", hasRightChild=" + hasRightChild +
                    ", isRoot=" + isRoot +
                    ", isBalanced=" + isBalanced +
                    '}';
        }
    }

    public static class ParseFrame {
        int level;
        Integer value = null;
        int asteriskCount = 0;

        ParseFrame(int level) {
            this.level = level;
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a binary tree: ");
        String userInput = input.nextLine();

        ArrayList<Node> nodeList = new ArrayList<>();
        Stack<ParseFrame> stack = new Stack<>();
        StringBuilder numberBuffer = new StringBuilder();
        int level = 0;

        for (int i = 0; i < userInput.length(); i++) {
            char c = userInput.charAt(i);

            if (c == ' ') continue;

            switch (c) {
                case '(' -> {
                    level++;
                    stack.push(new ParseFrame(level));
                }
                case ')' -> {
                    ParseFrame frame = stack.pop();
                    if (frame.value != null) {
                        Node node = new Node(frame.level, frame.value, frame.asteriskCount);
                        nodeList.add(node);
                        System.out.println(frame.asteriskCount + " asterisks for value " + frame.value);
                    }
                    level--;
                }
                case '*' -> {
                    if (!stack.isEmpty()) {
                        stack.peek().asteriskCount++;
                    }
                }
                default -> {
                    if (Character.isDigit(c)) {
                        numberBuffer.append(c);
                        if (i == userInput.length() - 1 || !Character.isDigit(userInput.charAt(i + 1))) {
                            int val = Integer.parseInt(numberBuffer.toString());
                            if (!stack.isEmpty()) {
                                stack.peek().value = val;
                            }
                            numberBuffer.setLength(0);
                        }
                    }
                }
            }
        }

        System.out.println("\nFinal Nodes:");
        nodeList.forEach(System.out::println);
    }
}
