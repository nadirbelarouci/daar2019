package fr.sorbonne.gutenberg;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

class LibraryApplicationTests {

    private static Stream<Path> getFiles() throws IOException {
        return Files.list(Paths.get("../books/test"));
    }

//    @ParameterizedTest(name = "#{index} - Indexing : {0}")
//    @MethodSource("getFiles")
//    @Execution(ExecutionMode.CONCURRENT)
//    void indexing(Path path) throws IOException, InterruptedException {
//        var request = HttpRequest.newBuilder()
//                .uri(URI.create("http://localhost:8080/"))
//                .POST(HttpRequest.BodyPublishers.ofFile(path))
//                .build();
//        var client = HttpClient.newHttpClient();
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println(response.body());
//    }

}
