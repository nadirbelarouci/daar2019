package fr.sorbonne.gutenberg.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "map-db")
@Setter
@Getter
public class MapDbProperties {
    private int tablesCount;
    private String indexPart;
    private String dbPath;
}
