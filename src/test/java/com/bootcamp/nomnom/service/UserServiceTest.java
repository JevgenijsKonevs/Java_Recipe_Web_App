package com.bootcamp.nomnom.service;

import com.bootcamp.nomnom.entity.User;
import com.bootcamp.nomnom.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    private User testUser;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);

        testUser = ServiceTestData.getUser();
    }

    @Test
    void loadUserByUsernameTest(){
        when(userRepository.findByUsername(anyString())).thenReturn(testUser);

        userService.loadUserByUsername(ServiceTestData.TEST_USERNAME);
        //We only check the invocation count because it returns weird object UserDetails. It will be the only weird place in the code
        verify(userRepository,atLeastOnce()).findByUsername(anyString());
    }

    //Use this one as a good example of an unit test :)
    @Test
    void savingUserWhenRegisterTest(){
        when(passwordEncoder.encode(anyString())).thenReturn(ServiceTestData.TEST_USERNAME);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User userSaved = userService.saveUserRegister(testUser);

        assertEquals(userSaved, testUser);
    }

}
