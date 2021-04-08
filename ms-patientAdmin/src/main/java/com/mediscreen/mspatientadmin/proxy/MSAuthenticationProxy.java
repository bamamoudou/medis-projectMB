package com.mediscreen.mspatientadmin.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ems-zuul")
public interface MSAuthenticationProxy {
    @GetMapping("/ms-authentication/validate-token")
    ResponseEntity<Void> validateToken(@RequestParam("token") String token);
}
