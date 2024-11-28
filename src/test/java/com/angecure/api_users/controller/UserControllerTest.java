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
}
