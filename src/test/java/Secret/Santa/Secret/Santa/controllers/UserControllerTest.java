package Secret.Santa.Secret.Santa.controllers;

import Secret.Santa.Secret.Santa.models.DTO.UserDTO;
import Secret.Santa.Secret.Santa.models.User;
import Secret.Santa.Secret.Santa.services.IUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private IUserService userService;

    @Test
    void getAllUsers() {
        List<User> mockUsers = Arrays.asList(new User(), new User());
        when(userService.getAllUsers()).thenReturn(mockUsers);

        ResponseEntity<List<User>> responseEntity = userController.getAllUsers();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockUsers, responseEntity.getBody());
    }

    @Test
    void createUser() {
        UserDTO userDTO = new UserDTO();
        User mockUser = new User();
        when(userService.createUser(userDTO)).thenReturn(mockUser);

        ResponseEntity<User> responseEntity = userController.createUser(userDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockUser, responseEntity.getBody());
    }

    @Test
    void getUserById() {
        int userId = 1;
        User mockUser = new User();
        when(userService.findByUserid(userId)).thenReturn(mockUser);

        ResponseEntity<User> responseEntity = userController.getUserById(userId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockUser, responseEntity.getBody());
    }
//
//    @Test
//    void updateUser() {
//        int userId = 1;
//        UserDTO userDTO = new UserDTO();
//        User mockUser = new User();
//        when(userService.editByUserId(userDTO, userId)).thenReturn(mockUser);
//
//        ResponseEntity<User> responseEntity = userController.updateUser(userId, userDTO);
//
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals(mockUser, responseEntity.getBody());
//    }

    @Test
    void deleteUser() {
        int userId = 1;
        when(userService.deleteUserByUserid(userId)).thenReturn(true);

        ResponseEntity<String> responseEntity = userController.deleteUser(userId);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }
}
