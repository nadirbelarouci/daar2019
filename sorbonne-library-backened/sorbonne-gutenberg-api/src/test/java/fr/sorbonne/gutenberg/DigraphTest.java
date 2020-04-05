package fr.sorbonne.gutenberg;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.BatchGetItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.TableKeysAndAttributes;

import com.amazonaws.services.dynamodbv2.model.KeysAndAttributes;
import fr.sorbonne.gutenberg.model.Book;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DigraphTest {

    static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
    static DynamoDB dynamoDB = new DynamoDB(client);

    @Test
    public void graph() {
//        Autocompletion autocompletion =
//                new Autocompletion(Arrays.asList("dog", "dark", "cat", "door", "dodge"));
//        System.out.println(autocompletion.autocomplete("do"));
//
//        TokenizedBook book = new TokenizedBook(
//                Stream.of("amazing demonstrate demonstrate comparator",
//                        "everything  insertion say feel follow comparator"));
//        TokenizedBook book1 = new TokenizedBook(
//                Stream.of("The last parameter specifies the comparator  comparator method ",
//                        "The insertion say point   comparator the point insertion which the key "));
////        Segment segment = new Segment();
////        segment.print(book.keySet());
////        segment.print(book1.keySet());
////        segment.add(book1,2);
////        segment.add(book,1);
//        System.out.println(System.getenv("BOOK_INDEXES_PATH"));

//        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
//        DynamoDBMapper mapper = new DynamoDBMapper(client);
//        List<String> books = Arrays.asList("43081", "48706", "48129", "45112", "48498", "41296", "43507",
//                "49299", "41172", "45973", "39958", "45246", "42047", "49475", "45425",
//                "42515", "45735", "48907", "43283", "47081", "39781", "40026", "42860",
//                "41979", "43708", "45692", "41776", "43562", "42783", "43979", "43571",
//                "45796", "40916", "44947", "44693", "45458", "49083", "43577", "42550",
//                "47261", "40413", "43551", "43169", "48545", "42918", "47619", "45767",
//                "44651", "42417", "44278", "47477", "46605", "48058", "43464", "42134", "47451");
//
//        TableKeysAndAttributes forumTableKeysAndAttributes = new TableKeysAndAttributes(Book.TABLE);
//        // Add a partition key
//        forumTableKeysAndAttributes.addHashOnlyPrimaryKeys("book_id", books.toArray(new String[books.size()]));
//
//
//        BatchGetItemOutcome outcome = dynamoDB.batchGetItem(forumTableKeysAndAttributes);
//
//        Map<String, KeysAndAttributes> unprocessed = null;
//
//        do {
//            for (String tableName : outcome.getTableItems().keySet()) {
//                System.out.println("Items in table " + tableName);
//                List<Item> items = outcome.getTableItems().get(tableName);
//                for (Item item : items) {
//                    System.out.println(item);
//                }
//            }
//
//            // Check for unprocessed keys which could happen if you exceed
//            // provisioned
//            // throughput or reach the limit on response size.
//            unprocessed = outcome.getUnprocessedKeys();
//
//            if (unprocessed.isEmpty()) {
//                System.out.println("No unprocessed keys found");
//            }
//            else {
//                System.out.println("Retrieving the unprocessed keys");
//                outcome = dynamoDB.batchGetItemUnprocessed(unprocessed);
//            }
//
//        } while (!unprocessed.isEmpty());

    }
}
