package com.zensar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;


@Service
public class UserServiceDelegateImp implements UserServiceDelegate {
 
//	@Autowired
//	RestTemplate restTemplate;
//	
//	@Override
//	public boolean isTokenValid(String token) {
//		HttpHeaders headers = new HttpHeaders();
//		headers.add("Authorization", token);
//		HttpEntity entity = new HttpEntity(headers);
//		ResponseEntity<Boolean> response =
//				this.restTemplate.exchange("http://localhost:8080/token/validate",
//						HttpMethod.GET,
//				entity, Boolean.class);
//		return response.getBody();
//	}
 
	@Autowired
	RestTemplate restTemplate;
	
	@Override
	@CircuitBreaker(name = "AUTH_TOKEN_VALIDATION", 
			fallbackMethod = "fallbackForIsTokenValid")
	public boolean isTokenValid(String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", token);
		HttpEntity entity = new HttpEntity(headers);
		ResponseEntity<Boolean> response = 
				this.restTemplate.exchange("http://API-Gateway/zenuserapp/token/validate", 
						HttpMethod.GET,
				entity, Boolean.class);
		return response.getBody();
	}

	public boolean fallbackForIsTokenValid(String token, Throwable t) {
		System.out.println("Inside fallbackForIsTokenValid exception: " + t);
		return false;
	}
}