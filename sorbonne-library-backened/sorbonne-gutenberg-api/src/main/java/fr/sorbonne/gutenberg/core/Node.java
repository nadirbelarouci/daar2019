package fr.sorbonne.gutenberg.core;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

public class Node implements Serializable {
    private Map<Character, Node> children;
    private boolean isWord;

    public Node(Map<Character, Node> children, boolean isWord) {
        this.children = children;
        this.isWord = isWord;
    }

    public boolean contains(char c) {
        return children.containsKey(c);
    }

    public Node getChildren(char c) {
        return children.get(c);
    }

    public Node computeIfAbsent(char c, Function<Character, Node> mapper) {
        return children.computeIfAbsent(c, mapper);
    }


    public void setWord(boolean word) {
        isWord = word;
    }

    public Collection<String> findNodes(String prefix) {
        Collection<String> words = new ArrayList<>();
        if (isWord) {
            words.add(prefix);
        }
        children.forEach((c, node) -> words.addAll(node.findNodes(prefix + c)));
        return words;
    }
}
