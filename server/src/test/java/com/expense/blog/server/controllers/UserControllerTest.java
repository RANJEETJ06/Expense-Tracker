package com.expense.blog.server.controllers;

import com.expense.blog.server.payloads.UserDto;
import com.expense.blog.server.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void createUser_Success() {
        UserDto testUserDto = new UserDto();
        testUserDto.setUserName("testUser");
        testUserDto.setEmail("test@example.com");
        testUserDto.setPassword("qaz");
        testUserDto.setBudget(2300);
        testUserDto.setTotalTransaction(2000);
        testUserDto.setNoOfTransaction(2);

        when(userService.createUser(any(UserDto.class))).thenReturn(testUserDto);

        ResponseEntity<UserDto> response = userController.createUser(testUserDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(testUserDto, response.getBody());
    }

    @Test
    void updateUser_Success() {
        UserDto testUserDto = new UserDto();
        testUserDto.setBudget(200);

        when(userService.userUpdate(any(UserDto.class), anyInt())).thenReturn(testUserDto);

        ResponseEntity<UserDto> response = userController.updateUser(testUserDto, "123");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testUserDto, response.getBody());
    }

    @Test
    void deleteUser_Success() {
        ResponseEntity<?> response = userController.deleteUser("123");

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(Collections.singletonMap("Message", "Deleted successfully"), response.getBody());
    }

    @Test
    void getUser_Success() {
        UserDto testUserDto = new UserDto();
        testUserDto.setUserName("testUser");
        testUserDto.setEmail("test@example.com");

        when(userService.getUserById(anyInt())).thenReturn(testUserDto);

        ResponseEntity<UserDto> response = userController.GetUser("123");

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(testUserDto, response.getBody());
    }

    @Test
    void getAllUsers_Success() {
        List<UserDto> testUsers = Collections.singletonList(new UserDto());

        when(userService.getAllUsersBySize(anyInt(), anyInt())).thenReturn(testUsers);

        ResponseEntity<List<UserDto>> response = userController.GetAllUser(1, 5);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(testUsers, response.getBody());
    }
}
