package fr.sorbonne.gutenberg.services;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import fr.sorbonne.gutenberg.core.*;
import fr.sorbonne.gutenberg.model.Book;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class IndexerService {
    private static final Logger logger = LoggerFactory.getLogger(IndexerService.class);

    private IndexWriter indexWriter;
    private IndexReader indexReader;
    private DynamoDBMapper mapper;

    public void index(MultipartFile file, String filename) throws IOException {
        TokenizedBook tokenizedBook = new TokenizedBook(new BufferedReader(new InputStreamReader(file.getInputStream())).lines());
        Book book = new Book(filename, tokenizedBook.getTitle(), tokenizedBook.getAuthor());
        mapper.save(book);
        indexWriter.add(tokenizedBook, Integer.parseInt(filename));
    }

    public Optional<Set<Document>> findByPrefix(String value) {

        return Stream.of(value.split("[^a-zA-Z]"))
                .map(indexReader::findByPrefix)
                .reduce((set1, set2) -> {
                    set1.retainAll(set2);
                    return set1;
                });
    }

    public Optional<Set<Document>> findByRegex(String regex) {
        return Optional.of(indexReader.findByRegex(regex));
    }


}
