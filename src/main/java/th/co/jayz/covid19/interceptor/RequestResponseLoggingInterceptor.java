package th.co.jayz.covid19.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;
 
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.StringJoiner;
 
public class RequestResponseLoggingInterceptor implements ClientHttpRequestInterceptor {
 
    
	private static final Logger log = LoggerFactory.getLogger(RequestResponseLoggingInterceptor.class);
 
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        logRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        logResponse(response);
        return response;
    }
 
	private void logRequest(HttpRequest request, byte[] body) throws IOException {
		StringJoiner joiner = new StringJoiner(",");
		joiner.add(request.getURI().toString());
		joiner.add(request.getMethod().toString());
		joiner.add(request.getHeaders().toString());
		joiner.add((new String(body, "UTF-8")));
		log.info(joiner.toString());
	}
 
    private void logResponse(ClientHttpResponse response) throws IOException {
    	
    	StringJoiner joiner = new StringJoiner(",");
		joiner.add(response.getStatusText().toString());
		joiner.add(response.getHeaders().toString());
		joiner.add(StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()).toString());
		log.info(joiner.toString());
    }
}