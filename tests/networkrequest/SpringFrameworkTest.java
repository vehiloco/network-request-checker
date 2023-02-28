import java.net.URI;
import java.util.Arrays;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

class SpringFrameworkTest {
    void a() throws Exception {
        // :: error: network.request.found
        URI uri = new URI("http://www.example.com");
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        // :: error: network.request.found.in.method.invocation
        restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
    }
}
