package com.mediscreen.msauthentication.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LoginTest {
    private Login login;

    @BeforeEach
    void init_test(){
        login = new Login("username", "password", false);
    }

    @Tag("LoginTest")
    @Test
    void get_test(){
        assertThat(login.getUsername()).isEqualTo("username");
        assertThat(login.getPassword()).isEqualTo("password");
        assertThat(login.isRememberUser()).isEqualTo(false);
    }

    @Tag("LoginTest")
    @Test
    void set_test(){
        assertThat(login.getUsername()).isEqualTo("username");
        assertThat(login.getPassword()).isEqualTo("password");
        assertThat(login.isRememberUser()).isEqualTo(false);

        login.setUsername("newUsername");
        login.setPassword("newPassword");
        login.setRememberUser(true);

        assertThat(login.getUsername()).isEqualTo("newUsername");
        assertThat(login.getPassword()).isEqualTo("newPassword");
        assertThat(login.isRememberUser()).isEqualTo(true);
    }
}