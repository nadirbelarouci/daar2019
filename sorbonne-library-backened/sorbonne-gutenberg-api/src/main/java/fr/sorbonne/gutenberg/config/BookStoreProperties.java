package fr.sorbonne.gutenberg.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "book-store")
@Setter
@Getter
public class BookStoreProperties {
    private String booksPath;
    private String booksIndicesPath;
    private int bookMaxFileSize;
    private String bookIndexSuffix;
    private String booksRankingFile;
}
