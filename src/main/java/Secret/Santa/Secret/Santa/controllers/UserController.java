package Secret.Santa.Secret.Santa.controllers;

import Secret.Santa.Secret.Santa.models.DTO.UserDTO;
import Secret.Santa.Secret.Santa.models.User;
import Secret.Santa.Secret.Santa.repos.IUserRepo;
import Secret.Santa.Secret.Santa.services.IUserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Validated
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private IUserRepo iUserRepo;
    @Autowired
    private IUserService iUserService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = iUserService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            logger.error("Error retrieving all users", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid UserDTO userDTO) {
        try {
            User user = iUserService.createUser(userDTO);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            logger.error("Error creating user", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{userid}")
    public ResponseEntity<User> getUserById(@Valid
                                            @Min(value = 1, message = "ID must be a non-negative integer and greater than 0")
                                            @PathVariable int userid) {
        try {
            User user = null;
            user = iUserService.findByUserid(userid);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            logger.error("Error retrieving user with ID: {}", userid, e);
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{userid}")
    public ResponseEntity<User> updateUser(@Valid
                                           @Min(value = 1, message = "ID must be a non-negative integer and greater than 0")
                                           @PathVariable int userid, @RequestBody @Valid UserDTO userDTO) {
        try {
            User user = iUserService.editByUserId(userDTO, userid);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            logger.error("Error updating user with ID: {}", userid, e);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{userid}")
    public ResponseEntity<String> deleteUser(@Valid @Min(value = 1, message = "ID must be a non-negative integer and greater than 0")
                                             @PathVariable int userid) {
        try {
            if (Boolean.TRUE.equals(iUserService.deleteUserByUserid(userid))) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.badRequest().body("Delete failed");
            }
        } catch (Exception e) {
            logger.error("Error deleting user with ID: {}", userid, e);
            return ResponseEntity.internalServerError().body("Error occurred while deleting user");
        }
    }

}
