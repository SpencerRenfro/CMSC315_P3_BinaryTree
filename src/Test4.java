import java.util.*;

public class Test4 {

    public static class Node {
        public int level;
        public int value;
        public boolean hasLeftChild;
        public boolean hasRightChild;
        public boolean isLeaf;
        public boolean isRoot;
        public boolean isBalanced;
        Node leftChild;
        Node rightChild;
        Node parent;

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
        //variables for the printing of the tree
        List<String> treeLines = new ArrayList<>();
        TreeMap<Integer, List<String>> treeMap = new TreeMap<>();
        //variable flag for if the tree is balanced
        boolean isBalanced = true;




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
                        int tempLevel = frame.level;
                        StringBuilder line = new StringBuilder();
                        Node node = new Node(frame.level, frame.value, frame.asteriskCount);

                        nodeList.add(node);
                        //This is building the string for the tree layout, appending a tab for each level
                        while(tempLevel != 1) {
                            line.append("\t");
                            tempLevel--;
                        }
                        line.append(node.value);

                        treeLines.add(line.toString());
                        treeMap.putIfAbsent(frame.level, new ArrayList<>());
                        treeMap.get(frame.level).add(line.toString());
                        System.out.println(frame.asteriskCount + " asterisks for value " + frame.value);

                        // Check if the node is balanced, if not, set isBalanced to false
                        if (!node.isBalanced) {
                            isBalanced = false;
                        }
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
        System.out.println("\nBinary Tree String:");

        Collections.reverse(treeLines);
        for (String line : treeLines) {
            System.out.println(line);
        }

        System.out.println("\nBinary TreeMap String:");
        for (Map.Entry<Integer, List<String>> entry : treeMap.entrySet()) {
            for (String s : entry.getValue()) {
                System.out.println(s);
            }
        }
        System.out.println("\nIs the tree balanced? " + (isBalanced ? "Yes" : "No"));
    }
}



/* Issue that arose was using stack to make the string, this made an inverse layout,
so then what I tried to do was use an ArrayList since this uses the Collections framework,
I can make the ArrayList of Strings, reverse it and print it out. However, this fixed some of the problems,
but not all of them. THe siblings were still in the inverse order,
* */