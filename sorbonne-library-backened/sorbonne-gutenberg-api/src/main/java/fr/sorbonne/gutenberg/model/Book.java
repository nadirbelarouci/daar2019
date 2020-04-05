package fr.sorbonne.gutenberg.model;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.google.gson.annotations.SerializedName;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@DynamoDBTable(tableName = "gutenberg")
public class Book {
    public static final String TABLE = "gutenberg";
    @DynamoDBHashKey(attributeName = "book_id")
    @EqualsAndHashCode.Include
    @ToString.Include
    @SerializedName("book_id")
    private String bookId;

    @DynamoDBAttribute(attributeName = "title")
    private String title;

    @DynamoDBAttribute(attributeName = "author")
    private String author;

    public Book(String bookId) {
        this.bookId = bookId;
    }


}
