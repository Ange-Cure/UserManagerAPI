package com.angecure.api_users.controller;

import com.angecure.api_users.model.ErrorResponse;
import com.angecure.api_users.model.Users;
import com.angecure.api_users.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUser_UserFound() {
        Integer userId = 1;
        Users user = new Users(userId, "testUser", LocalDate.of(1990, 1, 1), "France", "1234567890", Users.Gender.Male);
        when(userService.getUser(userId)).thenReturn(Optional.of(user));

        ResponseEntity<?> response = userController.getUser(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testGetUser_UserNotFound() {
        Integer userId = 1;
        when(userService.getUser(userId)).thenReturn(Optional.empty());

        ResponseEntity<?> response = userController.getUser(userId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(new ErrorResponse("User not found", HttpStatus.NOT_FOUND.value()), response.getBody());
    }

    @Test
    public void testRegisterUser_Success() {
        String username = "testUser";
        String birthdate = "01/01/1990";
        String countryOfResidence = "France";
        Optional<String> phoneNumber = Optional.of("1234567890");
        Optional<String> gender = Optional.of("Male");

        ResponseEntity<?> response = userController.registerUser(username, birthdate, countryOfResidence, phoneNumber, gender);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User registered successfully", response.getBody());
        verify(userService, times(1)).registerUser(any(Users.class));
    }

    @Test
    public void testRegisterUser_InvalidDateFormat() {
        String username = "testUser";
        String birthdate = "invalidDate";
        String countryOfResidence = "France";
        Optional<String> phoneNumber = Optional.of("1234567890");
        Optional<String> gender = Optional.of("Male");

        ResponseEntity<?> response = userController.registerUser(username, birthdate, countryOfResidence, phoneNumber, gender);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(new ErrorResponse("Invalid date format", HttpStatus.BAD_REQUEST.value()), response.getBody());
    }

    @Test
    public void testRegisterUser_InvalidGender() {
        String username = "testUser";
        String birthdate = "01/01/1990";
        String countryOfResidence = "France";
        Optional<String> phoneNumber = Optional.of("1234567890");
        Optional<String> gender = Optional.of("InvalidGender");

        ResponseEntity<?> response = userController.registerUser(username, birthdate, countryOfResidence, phoneNumber, gender);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(new ErrorResponse("Invalid gender value", HttpStatus.BAD_REQUEST.value()), response.getBody());
    }

    @Test
    public void testRegisterUser_NotAdultOrNotResidentInFrance() {
        String username = "testUser";
        String birthdate = "01/01/2010";
        String countryOfResidence = "France";
        Optional<String> phoneNumber = Optional.of("1234567890");
        Optional<String> gender = Optional.of("Male");

        ResponseEntity<?> response = userController.registerUser(username, birthdate, countryOfResidence, phoneNumber, gender);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals(new ErrorResponse("User is not an adult or does not reside in France", HttpStatus.FORBIDDEN.value()), response.getBody());
    }
}
