package com.mediscreen.msclientui.service;

import com.mediscreen.msclientui.JWTTest;
import com.mediscreen.msclientui.exception.EmptyDataException;
import com.mediscreen.msclientui.exception.NotAllowedException;
import com.mediscreen.msclientui.interfaces.SecurityServiceInterface;
import com.mediscreen.msclientui.model.Login;
import com.mediscreen.msclientui.proxy.MSZuulProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpSession;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SecurityServiceTest {
    private SecurityServiceInterface securityService;
    private Login login;

    @Mock
    private static MSZuulProxy msZuulProxy;

    @BeforeEach
    void init_test(){
        securityService = new SecurityService(msZuulProxy);
        login = new Login("username", "password", false);
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
        when(msZuulProxy.msAuthentication_validateToken(anyString())).thenReturn(new ResponseEntity<>(HttpStatus.FORBIDDEN));
        assertThatExceptionOfType(NotAllowedException.class).isThrownBy(() -> securityService.authenticationCheck("token"));
    }

    @Tag("SecurityServiceTest")
    @Test
    void isLog_test_nullOrEnptyToken(){
        HttpSession session = JWTTest.session;
        session.setAttribute("token", null);
        assertThat(this.securityService.isLog(session)).isEqualTo(false);

        session.setAttribute("token", "");
        assertThat(this.securityService.isLog(session)).isEqualTo(false);
    }

    @Tag("SecurityServiceTest")
    @Test
    void logUser_test_emtpyLogin(){
        Login wrongLogin = new Login();
        assertThatExceptionOfType(EmptyDataException.class).isThrownBy(() -> securityService.logUser(wrongLogin));
        wrongLogin.setUsername("");
        assertThatExceptionOfType(EmptyDataException.class).isThrownBy(() -> securityService.logUser(wrongLogin));
        wrongLogin.setUsername("username");
        assertThatExceptionOfType(EmptyDataException.class).isThrownBy(() -> securityService.logUser(wrongLogin));
        wrongLogin.setPassword("");
        assertThatExceptionOfType(EmptyDataException.class).isThrownBy(() -> securityService.logUser(wrongLogin));
        wrongLogin.setPassword("password");
        wrongLogin.setUsername("");
        assertThatExceptionOfType(EmptyDataException.class).isThrownBy(() -> securityService.logUser(wrongLogin));
    }

    @Tag("SecurityServiceTest")
    @Test
    void logUser_test_authentificationError(){
        when(msZuulProxy.msAuthentication_generateToken(any(Login.class))).thenThrow(new NotAllowedException("Error"));
        assertThatExceptionOfType(NotAllowedException.class).isThrownBy(() -> securityService.logUser(this.login));
    }


    @Tag("SecurityServiceTest")
    @Test
    void logUser_test_nullJWT(){
        when(msZuulProxy.msAuthentication_generateToken(any(Login.class))).thenReturn(null);
        assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> securityService.logUser(this.login));
    }
}
