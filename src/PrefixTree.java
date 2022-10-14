import java.util.*;

public class PrefixTree {
    private final Node rootNode;

    public PrefixTree() {
        this.rootNode = new Node('.', false, null);
    }

    private static class Node {
        private char letter;
        private boolean lastLetter;
        private Map<Character, Node> childNodes;

        public Node(char letter, boolean lastLetter, Map<Character, Node> childNodes) {
            this.letter = letter;
            this.lastLetter = lastLetter;
            this.childNodes = childNodes;
        }

        public char getLetter() {
            return letter;
        }

        public void setLetter(char letter) {
            this.letter = letter;
        }

        public boolean isLastLetter() {
            return lastLetter;
        }

        public void setLastLetter(boolean lastLetter) {
            this.lastLetter = lastLetter;
        }

        public Map<Character, Node> getChildNodes() {
            return childNodes;
        }

        public void setChildNodes(Map<Character, Node> childNodes) {
            this.childNodes = childNodes;
        }

        private static boolean existsNodeWithValue(char currentChar, Map<Character, Node> childNodes) {
            return childNodes != null && childNodes.containsKey(currentChar);
        }
    }
    
    public void add(String word) throws NullPointerException {
        if (word == null ) {
            throw new NullPointerException();
        }

        if (!word.isBlank()) {
            Node lastNode = this.rootNode;
            Map<Character, Node> childNodes;
            char currentChar;
            for (int i = 0; i < word.length(); i++) {
                if (lastNode.getChildNodes() == null) {
                    lastNode.setChildNodes(new HashMap<>());
                }
                childNodes = lastNode.getChildNodes();
                currentChar = word.charAt(i);
                if (Node.existsNodeWithValue(currentChar, childNodes)) {
                    lastNode = childNodes.get(currentChar);
                    if (isLastLetterIndex(word, i)) {
                        lastNode.setLastLetter(true);
                    }
                } else {
                    Node newNode = new Node(currentChar, isLastLetterIndex(word, i), null);
                    childNodes.put(currentChar, newNode);
                    lastNode = newNode;
                }
            }
        }
    }

    public boolean contains(String word) {
        Node currentNode = this.rootNode;
        boolean isExistingWord = false;
        for (int i = 0; i < word.length(); i++) {
           if (Node.existsNodeWithValue(word.charAt(i), currentNode.getChildNodes())) {
               currentNode = currentNode.getChildNodes().get(word.charAt(i));
               if (isLastLetterIndex(word, i)) {
                   isExistingWord = currentNode.isLastLetter();
               }
           }
        }
        return isExistingWord;
    }

    public Set<String> findAllByPrefix(String prefix) throws NullPointerException {
        if (prefix == null || prefix.isBlank()) {
            return Collections.emptySet();
        }

        Node lastNode = getLastNode(prefix);
        if (lastNode == null) {
            return Collections.emptySet();
        }

        return getWords(new HashSet<>(), new StringBuilder().append(prefix), lastNode);
    }

    private Set<String> getWords(HashSet<String> words, StringBuilder stringBuilder, Node lastNode) {
        if (lastNode.isLastLetter()) {
            words.add(stringBuilder.toString());
        }
        if (lastNode.getChildNodes() != null) {
            for (Node node : lastNode.getChildNodes().values()) {
                stringBuilder.append(node.getLetter());
                getWords(words, stringBuilder, node);
                stringBuilder.delete(stringBuilder.length()-1, stringBuilder.length());
            }
        }
        return words;
    }

    public Node getLastNode(String word) throws NoSuchElementException {
        Node currentNode = this.rootNode;
        for (int i = 0; i < word.length(); i++) {
            if (Node.existsNodeWithValue(word.charAt(i), currentNode.getChildNodes())) {
                currentNode = currentNode.getChildNodes().get(word.charAt(i));
            } else {
                currentNode = null;
                break;
            }
        }
        return currentNode;
    }

    private static boolean isLastLetterIndex(String word, int i) {
        return i == word.length() - 1;
    }
}
