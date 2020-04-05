package fr.sorbonne.gutenberg.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.print.Doc;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IndexReader extends IndexWorker {
    private static final Logger logger = LoggerFactory.getLogger(IndexReader.class);

    public IndexReader(ExecutorService executor, List<Index> indexes) {
        super(executor, indexes);
    }

    public Set<Document> findByPrefix(String key) {
        return find(index -> index.findByPrefix(key));
    }

    public Set<Document> findByRegex(String key) {
        return find(index -> index.findByRegex(key));
    }

    public Set<Document> find(Function<Index, Stream<Document>> docs) {
        List<CompletableFuture<Stream<Document>>> futureResults =  indexes.stream()
                .map(index -> CompletableFuture.supplyAsync(() -> docs.apply(index)))
                .collect(Collectors.toList());

        Set<Document> documents = futureResults
                .stream()
                .map(CompletableFuture::join)
                .filter(Objects::nonNull)
                .flatMap(Function.identity())
                .collect(Collectors.toSet());
        logger.info("merging result finished....");

        return documents;
    }
}
