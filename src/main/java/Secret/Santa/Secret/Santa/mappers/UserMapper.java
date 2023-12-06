package Secret.Santa.Secret.Santa.mappers;


import Secret.Santa.Secret.Santa.models.DTO.UserDTO;
import Secret.Santa.Secret.Santa.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public static User toUser(UserDTO userDTO, User user) {
        if (user == null){
            user = new User();
        }
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword((userDTO.getPassword()));
        return user;
    }

    public static UserDTO toUserDTO(User user) {

        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword((user.getPassword()));
        return userDTO;
    }
}
