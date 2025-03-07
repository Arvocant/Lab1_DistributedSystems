import org.junit.Test;
import org.kaas.GreetClient;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class GreetServer_ITest {

    @Test
    public void givenGreetingClient_whenServerRespondsWhenStarted_thenCorrect() throws IOException {
        GreetClient client = new GreetClient();
        client.startConnection("127.0.0.1", 4444);
        String response = client.sendMessage("hello server");
        assertEquals("hello client", response);
    }

}
