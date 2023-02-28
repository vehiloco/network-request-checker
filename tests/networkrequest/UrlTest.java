import java.net.MalformedURLException;
import java.net.URL;

public class UrlTest {
    void a() throws MalformedURLException {
        // :: error: network.request.found
        URL url1 = new URL("https://urltest.com");
        // :: error: network.request.found
        URL url2 = new URL("http", "example.com", 80, "pages/page1.html");
    }

    void b(String url) throws MalformedURLException {
        // :: error: network.request.found
        URL url3 = new URL(url);
    }
}
