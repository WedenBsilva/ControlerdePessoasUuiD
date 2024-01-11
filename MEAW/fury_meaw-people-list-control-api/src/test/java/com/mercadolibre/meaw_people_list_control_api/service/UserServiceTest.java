package com.mercadolibre.meaw_people_list_control_api.service;

import com.mercadolibre.meaw_people_list_control_api.models.User;
import com.mercadolibre.meaw_people_list_control_api.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testSearchUsersByTerm_Success() {
        String termoBusca = "John";
        LocalDate data = LocalDate.of(2000, 1, 29);
        List<User> usersFound = Arrays.asList(new User("john", "John Lennon", data), new User("johnk", "John Kennedy", data));

        when(userRepository.findByNameContainingIgnoreCase(termoBusca)).thenReturn(usersFound);

        List<User> result = userService.searchUsersByTerm(termoBusca);

        assertEquals(usersFound, result);

        verify(userRepository, times(1)).findByNameContainingIgnoreCase(termoBusca);
    }

    @Test
    public void testGetUserById_Success() {
        UUID userId = UUID.randomUUID();
        LocalDate data = LocalDate.of(2000, 1, 29);
        User user = new User("john", "John Lennon", data);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserById(userId);

        assertEquals(Optional.of(user), result);

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testGetUserById_UserNotFound() {
        UUID userId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Optional<User> result = userService.getUserById(userId);

        assertEquals(Optional.empty(), result);

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testCreateUserSuccess() {
        LocalDate data = LocalDate.of(2000, 1, 29);
        User user = new User("john", "John Lennon", data);

        when(userRepository.existsByNickname(anyString())).thenReturn(false);

        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.createUser(user);

        assertEquals(user, createdUser);

        verify(userRepository, times(1)).existsByNickname("john");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testCreateUser_InvalidUserEmptyName() {
        LocalDate data = LocalDate.of(2000, 1, 29);
        User user = new User("john", "", data);
        userService.createUser(user);
        assertNull(userService.createUser(user));
    }

    @Test
    void testCreateUser_NicknameAlreadyExists() {
        // Arrange
        LocalDate data = LocalDate.of(2000, 1, 29);
        User user = new User("john", "John Lennon", data);
        when(userRepository.existsByNickname(anyString())).thenReturn(true);
        userService.createUser(user);
        assertNull(userService.createUser(user));

    }

    @Test
    void testCreateUser_InvalidUserName() {
        LocalDate data = LocalDate.of(2000, 1, 29);
        User user = new User("", "John", data);
        userService.createUser(user);
        assertNull(userService.createUser(user));
    }

    @Test
    void testCreateUser_InvalidUserBirthdate() {
        User user = new User("john", "John", null);
        userService.createUser(user);
        assertNull(userService.createUser(user));
    }

    @Test
    public void testsearchUsersByTerm_Invalid() {
        String termoBusca = "";

        List<User> result = userService.searchUsersByTerm(termoBusca);

        assertNull(result);
    }
}