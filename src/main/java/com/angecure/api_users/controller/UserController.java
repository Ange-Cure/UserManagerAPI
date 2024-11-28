package com.angecure.api_users.controller;

import com.angecure.api_users.model.ErrorResponse;
import com.angecure.api_users.model.Users;
import com.angecure.api_users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getUserById")
    public ResponseEntity<?> getUser(@RequestParam Integer id) {
        Optional<Users> user = userService.getUser(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("User not found", HttpStatus.NOT_FOUND.value()));
    }

    @GetMapping("/registerUser")
    public ResponseEntity<?> registerUser(@RequestParam String username, @RequestParam String birthdate, @RequestParam String countryOfResidence, @RequestParam Optional<String> phoneNumber, @RequestParam Optional<String> gender) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate localDate = LocalDate.parse(birthdate, formatter);
            Users.Gender userGender = gender.map(Users.Gender::valueOf).orElse(null);
            String userPhoneNumber = phoneNumber.orElse("");
            Users user = new Users(null, username, localDate, countryOfResidence, userPhoneNumber, userGender);
            if (!isAdultAndResidentInFrance(user)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ErrorResponse("User is not an adult or does not reside in France", HttpStatus.FORBIDDEN.value()));
            }
            userService.registerUser(user);
            return ResponseEntity.ok("User registered successfully");
        } catch (DateTimeParseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Invalid date format", HttpStatus.BAD_REQUEST.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Invalid gender value", HttpStatus.BAD_REQUEST.value()));
        }
    }

    public boolean isAdultAndResidentInFrance(Users user) {
        if (!"france".equalsIgnoreCase(user.countryOfResidence())) {
            return false;
        }

        LocalDate currentDate = LocalDate.now();
        Period age = Period.between(user.birthdate(), currentDate);

        return age.getYears() >= 18;
    }
}