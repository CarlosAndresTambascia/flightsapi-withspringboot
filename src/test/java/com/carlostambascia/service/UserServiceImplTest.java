package com.carlostambascia.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceImplTest {
    public static final String NAME = "admin";

    @Test
    public void loadByUSerNameShouldRetrieveSpecifiedNameAndPassword() {
        //given
        UserServiceImpl userService = new UserServiceImpl();
        //when
        UserDetails userDetails = userService.loadUserByUsername("");
        //then
        assertThat(userDetails.getUsername()).isEqualTo(NAME);
        assertThat(userDetails.getPassword()).isEqualTo(NAME);
    }
}