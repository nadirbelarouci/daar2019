package fr.sorbonne.gutenberg.services;

import com.amazonaws.services.dynamodbv2.document.BatchGetItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.TableKeysAndAttributes;
import com.google.gson.Gson;
import fr.sorbonne.gutenberg.core.Document;
import fr.sorbonne.gutenberg.model.Book;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SearchService {
    private static final Logger logger = LoggerFactory.getLogger(SearchService.class);

    private static final int PAGE_SIZE = 20;
    private static Gson gson = new Gson();
    private DynamoDB dynamoDB;
    private IndexerService indexerService;

    public Collection<Book> searchByValue(String value, Integer start) {
        return loadBooks(indexerService.findByPrefix(value).orElseGet(HashSet::new));
    }

    public Collection<Book> searchByRegex(String pattern, Integer start) {
        return loadBooks(indexerService.findByRegex(pattern).orElseGet(HashSet::new));
    }

    private Collection<Book> loadBooks(Set<Document> documents) {


        logger.info("starting loading books from DB");
        String[] books = documents.stream()
                .limit(100)
                .map(Document::getId)
                .map(String::valueOf)
                .toArray(String[]::new);

        TableKeysAndAttributes bookTableKeysAndAttributes = new TableKeysAndAttributes(Book.TABLE);
        // Add a partition key
        bookTableKeysAndAttributes.addHashOnlyPrimaryKeys("book_id", books);


        BatchGetItemOutcome outcome = dynamoDB.batchGetItem(bookTableKeysAndAttributes);


        return outcome.getTableItems().get(Book.TABLE)
                .stream()
                .map(Item::toJSON)
                .map(item -> gson.fromJson(item, Book.class))
                .collect(Collectors.toSet());
    }

    public void searchBookByValue(String book, String value, Integer start) {

    }

    public void searchBookByRegex(String book, String pattern, Integer start) {

    }

}
