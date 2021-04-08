package com.mediscreen.msauthentication.controller;

import com.mediscreen.msauthentication.interfaces.SecurityServiceInterface;
import com.mediscreen.msauthentication.models.Jwt;
import com.mediscreen.msauthentication.models.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class AuthenticationController {
    @Autowired
    private SecurityServiceInterface securityService;

    @PostMapping("/generate-token")
    public ResponseEntity<Jwt> generateToken(@Valid @RequestBody Login login){
        return new ResponseEntity<>(securityService.logUser(login), HttpStatus.OK);
    }

    @GetMapping("/validate-token")
    public ResponseEntity<Void> validateToken(@RequestParam String token){
        if (securityService.isLog(token)) return ResponseEntity.status(HttpStatus.OK).build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
