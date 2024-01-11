package com.mercadolibre.meaw_people_list_control_api.controller;

import com.mercadolibre.meaw_people_list_control_api.models.User;
import com.mercadolibre.meaw_people_list_control_api.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSearchUsersByTerm_Success() {
        String termoBusca = "John";
        LocalDate date = LocalDate.of(2000, 1, 29);
        List<User> usersFound = Arrays.asList(new User("john", "John Lennon", date), new User("johnk", "John Kennedy", date));

        when(userService.searchUsersByTerm(termoBusca)).thenReturn(usersFound);

        ResponseEntity<List<User>> responseEntity = userController.searchUsersByTerm(termoBusca);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(usersFound, responseEntity.getBody());

        verify(userService, times(1)).searchUsersByTerm(termoBusca);
    }

    @Test
    public void testGetUserById_Success() {
        UUID userId = UUID.randomUUID();
        LocalDate data = LocalDate.of(2000, 1, 29);
        User user = new User("john", "John Lennon", data);

        when(userService.getUserById(userId)).thenReturn(Optional.of(user));

        ResponseEntity<User> responseEntity = userController.getUserById(userId.toString());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(user, responseEntity.getBody());

        verify(userService, times(1)).getUserById(userId);
    }

    @Test
    public void testGetUserById_UserNotFound() {
        UUID userId = UUID.randomUUID();

        when(userService.getUserById(userId)).thenReturn(Optional.empty());

        ResponseEntity<User> responseEntity = userController.getUserById(userId.toString());

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        verify(userService, times(1)).getUserById(userId);
    }

    @Test
    public void testGetUserById_InvalidId() {
        ResponseEntity<User> responseEntity = userController.getUserById("invalidId");

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testCreateUserSuccess() {
        LocalDate data = LocalDate.of(2000, 1, 29);
        User user = new User("john", "John Lennon", data);

        when(userService.createUser(user)).thenReturn(user);

        ResponseEntity<?> responseEntity = userController.createUser(user);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        verify(userService, times(1)).createUser(user);
    }

    @Test
    public void testCreateUser_InvalidUser() {
        LocalDate data = LocalDate.of(2000, 1, 29);
        User user = new User("john", "", data);
        ResponseEntity<?> responseEntity = userController.createUser(user);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());

        verify(userService, times(0)).createUser(user);

    }

    @Test
    public void testCreateUser_NicknameInvalid() {
        LocalDate data = LocalDate.of(2000, 1, 29);
        User user = new User("", "John", data);
        ResponseEntity<?> responseEntity = userController.createUser(user);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());

        verify(userService, times(0)).createUser(user);

    }

    @Test
    public void testCreateUser_NicknameIsNull() {
        LocalDate data = LocalDate.of(2000, 1, 29);
        User user = new User(null, "John", data);
        ResponseEntity<?> responseEntity = userController.createUser(user);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());

        verify(userService, times(0)).createUser(user);

    }

    @Test
    public void testCreateUser_NameInvalid() {
        LocalDate data = LocalDate.of(2000, 1, 29);
        User user = new User("john", null, data);
        ResponseEntity<?> responseEntity = userController.createUser(user);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());

        verify(userService, times(0)).createUser(user);

    }

    @Test
    public void testCreateUser_BirthdateInvalid() {
        User user = new User("john", "John", null);
        ResponseEntity<?> responseEntity = userController.createUser(user);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());

        verify(userService, times(0)).createUser(user);

    }


    @Test
    public void testCreateUser_NicknameAlreadyExists() {
        LocalDate data = LocalDate.of(2000, 1, 29);
        User user = new User("john", "John Lennon", data);

        when(userService.createUser(user)).thenReturn(user);

        when(userService.createUser(user)).thenReturn(user);

        ResponseEntity<?> responseEntity = userController.createUser(user);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        verify(userService, times(1)).createUser(user);
    }

    @Test
    public void testSearchUsersByTermInvalid() {
        ResponseEntity<List<User>> response =
                userController.searchUsersByTerm(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
