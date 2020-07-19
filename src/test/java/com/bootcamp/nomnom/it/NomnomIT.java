package com.bootcamp.nomnom.it;

import com.bootcamp.nomnom.TestData;
import com.bootcamp.nomnom.entity.User;
import com.bootcamp.nomnom.repository.UserRepository;
import com.bootcamp.nomnom.service.UserService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.verify;

// Here we create some simple integration tests testing random things because we promised Valdis.

@SpringBootTest
public class NomnomIT {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Test
    public void createUser(){
        User userTest = userService.saveUserRegister(TestData.getUser());

        assertEquals(userTest.getUsername(), TestData.TEST_USERNAME);
        assertNotEquals(userTest.getPassword(), TestData.TEST_PASSWORD);
    }

    @Disabled
    @Test
    public void saveRecipe(){

    }
}
