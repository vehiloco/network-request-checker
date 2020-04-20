import org.apache.http.client.methods.HttpGet;

public class HttpGetTest {
    void a() throws Exception{
        // :: error: network.request.found
        HttpGet httpget = new HttpGet("https://www.example.com");
    }
}
