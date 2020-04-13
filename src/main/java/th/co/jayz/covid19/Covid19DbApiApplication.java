package th.co.jayz.covid19;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.lang.Nullable;
import io.micrometer.elastic.ElasticConfig;
import io.micrometer.elastic.ElasticMeterRegistry;
import th.co.jayz.covid19.http.entity.TimelineSummaryResp;
import th.co.jayz.covid19.interceptor.RequestResponseLoggingInterceptor;

@SpringBootApplication
public class Covid19DbApiApplication {
	
	private static final ElasticConfig elasticConfig = new ElasticConfig() {
	    @Override
	    @Nullable
	    public String get(String k) {
	        return null;
	    }
	};
	private static MeterRegistry registry = new ElasticMeterRegistry(elasticConfig, Clock.SYSTEM);

	public static void main(String[] args) {
		
		 Metrics.globalRegistry.add(registry);
		
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
