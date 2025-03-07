import org.junit.Test;
import org.kaas.EchoClient;
import org.kaas.FileClient;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


//run MultiThreadedServer.main() before this!
public class MultiThreadedServer_ITest {

    @Test
    public void Client1_requestsJPGFile() throws IOException {
        FileClient client1 = new FileClient();
        client1.startConnection("127.0.0.1", 6666);
        File file1 = client1.requestFile("otag.jpg");
        client1.stopConnection();

        assertEquals(Files.mismatch(Path.of(file1.getPath()),Path.of("src/files/serverfiles/otag.jpg")),-1);
    }

    @Test
    public void Client2_requestsPDFFile() throws IOException {
        FileClient client2 = new FileClient();
        client2.startConnection("127.0.0.1", 6666);
        File file2 = client2.requestFile("lab1.pdf");
        client2.stopConnection();

        assertEquals(Files.mismatch(Path.of(file2.getPath()),Path.of("src/files/serverfiles/lab1.pdf")),-1);
    }
}
