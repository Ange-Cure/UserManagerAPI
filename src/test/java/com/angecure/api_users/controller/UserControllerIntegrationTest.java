package com.angecure.api_users.controller;

import com.angecure.api_users.model.Users;
import com.angecure.api_users.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testGetUser_UserFound() throws Exception {
        Integer userId = 1;
        Users user = new Users(userId, "testUser", LocalDate.of(1990, 1, 1), "France", "1234567890", Users.Gender.Male);
        userRepository.save(user);

        mockMvc.perform(get("/getUserById")
                        .param("id", userId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"userId\":1,\"userName\":\"testUser\",\"birthdate\":\"1990-01-01\",\"countryOfResidence\":\"France\",\"phoneNumber\":\"1234567890\",\"gender\":\"Male\"}"));
    }

    @Test
    public void testGetUser_UserNotFound() throws Exception {
        Integer userId = 2;

        mockMvc.perform(get("/getUserById")
                        .param("id", userId.toString()))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"message\":\"User not found\",\"status\":404}"));
    }
}
