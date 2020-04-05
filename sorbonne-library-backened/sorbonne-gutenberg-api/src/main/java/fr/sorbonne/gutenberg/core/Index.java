package fr.sorbonne.gutenberg.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class Index {
    private static final Logger logger = LoggerFactory.getLogger(Index.class);
    private ConcurrentHashMap<String, List<Document>> index;
    private Autocompletion autocompletion;
    private boolean isDirty;

    public Index(ConcurrentHashMap<String, List<Document>> index) {
//        this.db = db;
        this.index = index;
        autocompletion = new Autocompletion(index.keySet());
    }

    public ConcurrentHashMap<String, List<Document>> add(TokenizedBook book, int bookId) {
        logger.info("Start indexing {}", bookId);
        book.keySet().forEach(word -> {
            List<Document> list = index.computeIfAbsent(word, k -> new ArrayList<>());
            Document wordPerDocCount = new Document(bookId, book.getWordCount(word));
            int i = Collections.binarySearch(list, wordPerDocCount);
            if (i < 0) {
                index.compute(word, (s, wordPerDocumentCounts) -> {
                    list.add(-i - 1, wordPerDocCount);
                    return list;
                });
            }
        });
        logger.info("Finished indexing {}", bookId);
        autocompletion.add(book.keySet());
        isDirty = true;
        return index;
    }


    public Stream<Document> findByPrefix(String key) {
        logger.info("building result from autocomplete");
        Collection<String> words = autocompletion.autocomplete(key);
        Stream<Document> docs = words.stream()
                .map(index::get)
                .filter(Objects::nonNull)
                .flatMap(List::stream);
        logger.info("building result from autocomplete finished");

        return docs;

    }

    public Stream<Document> findByRegex(String regex) {
        NFA nfa = new NFA(regex);
        return index.keySet().stream()
                .filter(nfa::recognizes)
                .map(index::get)
                .filter(Objects::nonNull)
                .flatMap(List::stream);
    }

    public void print() {
        index.forEach((s, wordPerDocumentCounts) -> {
            System.out.println(s + ": " + wordPerDocumentCounts);
        });
    }

    public void print(Collection<String> keys) {
        keys.forEach(key -> System.out.println(key + ": " + index.get(key)));
    }

    @Override
    public String toString() {
        return index.toString();
    }

    public ConcurrentHashMap<String, List<Document>> getIndex() {
        return index;
    }

    public boolean isDirty() {
        return isDirty;
    }

    public void setDirty(boolean dirty) {
        isDirty = dirty;
    }
}
