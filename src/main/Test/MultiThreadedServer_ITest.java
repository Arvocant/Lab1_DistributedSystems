import org.junit.Test;
import org.kaas.EchoClient;
import org.kaas.FileClient;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

public class MultiThreadedServer_ITest {

    @Test
    public void Client1_requestsJPGFile() throws IOException {
        FileClient client1 = new FileClient();
        client1.startConnection("127.0.0.1", 6666);
        File file1 = client1.requestFile("otag.jpg");
        System.out.println("file received");
        client1.stopConnection();

        assertEquals(file1,new File("src/serverfiles/otag.jpg"));
    }

    @Test
    public void Client2_requestsPDFFile() throws IOException {
        FileClient client2 = new FileClient();
        client2.startConnection("127.0.0.1", 6666);
        File file1 = client2.requestFile("lab1.pdf");
        System.out.println("file received");
        client2.stopConnection();

        assertEquals(file1,new File("src/serverfiles/otag.jpg"));
    }
}
