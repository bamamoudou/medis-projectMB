package com.mediscreen.mediscreenpatient.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "zuul-server")
public interface MSAuthenticationProxy {
    @GetMapping("/mediscreen-authentication/validate-token")
    ResponseEntity<Void> validateToken(@RequestParam("token") String token);
}