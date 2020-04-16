package th.co.jayz.covid19;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import th.co.jayz.covid19.http.entity.TimelineSummaryResp;
import th.co.jayz.covid19.interceptor.RequestResponseLoggingInterceptor;

@SpringBootApplication
public class Covid19DbApiApplication {
	
	public static void main(String[] args) {
		
		SpringApplication.run(Covid19DbApiApplication.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate() {
		ClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());

		RestTemplate restTemplate = new RestTemplate(factory);
		restTemplate.setInterceptors(Collections.singletonList(new RequestResponseLoggingInterceptor()));

		return restTemplate;
	}
	
	@Bean
	public TimelineSummaryResp cacheTimeline() {
		return new TimelineSummaryResp();
	}
	

}
