package Secret.Santa.Secret.Santa.services;

import Secret.Santa.Secret.Santa.models.DTO.UserDTO;
import Secret.Santa.Secret.Santa.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IUserService {
    List<User> getAllUsers();
    User findByUserid(int userid);
    UserDTO editByUserId(UserDTO lessorDTO, int userid);
    User createUser(UserDTO lessorDTO);

    boolean deleteUserByUserid(int userid);
}
