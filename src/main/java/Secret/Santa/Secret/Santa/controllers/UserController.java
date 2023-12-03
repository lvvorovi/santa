package Secret.Santa.Secret.Santa.controllers;

import Secret.Santa.Secret.Santa.models.DTO.UserDTO;
import Secret.Santa.Secret.Santa.models.User;
import Secret.Santa.Secret.Santa.repos.IUserRepo;
import Secret.Santa.Secret.Santa.services.IUserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Validated
public class UserController {
    @Autowired
    private IUserRepo iUserRepo;
    @Autowired
    private IUserService iUserService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = iUserService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid UserDTO userDTO) {
        User user = iUserService.createUser(userDTO);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{userid}")
    public ResponseEntity<User> getUserById(@Valid
                                            @Min(value = 1, message = "ID must be a non-negative integer and greater than 0")
                                            @PathVariable int userid) {
        User user = null;
        user = iUserService.findByUserid(userid);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{userid}")
    public ResponseEntity<User> updateUser(@Valid
                                           @Min(value = 1, message = "ID must be a non-negative integer and greater than 0")
                                           @PathVariable int userid, @RequestBody @Valid UserDTO userDTO) {
        User user = iUserService.editByUserId(userDTO, userid);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{userid}")
    public ResponseEntity<String> deleteUser(@Valid @Min(value = 1, message = "ID must be a non-negative integer and greater than 0")
                                             @PathVariable int userid) {
        if (Boolean.TRUE.equals(iUserService.deleteUserByUserid(userid))) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.badRequest().body("Delete failed");
    }

}
