package fr.sorbonne.gutenberg.api;

import fr.sorbonne.gutenberg.core.Index;
import fr.sorbonne.gutenberg.model.Book;
import fr.sorbonne.gutenberg.services.SearchService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Set;


@RestController
@RequestMapping("/api/search")
@AllArgsConstructor
public class SearchApi {
    private static final Logger logger = LoggerFactory.getLogger(SearchApi.class);

    private SearchService searchService;

    @GetMapping("/{value}")
    public ResponseEntity<Collection<Book>> searchByValue(@PathVariable String value,
                                                   @RequestParam(required = false) Integer start) {
        Collection<Book> books = searchService.searchByValue(value.toLowerCase(), start);
        logger.info("finished loading books from DB");

        return ResponseEntity.ok().body(books);
    }

    @GetMapping("/regex/{pattern}")
    public ResponseEntity<Collection<Book>> searchByRegex(@PathVariable String pattern,
                                                   @RequestParam(required = false) Integer start) {
        Collection<Book> books = searchService.searchByRegex(pattern.toLowerCase(), start);
        return ResponseEntity.ok().body(books);
    }


}
