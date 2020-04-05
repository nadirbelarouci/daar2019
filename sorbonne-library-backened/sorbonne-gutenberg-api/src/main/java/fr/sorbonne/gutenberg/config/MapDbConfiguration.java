package fr.sorbonne.gutenberg.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class MapDbConfiguration {

    private MapDbProperties mapDbProperties;

//    @Bean
//    public DB makeDb() {
//
//        return DBMaker.fileDB(mapDbProperties.getDbPath())
//                .fileMmapEnable()
//                .allocateIncrement(64 * 1024 * 1024)
//                .closeOnJvmShutdown()
//                .make();
//    }


}
