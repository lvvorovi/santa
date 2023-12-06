//package Secret.Santa.Secret.Santa.services.impl;
//
//import Secret.Santa.Secret.Santa.models.DTO.UserDTO;
//import Secret.Santa.Secret.Santa.models.User;
//import Secret.Santa.Secret.Santa.repos.IUserRepo;
//import Secret.Santa.Secret.Santa.services.impl.UserServiceImpl;
//import jakarta.persistence.EntityNotFoundException;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//class UserServiceImplTest {
//
//    @Mock
//    private IUserRepo iUserRepo;
//
//    @InjectMocks
//    private UserServiceImpl userService;
//
//    @Test
//    void getAllUsers() {
//        List<User> userList = new ArrayList<>();
//        when(iUserRepo.findAll()).thenReturn(userList);
//
//        List<User> result = userService.getAllUsers();
//
//        assertEquals(userList, result);
//    }
//
//    @Test
//    void getAllUsers_EmptyList() {
//        when(iUserRepo.findAll()).thenReturn(Collections.emptyList());
//
//        List<User> result = userService.getAllUsers();
//
//        assertNotNull(result);
//        assertTrue(result.isEmpty());
//    }
//
//    @Test
//    void findByUserid() {
//        int userId = 1;
//        User user = new User();
//        when(iUserRepo.findById(userId)).thenReturn(Optional.of(user));
//
//        User result = userService.findByUserid(userId);
//
//        assertEquals(user, result);
//    }
//
//    @Test
//    void findByUserid_UserNotFound() {
//        int userId = 1;
//        when(iUserRepo.findById(userId)).thenReturn(Optional.empty());
//
//        assertThrows(EntityNotFoundException.class, () -> userService.findByUserid(userId));
//    }
//
////    @Test
////    void editByUserId() {
////        int userId = 1;
////        UserDTO userDTO = new UserDTO();
////        userDTO.setName("NewName");
////
////        User existingUser = new User();
////        existingUser.setUserId(userId);
////        existingUser.setName("OldName");
////
////        when(iUserRepo.findById(userId)).thenReturn(Optional.of(existingUser));
////        when(iUserRepo.save(existingUser)).thenReturn(existingUser);
////
////        User result = userService.editByUserId(userDTO, userId);
////
////        assertEquals(userDTO.getName(), result.getName());
////    }
//
////    @Test
////    void createUser() {
////        UserDTO userDTO = new UserDTO();
////        userDTO.setName("Benas");
////        userDTO.setEmail("Benas@gmail.com");
////        userDTO.setPassword("password");
////
////        User user = new User();
////        user.setName(userDTO.getName());
////        user.setEmail(userDTO.getEmail());
////        user.setPassword(userDTO.getPassword());
////        user.setGroups(new ArrayList<>());
////
////        when(iUserRepo.save(any())).thenReturn(user);
////
////        User result = userService.createUser(userDTO);
////
////        assertNotNull(result);
////        assertEquals(userDTO.getName(), result.getName());
////        assertEquals(userDTO.getEmail(), result.getEmail());
////        assertEquals(userDTO.getPassword(), result.getPassword());
////        assertTrue(result.getGroups().isEmpty());
////    }
//
//    @Test
//    void deleteUserByUserid() {
//        int userId = 1;
//
//        boolean result = userService.deleteUserByUserid(userId);
//
//        assertFalse(result);
//    }
//
//    @Test
//    void deleteUserByUserid_UserNotFound() {
//        int userId = 1;
//        when(iUserRepo.existsById(userId)).thenReturn(false);
//
//        boolean result = userService.deleteUserByUserid(userId);
//
//        assertFalse(result);
//    }
//
//    void deleteUserByUserid_NegativeUserId() {
//        int userId = -1;
//
//        assertThrows(IllegalArgumentException.class, () -> userService.deleteUserByUserid(userId));
//    }
//}
