package fr.sorbonne.gutenberg.core;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TokenizedBook {
    private static Set<String> common_words = Stream.of("the", "at", "there", "some", "my", "of", "be", "use",
            "her", "than", "and", "this", "an", "would", "first", "a", "have", "each", "make",
            "water", "to", "from", "which", "like", "been", "in", "or", "she", "him", "call", "is",
            "one", "do", "into", "who", "you", "had", "how", "time", "oil", "that", "by", "their",
            "has", "its", "it", "word", "if", "look", "now", "he", "but", "will", "two", "find", "was",
            "not", "up", "more", "long", "for", "what", "other", "write", "down", "on", "all", "about",
            "go", "day", "are", "were", "out", "see", "did", "as", "we", "many", "number", "get", "with",
            "when", "then", "no", "come", "his", "your", "them", "way", "made", "they", "can", "these",
            "could", "may", "i", "said", "so").collect(Collectors.toSet());

    private String title = "";
    private String author = "";
    private ConcurrentHashMap<String, Integer> wordsCount = new ConcurrentHashMap<>();

    public TokenizedBook(Stream<String> lines)  {
        this.process(lines);
    }

    private void process(Stream<String> lines)  {
        lines.parallel()
                .map(String::toLowerCase)
                .filter(line -> !line.isEmpty())
                .forEach(this::processLine);

    }

    private void processLine(String line) {
        String[] words = line.split("[^a-zA-Z]");
        resolveTitle(line);
        Stream.of(words)
                .filter(word -> !word.isEmpty())
                .filter(word -> !common_words.contains(word))
                .filter(word -> word.length() > 2)
                .forEach(word -> {
                            wordsCount.merge(word, 1, Integer::sum);

                        }
                );
    }

    private void resolveTitle(String line) {
        if (title.isEmpty() && line.contains("title:")) {
            title = line.replace("title:", "").trim();
        } else if (author.isEmpty() && line.contains("author:")) {
            author = line.replace("author:", "").trim();
        }
    }

    public void print() {
        wordsCount.forEach((s, integer) -> System.out.println(s + ": " + integer));
    }

    public Set<String> keySet() {
        return wordsCount.keySet();
    }

    public int getWordCount(String word) {
        return wordsCount.getOrDefault(word, -1);
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
}
