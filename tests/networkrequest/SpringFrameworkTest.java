import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import java.util.Arrays;
import java.net.URI;

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
