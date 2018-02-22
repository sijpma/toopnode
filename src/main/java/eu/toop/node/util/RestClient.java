package eu.toop.node.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestClient {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private RestTemplate localRestTemplate;
	
	@Autowired
	private List<String> nonProxyHosts;

	private HttpHeaders headers;
	private HttpStatus status;

	public RestClient() {
		this.headers = new HttpHeaders();
	}

	public String get(String url) {
		HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
		ResponseEntity<String> responseEntity = getRestTemplate(url).exchange(url, HttpMethod.GET, requestEntity, String.class);
		this.setStatus(responseEntity.getStatusCode());
		return responseEntity.getBody();
	}

	private RestTemplate getRestTemplate(String url) {		
		if (nonProxyHosts.stream().filter(p->url.contains(p)).count()>0) {
			return localRestTemplate;
		}
		else {
			return restTemplate;
		}
	}

	public String post(String url, String json) {
		HttpEntity<String> requestEntity = new HttpEntity<String>(json, headers);
		ResponseEntity<String> responseEntity = getRestTemplate(url).exchange(url, HttpMethod.POST, requestEntity,
				String.class);
		this.setStatus(responseEntity.getStatusCode());
		return responseEntity.getBody();
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}
}
