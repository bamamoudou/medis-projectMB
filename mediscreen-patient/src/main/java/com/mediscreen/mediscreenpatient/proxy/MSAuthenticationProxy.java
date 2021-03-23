package com.mediscreen.mediscreenpatient.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "${mediscreen-mediscreen-authentification.name}", url = "${mediscreen-mediscreen-authentification.url}")
public interface MSAuthenticationProxy {
	@GetMapping("/validate-token")
	ResponseEntity<Void> validateToken(@RequestParam("token") String token);
}