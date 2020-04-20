import org.checkerframework.checker.networkrequest.qual.NetworkRequest;

public class DummyRequestTest {
    class DummyRequst {
        @NetworkRequest({"URL"}) String request(String url) {
            return url;
        }
    }
    class SubDummyRequest extends DummyRequst {
        @Override
        String request(String url) {
            // :: error: network.request.found.in.method.invocation
            return super.request(url);
        }
    }
    void a() {
        SubDummyRequest sdr = new SubDummyRequest();
        // :: error: network.request.found.in.method.invocation
        sdr.request("http://www.example.com");
    }
}
