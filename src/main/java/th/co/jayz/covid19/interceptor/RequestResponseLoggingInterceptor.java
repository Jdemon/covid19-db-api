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
 
    
	private static final Logger log = LoggerFactory.getLogger("service_log");
 
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
    	long ptime = System.currentTimeMillis();
    	StringJoiner joiner = logRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        logResponse(response,joiner);
        ptime = System.currentTimeMillis() - ptime;
        joiner.add("processTime="+ptime);
        log.info(joiner.toString());
        return response;
    }
 
	private StringJoiner logRequest(HttpRequest request, byte[] body) throws IOException {
		StringJoiner joiner = new StringJoiner("|");
		joiner.add("log_type=external_service");
		joiner.add("uri="+request.getURI().toString());
		joiner.add("method="+request.getMethod().toString());
		//joiner.add("reqHeader="+request.getHeaders().toString());
		if(request.getMethod().toString().equals("POST")) {
			joiner.add("reqBody="+(new String(body, "UTF-8")));
		}
		return joiner;
	}
 
    private void logResponse(ClientHttpResponse response,StringJoiner joiner) throws IOException {
    	
		joiner.add("statusCode="+response.getStatusCode().value());
		joiner.add("statusText="+response.getStatusText().toString());
		//joiner.add("reqHeader="+response.getHeaders().toString());
		joiner.add("respBody="+StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()).toString());
    }
}