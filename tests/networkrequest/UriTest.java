import java.net.URI;


public class UriTest {
    static final String str = "https://www.example.com";

    void a() throws Exception {
        // :: error: network.request.found
        URI uri1 = new URI(str);
        // :: error: network.request.found.in.method.invocation
        URI uri2 = URI.create(str);
    }
    void b() throws Exception {
        // :: error: network.request.found.in.method.invocation
        URI.create(str);
    }
    void c() throws Exception {
        // TODO This should be reported, rather than line 15.
        b();
    }
}
