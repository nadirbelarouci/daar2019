package fr.sorbonne.gutenberg.services;


import com.amazonaws.util.IOUtils;
import fr.sorbonne.gutenberg.config.BookStoreProperties;
import fr.sorbonne.gutenberg.exceptions.FileStoreException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    private final FileStoreService fileStoreService;
    private final BookStoreProperties bookStoreProperties;
    private final IndexerService indexerService;

    /**
     * Upload a book to the storage store
     *
     * @param book the id of the candidate
     *             =     * @return the filename of the book
     */
    public String uploadBook(MultipartFile book) {

        checkIsEmptyThenThrow(book);
        checkIsTxtThenThrow(book);

        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", book.getContentType());
        metadata.put("Content-Length", String.valueOf(book.getSize()));

        try {
            String path = String.format("%s", bookStoreProperties.getBooksPath());
            String filename = String.format("%s", generateBookId(book));
//            fileStoreService.save(path, filename, Optional.of(metadata), book.getInputStream());
            indexerService.index(book, filename);
            return filename;
        } catch (FileStoreException | IOException e) {
            logger.error("Uploading book failed : {}", e.getMessage());
            throw new FileStoreException("file.upload.failed");
        }
    }

    private String generateBookId(MultipartFile book) {
        String fileName = book.getOriginalFilename();
        if (fileName == null) throw new FileStoreException("file.upload.failed");
        int dashIndex = fileName.indexOf("-");
        return dashIndex != -1 ?
                fileName.substring(0, dashIndex) :
                fileName.substring(0, fileName.indexOf("."));
    }

    /**
     * Get a book
     *
     * @param bookName the book id
     * @return the book file as byte stream
     */
    public byte[] downloadBook(String bookName) {
        try {
            URL website = new URL(String.format("http://www.gutenberg.org/files/%s/%s.txt", bookName, bookName));
            return IOUtils.toByteArray(website.openStream());
        } catch (IOException e) {
            logger.error("Downloading book failed : {}", e.getMessage());
            throw new FileStoreException("file.download.failed");
        }
    }

    private InputStream download(String path, String name) {
        try {
            return fileStoreService.download(path, name);
        } catch (FileStoreException e) {
            logger.error("Downloading book failed : {}", e.getMessage());
            throw new FileStoreException("file.download.failed");
        }
    }

    /**
     * Check if the book file size is lower then maximum allowed size
     */
    private void checkIsMaxSizeThenThrow(MultipartFile book) {
        if (book.getSize() > (bookStoreProperties.getBookMaxFileSize() * 1000000)) {
            logger.error("Uploading book failed : file is too big");
            throw new FileStoreException("file.upload.max-size");
        }
    }

    /**
     * Check if the file is of type PDF
     */
    private void checkIsTxtThenThrow(MultipartFile book) {
        if (book.getContentType() != null && !book.getContentType().toLowerCase().contains("text/plain")) {
            logger.error("Uploading book failed : not a Txt file");
            throw new FileStoreException("file.upload.invalid-type");
        }
    }

    /**
     * Check if the file uploaded is empty
     */
    private void checkIsEmptyThenThrow(MultipartFile book) {
        if (book.isEmpty()) {
            logger.error("Uploading book failed : empty file");
            throw new FileStoreException("file.upload.empty");
        }
    }


}
