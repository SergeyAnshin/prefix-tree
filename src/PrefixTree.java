import java.util.*;

public class PrefixTree {
    private final Node node;

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
    }

    public PrefixTree() {
        this.node = new Node('.', false, null);
    }
}
