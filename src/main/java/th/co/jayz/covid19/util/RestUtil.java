package th.co.jayz.covid19.util;

import java.util.Arrays;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestUtil {
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	private HttpEntity<String> getEntity(){
		HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        return entity;
	}
	
	public ResponseEntity<HashMap<String, String>> exchange(String url, HttpMethod method){
        
        ParameterizedTypeReference<HashMap<String, String>> responseType = 
                new ParameterizedTypeReference<HashMap<String, String>>() {};
       
       return restTemplate.exchange(url,method,getEntity(),responseType);
	}
	
	public  <T> ResponseEntity<T> exchange(String url, HttpMethod method,Class<T> responseType){
		
		return restTemplate.exchange(url,method,getEntity(),responseType);
	}
	
	

}
