package com.maxime.mavenpoc;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class SpringConnector {
	
	private String url;
	private static RestTemplate restTemplate = new RestTemplate();
	
	@SuppressWarnings("unused")
	private SpringConnector(){
		
	}
	
	public SpringConnector(String url){
		RestTemplate restTemplate = getRestTemplate();
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
		setUrl(url);
	}
	
	public Object getGenericObjects(){
		Object result = getRestTemplate().getForObject(url, Object.class);
		return result;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public static RestTemplate getRestTemplate() {
		return restTemplate;
	}


}
