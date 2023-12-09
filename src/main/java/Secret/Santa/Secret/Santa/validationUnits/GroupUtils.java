package Secret.Santa.Secret.Santa.validationUnits;

import Secret.Santa.Secret.Santa.exception.SantaValidationException;
import Secret.Santa.Secret.Santa.models.Group;
import Secret.Santa.Secret.Santa.models.User;
import Secret.Santa.Secret.Santa.repos.IGroupRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupUtils {

    @Autowired
    private final IGroupRepo groupRepository;
    private final UserUtils userUtils;


    public Group getGroupById(Integer id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new SantaValidationException("Group does not exist", "id",
                        "Group not found", String.valueOf(id)));
    }

    public boolean isUserInGroup(int userId, int groupId) {
        Group group = getGroupById(groupId);
        User user = userUtils.getUserById(userId);
        return group.getUser().contains(user);
    }
//    public boolean isUserInGroup(int userId, int groupId) {
//        // Fetch the group details by groupId
//        Group group = getGroupById(groupId);
//
//        // Fetch the user details by userId
//        User user = getUserById(userId);
//
//        // Check if the user is a member of the group
//        return group.getMembers().contains(user);
//    }


//    public List<User> getUsersInGroup(Group group) {
//        List<User> usersInGroup = groupRepository.findById(group);
//
//        if (usersInGroup.isEmpty()) {
//            throw new SantaValidationException("No users in group", "id",
//                    "Users not found in the group", String.valueOf(group.getGroupId()));
//        }
//
//        return usersInGroup;
//    }
}
