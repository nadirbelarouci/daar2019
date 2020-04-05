package fr.sorbonne.gutenberg.core;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Autocompletion implements Serializable {
    private Node root;

    public Autocompletion(Collection<String> words) {
        root = new Node(new HashMap<>(), false);
        add(words);
    }


    public void add(Collection<String> words) {
        Node current;
        for (String word : words) {
            current = root;
            for (char c : word.toCharArray()) {
                current = current.computeIfAbsent(c,
                        key -> new Node(new HashMap<>(), false));
            }
            current.setWord(true);
        }
    }

    public Collection<String> autocomplete(String prefix) {
        Node current = root;
        for (char c : prefix.toCharArray()) {
            if (!current.contains(c)) {
                return new ArrayList<>();
            }
            current = current.getChildren(c);
        }
        return current.findNodes(prefix);
    }

}
