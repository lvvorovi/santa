package Secret.Santa.Secret.Santa.validationUnits;

import Secret.Santa.Secret.Santa.exception.SantaValidationException;
import Secret.Santa.Secret.Santa.models.Gift;
import Secret.Santa.Secret.Santa.models.Group;
import Secret.Santa.Secret.Santa.models.User;
import Secret.Santa.Secret.Santa.repos.IGiftRepo;
import Secret.Santa.Secret.Santa.repos.IGroupRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GiftUtils {

    @Autowired
    private final IGiftRepo giftRepository;
    private final UserUtils userUtils;


    public Gift getGiftById(Integer id) {
        return giftRepository.findById(id)
                .orElseThrow(() -> new SantaValidationException("Gift does not exist", "id",
                        "Gift not found", String.valueOf(id)));
    }

    public boolean giftBelongsToUser(int userId, int giftId) {
        Gift gift = getGiftById(giftId);
        User user = userUtils.getUserById(userId);
        return gift.getCreatedBy().equals(user);
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
