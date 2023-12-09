package Secret.Santa.Secret.Santa.validationUnits;

import Secret.Santa.Secret.Santa.exception.SantaValidationException;
import Secret.Santa.Secret.Santa.models.Group;
import Secret.Santa.Secret.Santa.models.User;
import Secret.Santa.Secret.Santa.repos.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserUtils {

    @Autowired
    private final IUserRepo userRepository;


    public UserUtils(IUserRepo userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new SantaValidationException("User does not exist", "id",
                        "User not found", String.valueOf(id)));
    }


//    public List<User> getUsersInGroup(Group group) {
//        List<User> usersInGroup = userRepository.findByGroups(group);
//
//        if (usersInGroup.isEmpty()) {
//            throw new SantaValidationException("No users in group", "id",
//                    "Users not found in the group", String.valueOf(group.getGroupId()));
//        }
//
//        return usersInGroup;
//    }


}
