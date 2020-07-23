package com.bootcamp.nomnom.service;

import com.bootcamp.nomnom.TestData;
import com.bootcamp.nomnom.entity.Recipe;
import com.bootcamp.nomnom.entity.User;
import com.bootcamp.nomnom.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
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
    private MultipartFile multipartFile;

    private final String TEST_FILENAME_2 = "testFilename_2.png";


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        testUser = TestData.getUser();
    }

    @Test
    void loadUserByUsernameTest() {
        when(userRepository.findByUsername(anyString())).thenReturn(testUser);

        userService.loadUserByUsername(TestData.TEST_USERNAME);
        //We only check the invocation count because it returns weird object UserDetails. It will be the only weird place in the code
        verify(userRepository,atLeastOnce()).findByUsername(anyString());
    }

    //Use this one as a good example of an unit test :)
    @Test
    void savingUserWhenRegisterTest() {
        when(passwordEncoder.encode(anyString())).thenReturn(TestData.TEST_USERNAME);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User userSaved = userService.saveUserRegister(testUser);

        assertEquals(userSaved, testUser);
    }

    @Test
    void userExistsTest() {
        when(userRepository.existsByUsername(anyString())).thenReturn(true);
        assertTrue(userService.userExists(TestData.TEST_USERNAME));
    }

    @Test
    void saveProfilePhotoTest() throws IOException {
        User u = TestData.getUser();
        when(userRepository.save(any(User.class))).thenReturn(u);

        User actualUser = userService.saveProfilePhoto(u, TestData.getMockMultipartFile(TestData.TEST_CONTENT));
        assertEquals(u, actualUser);
    }

    @Test
    void updateUserPasswordTest() {
        final String ENCODED_PASSWORD = "Xc79Ysjw973ns";
        User u = TestData.getUser();
        when(passwordEncoder.encode(anyString())).thenReturn(ENCODED_PASSWORD);
        when(userRepository.save(any(User.class))).thenReturn(u);

        User actualUser = userService.updateUserPassword(TestData.getUser(), TestData.TEST_PASSWORD);

        assertEquals(u.getId(), actualUser.getId());
        assertEquals(u.getUsername(), actualUser.getUsername());
        assertEquals(ENCODED_PASSWORD, actualUser.getPassword());
        assertEquals(u.getFileName(), actualUser.getFileName());
    }

    @Test
    void getUserByUsernameTest() {
        User u = TestData.getUser();
        when(userRepository.findByUsername(anyString())).thenReturn(u);

        assertEquals(u, userService.getUserByUsername(TestData.TEST_USERNAME));
    }

    @Test
    void deleteUserPictureTest() throws IOException {
        User u = TestData.getUser();
        MultipartFile multipartFile = TestData.getMockMultipartFile(TestData.TEST_CONTENT);

        Files.write(Paths.get(TestData.USER_ABSOLUTE_PATH + u.getFileName()), multipartFile.getBytes());
        assertTrue(Files.exists(Paths.get(TestData.USER_ABSOLUTE_PATH + TestData.TEST_FILENAME)));

        assertEquals("default.png", userService.deleteUserPicture(u).getFileName());
        assertFalse(Files.exists(Paths.get(TestData.USER_ABSOLUTE_PATH + TestData.TEST_FILENAME)));
    }

    @Test
    void saveProfilePictureHasImageTest() throws IOException {
        multipartFile = TestData.getMockMultipartFile(TestData.TEST_CONTENT);
        testUser.setFileName(TEST_FILENAME_2);

        Files.write(Paths.get(TestData.USER_ABSOLUTE_PATH + testUser.getFileName()), multipartFile.getBytes());
        assertTrue(Files.exists(Paths.get(TestData.USER_ABSOLUTE_PATH + testUser.getFileName())));

        User retrieved = userService.saveProfilePhoto(testUser, multipartFile);

        assertEquals(TestData.TEST_USERNAME, testUser.getUsername());
        assertNotEquals(TEST_FILENAME_2, testUser.getFileName());
    }

    @Test
    void saveProfilePictureNoImageTest() throws IOException {
        multipartFile = null;

        User retrieved = userService.saveProfilePhoto(testUser, multipartFile);

        assertEquals(TestData.TEST_USERNAME, retrieved.getUsername());
        assertEquals("default.png", retrieved.getFileName());
    }
}
