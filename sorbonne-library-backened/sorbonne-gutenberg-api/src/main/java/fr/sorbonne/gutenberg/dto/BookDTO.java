package fr.sorbonne.gutenberg.dto;


import fr.sorbonne.gutenberg.core.Document;
import fr.sorbonne.gutenberg.model.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookDTO {
    private String bookId;

    private String title;

    private String author;


    public static BookDTO from(Book book) {
        return new BookDTO(book.getBookId(), book.getTitle(), book.getAuthor());
    }

}
