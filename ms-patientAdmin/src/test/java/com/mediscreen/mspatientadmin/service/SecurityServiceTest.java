package com.mediscreen.mspatientadmin.service;

import com.mediscreen.mspatientadmin.exception.EmptyDataException;
import com.mediscreen.mspatientadmin.exception.NotAllowedException;
import com.mediscreen.mspatientadmin.proxy.MSAuthenticationProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SecurityServiceTest {
    private SecurityService securityService;

    @Mock
    private static MSAuthenticationProxy msAuthenticationProxy;

    @BeforeEach
    void init_test(){
        securityService = new SecurityService(msAuthenticationProxy);
    }

    @Tag("SecurityServiceTest")
    @Test
    void authenticationCheck_test_emptyToken(){
        assertThatExceptionOfType(EmptyDataException.class).isThrownBy(() -> securityService.authenticationCheck(""));
        assertThatExceptionOfType(EmptyDataException.class).isThrownBy(() -> securityService.authenticationCheck(null));
    }

    @Tag("SecurityServiceTest")
    @Test
    void authenticationCheck_test_permissionDenied(){
        when(msAuthenticationProxy.validateToken(anyString())).thenReturn(new ResponseEntity<>(HttpStatus.FORBIDDEN));
        assertThatExceptionOfType(NotAllowedException.class).isThrownBy(() -> securityService.authenticationCheck("token"));
    }
}